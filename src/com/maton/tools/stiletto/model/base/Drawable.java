package com.maton.tools.stiletto.model.base;

import org.eclipse.swt.graphics.GC;

public interface Drawable {
	public void draw(GC g, int x, int y, int ox, int oy, float rotation, int alpha, boolean flipX, boolean flipY);
}
