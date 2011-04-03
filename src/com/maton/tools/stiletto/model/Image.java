package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import com.maton.tools.stiletto.graphics.DrawTools;
import com.maton.tools.stiletto.model.base.Drawable;
import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.model.base.IImageProvider;

public class Image implements Drawable, IBaseModel, IImageProvider {

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

	public void draw(GC g, int x, int y, int ox, int oy, float rotation,
			int alpha, boolean flipX, boolean flipY) {
		if (!loaded)
			return;

		DrawTools.drawImage(image, g, x, y, ox, oy, alpha, rotation, flipX,
				flipY);
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

	@Override
	public void setName(String name) {
		throw new RuntimeException("Cannot change an image's name");
	}

	public Object getSelf() {
		return this;
	}

	@Override
	public java.awt.Image getImage() {
		return ctx.loadAwtImage(name);
	}

	public Rectangle getBounds() {
		return image.getBounds();
	}
}
