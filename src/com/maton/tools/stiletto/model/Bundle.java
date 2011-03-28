package com.maton.tools.stiletto.model;

import java.io.File;

import org.eclipse.swt.graphics.Device;

import com.maton.tools.stiletto.model.io.ModelInput;
import com.maton.tools.stiletto.model.io.ModelOutput;

public class Bundle {
	protected File file;
	protected ImagePool images;
	protected SpritePool sprites;
	protected AnimationPool animations;
	protected ActorPool actors;
	protected FontPool fonts;
	protected BundleContext ctx;

	public Bundle(Device device, File file) {
		this.file = file;

		String path = file.getParentFile().getAbsolutePath();
		ctx = new BundleContext(device, path);

		images = new ImagePool(ctx);
		sprites = new SpritePool(ctx);
		animations = new AnimationPool(ctx);
		actors = new ActorPool(ctx);
		fonts = new FontPool(ctx);

		if (file.exists()) {
			load();
		} else {
			images.reload();
			save();
		}
	}

	public ImagePool getImages() {
		return images;
	}

	public SpritePool getSprites() {
		return sprites;
	}

	public AnimationPool getAnimations() {
		return animations;
	}

	public ActorPool getActors() {
		return actors;
	}

	public FontPool getFonts() {
		return fonts;
	}

	public void refresh() {
		images.reload();
	}

	public void imports(File[] pngs) {
		for (File png : pngs) {
			images.importFile(png);
		}
	}

	public void build() {

	}

	public void save() {
		ModelOutput.save(this);
	}

	public void load() {
		ModelInput.load(this);
	}

	public File getFile() {
		return file;
	}

	public void clear() {
		fonts.clear();
		actors.clear();
		animations.clear();
		sprites.clear();
		images.clear();
	}

}
