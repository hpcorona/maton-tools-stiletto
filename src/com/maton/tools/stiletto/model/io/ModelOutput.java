package com.maton.tools.stiletto.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.model.base.IImageProvider;
import com.maton.tools.stiletto.model.base.NameResolver;
import com.maton.tools.stiletto.model.base.Positioned;
import com.maton.tools.stiletto.process.base.ImagePart;

public class ModelOutput {

	public static List<IImageProvider> getUsedImages(Bundle bundle) {
		ArrayList<IImageProvider> used = new ArrayList<IImageProvider>();

		List<Image> images = bundle.getImages().getList();
		for (Image img : images) {
			if (img.isExport()) {
				used.add(img);
			}
		}

		List<Sprite> sprites = bundle.getSprites().getList();
		for (Sprite spr : sprites) {
			if (spr.isRendered()) {
				used.add(spr);
			} else {
				List<Positioned<Image>> childs = spr.getList();
				for (Positioned<Image> img : childs) {
					if (used.indexOf(img.getSource()) < 0) {
						used.add(img.getSource());
					}
				}
			}
		}

		return used;
	}

	@SuppressWarnings("rawtypes")
	public static void verifyList(List names) {
		NameResolver resolver = new NameResolver("_");
		for (Object m : names) {
			if (m instanceof IBaseModel) {
				resolver.add((IBaseModel) m);
			}
		}
		resolver.free();
	}

	public static void verifyBundle(Bundle bundle) {
		verifyList(bundle.getImages().getList());
		verifyList(bundle.getSprites().getList());
		verifyList(bundle.getAnimations().getList());
		verifyList(bundle.getActors().getList());
		verifyList(bundle.getFonts().getList());

		// Exported Images
		ArrayList<IBaseModel> exported = new ArrayList<IBaseModel>();

		List<Image> images = bundle.getImages().getList();
		for (Image img : images) {
			if (img.isExport()) {
				exported.add(new ImageProxy(img));
			}
		}

		List<Sprite> sprites = bundle.getSprites().getList();
		for (Sprite spr : sprites) {
			if (spr.isRendered()) {
				exported.add(new SpriteImageProxy(spr));
			} else {
				List<Positioned<Image>> childs = spr.getList();
				for (Positioned<Image> img : childs) {
					exported.add(new ImageProxy(img.getSource()));
				}
			}
		}

		verifyList(exported);
		
		bundle.getImages().notifyAllChange();
		bundle.getSprites().notifyAllChange();
		bundle.getAnimations().notifyAllChange();
		bundle.getActors().notifyAllChange();
		bundle.getFonts().notifyAllChange();
	}

	public static boolean save(Bundle bundle) {
		verifyBundle(bundle);
		
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "mine");
		ve.setProperty("mine.resource.loader.instance",
				new InternalResourceLoader());

		VelocityContext ctx = new VelocityContext();
		ctx.put("bundle", bundle);

		Template tpl = ve.getTemplate("output.vm");

		OutputStream out = null;
		try {
			out = new FileOutputStream(bundle.getFile());
			OutputStreamWriter osw = new OutputStreamWriter(out);

			tpl.merge(ctx, osw);

			osw.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				return false;
			}
		}
		
		return true;
	}

	public static boolean saveAtlas(File atlasFile, String pngFile, ArrayList<ImagePart> parts) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "mine");
		ve.setProperty("mine.resource.loader.instance",
				new InternalResourceLoader());

		VelocityContext ctx = new VelocityContext();
		ctx.put("image", pngFile);
		ctx.put("parts", parts);

		Template tpl = ve.getTemplate("atlas.vm");

		OutputStream out = null;
		try {
			out = new FileOutputStream(atlasFile);
			OutputStreamWriter osw = new OutputStreamWriter(out);

			tpl.merge(ctx, osw);

			osw.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				return false;
			}
		}

		return true;
	}
	
	public static boolean saveFont(File fontFile, Font font, ArrayList<ImagePart> parts) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "mine");
		ve.setProperty("mine.resource.loader.instance",
				new InternalResourceLoader());

		VelocityContext ctx = new VelocityContext();
		ctx.put("font", font);
		ctx.put("chars", parts);

		Template tpl = ve.getTemplate("font.vm");

		OutputStream out = null;
		try {
			out = new FileOutputStream(fontFile);
			OutputStreamWriter osw = new OutputStreamWriter(out);

			tpl.merge(ctx, osw);

			osw.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				return false;
			}
		}

		return true;
	}
	
	public static boolean saveBundle(File bundleFile, String atlas, Bundle bundle) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "mine");
		ve.setProperty("mine.resource.loader.instance",
				new InternalResourceLoader());

		VelocityContext ctx = new VelocityContext();
		ctx.put("atlas", atlas);
		ctx.put("bundle", bundle);

		Template tpl = ve.getTemplate("bundle.vm");

		OutputStream out = null;
		try {
			out = new FileOutputStream(bundleFile);
			OutputStreamWriter osw = new OutputStreamWriter(out);

			tpl.merge(ctx, osw);

			osw.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				return false;
			}
		}

		return true;
	}
}
