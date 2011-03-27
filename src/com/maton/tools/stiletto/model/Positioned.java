package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;

public class Positioned<T extends Drawable> implements Drawable {

	private T source;
	private int x;
	private int y;
	private int alpha;
	private float rotation;

	public Positioned(T source) {
		this.source = source;
		x = 0;
		y = 0;
		alpha = 255;
		rotation = 0;
	}

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
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
	public void draw(GC g, int x, int y, float rotation, int alpha) {
		source.draw(g, this.x + x, this.y + y, this.rotation, this.alpha);
	}
	
}
