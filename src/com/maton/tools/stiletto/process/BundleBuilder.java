package com.maton.tools.stiletto.process;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.maton.tools.crusher.Crusher;
import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.model.CharMetric;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.IImageProvider;
import com.maton.tools.stiletto.model.io.ModelOutput;
import com.maton.tools.stiletto.process.base.ImagePart;

public class BundleBuilder implements IRunnableWithProgress {

	protected Bundle bundle;
	protected IProgressMonitor monitor;
	protected ArrayList<ImagePart> parts;
	protected HashMap<String, ArrayList<ImagePart>> fontChars;
	protected String pngFile;
	protected String bundleFile;
	protected String atlasFile;

	public BundleBuilder(Bundle bundle) {
		this.bundle = bundle;
		parts = new ArrayList<ImagePart>();
		fontChars = new HashMap<String, ArrayList<ImagePart>>();
	}

	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		this.monitor = monitor;

		pngFile = bundle.getName() + ".png";
		bundleFile = bundle.getName() + ".xml";
		atlasFile = bundle.getName() + "_atlas.xml";
		
		startup();
		buildFonts();
		distribute();
		purge();
		writeXml();
		unload();
	}
	
	private void writeXml() {
		monitor.beginTask("Generating Bundle", 10);
		
		monitor.subTask("Writing Texture Atlas...");
		ModelOutput.saveAtlas(new File(bundle.getCtx().getBuildPath(atlasFile)), pngFile, parts);
		
		for (Font font : bundle.getFonts().getList()) {
			monitor.subTask("Writing Font " + font.getName() + "...");
			
			File fontFile = new File(bundle.getCtx().getBuildPath(font.getName() + ".fnt"));
			ModelOutput.saveFont(fontFile, font, fontChars.get(font.getName()));
		}
		
		monitor.subTask("Writing the final Bundle...");
		ModelOutput.saveBundle(new File(bundle.getCtx().getBuildPath(bundleFile)), atlasFile, bundle);
	}
	
	private void purge() {
		// Purge fonts
		for (int i = parts.size() - 1; i >= 0; i--) {
			if (parts.get(i).getName() == null) {
				parts.remove(i);
			}
		}
	}

	private void distribute() {
		monitor.beginTask("Consolidating resources", 10);

		monitor.subTask("Optimizing Texture Atlas...");

		Crusher crusher = new Crusher();
		for (ImagePart part : parts) {
			crusher.addPart(part);
		}

		crusher.run();

		monitor.subTask("Saving image...");
		BufferedImage img = (BufferedImage) crusher.buildImage();
		try {
			ImageIO.write(
					img,
					"png",
					new File(bundle.getCtx().getBuildPath(
							pngFile)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startup() {
		monitor.beginTask("Preparing resources", 10);

		monitor.subTask("Preparing subtextures...");
		List<IImageProvider> used = ModelOutput.getUsedImages(bundle);
		for (IImageProvider img : used) {
			String name = "";
			
			if (img instanceof Image) {
				name = ((Image) img).getExportName();
			} else if (img instanceof Sprite) {
				name = ((Sprite) img).getImageName();
			}
			ImagePart part = new ImagePart(name, img, img.getImage());
			parts.add(part);
		}
	}

	public void unload() {
		monitor.beginTask("Unloading resources", 10);

		monitor.subTask("Deleting temporal images...");

		for (ImagePart part : parts) {
			part.getImage().flush();
		}
		parts.clear();
	}

	public void buildFonts() {
		List<Font> fonts = bundle.getFonts().getList();
		for (Font font : fonts) {
			buildFont(font);
		}
	}

	public void buildFont(Font font) {
		monitor.subTask("Building font " + font.getName() + "...");
		
		ArrayList<ImagePart> chars = new ArrayList<ImagePart>();

		List<CharMetric> metrics = font.getCharMetrics();
		for (CharMetric metric : metrics) {
			BufferedImage charImg = new BufferedImage(metric.width,
					metric.height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
			Graphics2D g = charImg.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			font.regenFont(g);
			font.renderChar(g, 0, 0, metric);
			g.dispose();

			ImagePart part = new ImagePart(null, metric, charImg);
			parts.add(part);
			
			chars.add(part);
		}
		
		fontChars.put(font.getName(), chars);
	}
}
