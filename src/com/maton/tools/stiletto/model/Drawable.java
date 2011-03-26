package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;

public interface Drawable {
	public void draw(GC g, int x, int y, float rotation, int alpha);
}
