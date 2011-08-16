package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import com.maton.tools.stiletto.model.base.Drawable;

public class Positioned implements Drawable {

	private Image source;
	private int x;
	private int y;
	private int alpha;
	private float rotation;
	private boolean flipX;
	private boolean flipY;
	private Resolution resolution;

	public Positioned(Image source) {
		this.source = source;
		x = 0;
		y = 0;
		alpha = 255;
		rotation = 0;
	}

	public Image getSource() {
		return source;
	}

	public void setSource(Image source) {
		this.source = source;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public float getRotation() {
		return rotation;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	@Override
	public void draw(GC g, int x, int y, int ox, int oy, float rotation,
			int alpha, boolean flipX, boolean flipY) {
		source.draw(g, x, y, this.x, this.y, this.rotation, this.alpha,
				this.flipX, this.flipY);
	}

	public boolean isFlipX() {
		return flipX;
	}

	public void setFlipX(boolean flipX) {
		this.flipX = flipX;
	}

	public boolean isFlipY() {
		return flipY;
	}

	public void setFlipY(boolean flipY) {
		this.flipY = flipY;
	}

	public Rectangle getBounds() {
		Rectangle base = source.getBounds();
		if (rotation == 0) {
			return base;
		}

		Rectangle rect = new Rectangle(base.x, base.y, base.width, base.height);

		float ox = base.width / 2;
		float oy = base.height / 2;
		float rad = (float) Math.toRadians(-rotation);
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);

		float nx = ox - cos * ox + sin * oy;
		float ny = oy - sin * ox - cos * oy;

		rect.x += nx;
		rect.y += ny;
		rect.width -= nx;
		rect.height -= ny;

		return rect;
	}

	public Resolution getResolution() {
		return resolution;
	}

	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
	public float getResX() {
		if (resolution == null) {
			return (float)x;
		}
		
		if (source == null) {
			return resolution.scale * (float)x;
		}
		
		Alternate alt = source.getAlternates().getElement(resolution.getName());
		if (alt == null) {
			return resolution.scale * (float)x;
		}
		
		return alt.scaleX * (float)x;
	}
	
	public float getResY() {
		if (resolution == null) {
			return (float)y;
		}
		
		if (source == null) {
			return resolution.scale * (float)y;
		}
		
		Alternate alt = source.getAlternates().getElement(resolution.getName());
		if (alt == null) {
			return resolution.scale * (float)y;
		}
		
		return alt.scaleY * (float)y;
	}

}
