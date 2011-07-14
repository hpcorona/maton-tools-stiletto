package com.maton.tools.stiletto.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Display;

public class DrawTools {
	
	public static final Transform FLIPX, FLIPY;
	public static final AffineTransform SFLIPX, SFLIPY;
	
	static {
		FLIPX = new Transform(Display.getCurrent());
		FLIPY = new Transform(Display.getCurrent());
		
		FLIPY.setElements(1, 0, 0, -1, 0, 0);
		FLIPX.setElements(-1, 0, 0, 1, 0, 0);
		
		SFLIPX = new AffineTransform();
		SFLIPY = new AffineTransform();
		
		SFLIPX.setTransform(1, 0, 0, -1, 0, 0);
		SFLIPY.setTransform(-1, 0, 0, 1, 0, 0);
	}
	
	public static void makeRotation(float angle, int x, int y, int ox, int oy, Transform t) {
		float rad = (float)Math.toRadians(-angle);
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		
		float dx = ox - cos * ox + sin * oy;
		float dy = oy - sin * ox - cos * oy;

		t.translate(x + dx, y + dy);
		t.rotate(-angle);
	}
	
	public static void drawImage(Image img, GC gc, int x, int y, int ox, int oy, int alpha, float angle, boolean flipX, boolean flipY) {
		Rectangle rect = img.getBounds();
		int rotw = rect.width / 2;
		int roth = rect.height / 2;
		
		if (flipX) {
			x += rect.width;
			rotw = -rotw;
		}
		if (flipY) {
			y += rect.height;
			roth = -roth;
		}
		
		Transform t = new Transform(gc.getDevice());
		makeRotation(angle, x + ox, y + oy, rotw, roth, t);

		
		if (flipX) {
			t.multiply(FLIPX);
		}
		
		if (flipY) {
			t.multiply(FLIPY);
		}

		gc.setTransform(t);
		gc.setAlpha(alpha);
		
		gc.drawImage(img, 0, 0);
		
		gc.setTransform(null);
		t.dispose();
	}

	public static void drawImage(Image img, GC gc, int x, int y, int ox, int oy, int sx, int sy, int sw, int sh, int dw, int dh) {
		Rectangle rect = img.getBounds();
		int rotw = rect.width / 2;
		int roth = rect.height / 2;
		
		Transform t = new Transform(gc.getDevice());
		makeRotation(0, x + ox, y + oy, rotw, roth, t);

		gc.setTransform(t);
		gc.setAlpha(255);
		
		try {
			gc.drawImage(img, sx, sy, sw, sh, 0, 0, dw, dh);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		gc.setTransform(null);
		t.dispose();
	}

	public static void makeRotation(float angle, int x, int y, int ox, int oy, AffineTransform t) {
		float rad = (float)Math.toRadians(-angle);
		float cos = (float)Math.cos(rad);
		float sin = (float)Math.sin(rad);
		
		float dx = ox - cos * ox + sin * oy;
		float dy = oy - sin * ox - cos * oy;

		t.translate(x + dx, y + dy);
		t.rotate(-angle);
	}
	
	public static void drawImage(java.awt.Image img, Graphics2D gc, int x, int y, int ox, int oy, int alpha, float angle, boolean flipX, boolean flipY) {
		int rotw = img.getWidth(null) / 2;
		int roth = img.getHeight(null) / 2;
		
		if (flipX) {
			x += img.getWidth(null);
			rotw = -rotw;
		}
		if (flipY) {
			y += img.getHeight(null);
			roth = -roth;
		}
		
		AffineTransform t = new AffineTransform();
		makeRotation(angle, x + ox, y + oy, rotw, roth, t);

		
		if (flipX) {
			//t.multiply(FLIPX);
		}
		
		if (flipY) {
			//t.multiply(FLIPY);
		}

		gc.setTransform(t);
		//gc.setAlpha(alpha);
		
		//gc.drawImage(img, 0, 0);
		
		gc.setTransform(null);
	}
}
