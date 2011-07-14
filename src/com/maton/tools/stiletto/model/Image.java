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
	
	private boolean framed;
	private float left, right, top, bottom;

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
	
	public void draw(GC g, int x, int y, int ox, int oy, int width, int height) {
		if (!loaded)
			return;
		
		int mw = getWidth() - (int)left - (int)right;
		int mh = getHeight() - (int)top - (int)bottom;
		
		int rw = width - (int)left - (int)right;
		int rh = height - (int)top - (int)bottom;
		
		int px = ox + width - (int)right;
		int py = oy + height - (int)bottom;
		
		if (mw < 0 || mh < 0 || rw < 0 || rh < 0) {
			return;
		}
		
		// Arriba
		DrawTools.drawImage(image, g, x, y, ox, oy, 0, 0, (int)left, (int)top, (int)left, (int)top);
		DrawTools.drawImage(image, g, x, y, ox + (int)left, oy, (int)left, 0, mw, (int)top, rw, (int)top);
		DrawTools.drawImage(image, g, x, y, px, oy, mw + (int)left, 0, (int)right, (int)top, (int)right, (int)top);

		// Enmedio
		DrawTools.drawImage(image, g, x, y, ox, oy + (int)top, 0, (int)top, (int)left, mh, (int)left, rh);
		DrawTools.drawImage(image, g, x, y, ox + (int)left, oy + (int)top, (int)left, (int)top, mw, mh, rw, rh);
		DrawTools.drawImage(image, g, x, y, px, oy + (int)top, mw + (int)left, (int)top, (int)right, mh, (int)right, rh);

		// Abajo
		DrawTools.drawImage(image, g, x, y, ox, py, 0, getHeight() - (int)bottom, (int)left, (int)bottom, (int)left, (int)bottom);
		DrawTools.drawImage(image, g, x, y, ox + (int)left, py, (int)left, getHeight() - (int)bottom, mw, (int)bottom, rw, (int)bottom);
		DrawTools.drawImage(image, g, x, y, px, py, mw + (int)left, getHeight() - (int)bottom, (int)right, (int)bottom, (int)right, (int)bottom);
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

	public boolean isFramed() {
		return framed;
	}

	public void setFramed(boolean framed) {
		this.framed = framed;
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getRight() {
		return right;
	}

	public void setRight(float right) {
		this.right = right;
	}

	public float getTop() {
		return top;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getBottom() {
		return bottom;
	}

	public void setBottom(float bottom) {
		this.bottom = bottom;
	}
}
