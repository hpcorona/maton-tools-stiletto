package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Transform;


public class Image implements Drawable {
	
	private org.eclipse.swt.graphics.Image image;
	private boolean export;
	private String exportName;
	private String name;
	private boolean loaded;
	private BundleContext ctx;
	
	public Image(String name, BundleContext ctx) {
		this.name = name;
		loaded = false;
		this.ctx = ctx;
		export = true;
		
		int lIdx = name.lastIndexOf(".");
		exportName = name.substring(0, lIdx);
		
		reload();
	}

	public String getName() {
		return name;
	}

	public boolean isLoaded() {
		return loaded;
	}
	
	public void draw(GC g, int x, int y, float rotation, int alpha) {
		if (!loaded) return;
		
		Transform trans = new Transform(g.getDevice());
		trans.rotate(rotation);
		g.setTransform(trans);
		g.setAlpha(alpha);
		
		g.drawImage(image, x, y);
		
		g.setTransform(null);
	}
	
	public void reload() {
		loaded = false;
		
		if (image != null) {
			image.dispose();
		}
		
		image = ctx.loadImage(name);
		if (image != null) {
			loaded = true;
		}
	}
	
	public void free() {
		loaded = false;
		
		if (image != null) {
			image.dispose();
		}
		
		image = null;
	}
	
	@Override
	public void finalize() {
		if (image != null) {
			image.dispose();
		}
		try {
			super.finalize();
		} catch (Throwable e) {
			// Ignore
		}
	}
	
	public int getWidth() {
		if (loaded == false) {
			return 0;
		}
		return image.getBounds().width;
	}
	
	public int getHeight() {
		if (loaded == false) {
			return 0;
		}
		return image.getBounds().height;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	public String getExportName() {
		return exportName;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}

}
