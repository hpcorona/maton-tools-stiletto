package com.maton.tools.stiletto.model;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Device;

public class Bundle {
	protected File file;
	protected ImagePool images;
	protected SpritePool sprites;
	protected ArrayList<Animation> animations;
	protected BundleContext ctx;
	
	public Bundle(Device device, File file) {
		this.file = file;
		
		String path = file.getParentFile().getAbsolutePath();
		ctx = new BundleContext(device, path);
		
		images = new ImagePool(ctx);
		sprites = new SpritePool(ctx);
		animations = new ArrayList<Animation>();
		
		images.reload();
	}
	
	public ImagePool getImages() {
		return images;
	}
	
	public SpritePool getSprites() {
		return sprites;
	}
	
	public ArrayList<Animation> getAnimations() {
		return animations;
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
		
	}
	
	public void load() {
		
	}
}
