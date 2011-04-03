package com.maton.tools.stiletto.model;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

import com.maton.tools.stiletto.graphics.DrawTools;
import com.maton.tools.stiletto.model.base.BaseContainer;
import com.maton.tools.stiletto.model.base.Drawable;
import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.model.base.IImageProvider;

public class Sprite extends BaseContainer<Positioned, Image> implements
		Drawable, IBaseModel, IImageProvider {

	private String name;
	private boolean rendered;
	private String imageName;
	private int width;
	private int height;
	private int minX;
	private int minY;

	public Sprite(String name) {
		super();
		this.name = name;
		rendered = false;
		imageName = "";
	}

	@Override
	public void draw(GC g, int x, int y, int ox, int oy, float rotation,
			int alpha, boolean flipX, boolean flipY) {
		for (Positioned img : childs) {
			img.draw(g, x, y, 0, 0, rotation, alpha, flipX, flipY);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;

		if (rendered && (imageName == null || imageName.length() == 0)) {
			imageName = name;
		}
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public Positioned createChild(Image value) {
		return new Positioned(value);
	}

	protected void calcBounds() {
		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (Positioned img : childs) {
			Rectangle bound = img.getBounds();
			bound.x += img.getX();
			bound.y += img.getY();
			
			minX = Math.min(bound.x, minX);
			minY = Math.min(bound.y, minY);
			maxX = Math.max(bound.x + bound.width - 1, maxX);
			maxY = Math.max(bound.y + bound.height - 1, maxY);
		}

		width = maxX - minX;
		height = maxY - minY;
	}

	@Override
	public java.awt.Image getImage() {
		if (!rendered) {
			return null;
		}

		calcBounds();

		BufferedImage buff = new BufferedImage(width, height,
				BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g = buff.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);


		for (Positioned img : childs) {
			java.awt.Image newImg = img.getSource().getImage();

			if (newImg == null)
				continue;

			AffineTransform prev = g.getTransform();
			Composite prec = g.getComposite();
			
			AffineTransform at = new AffineTransform();

			int rotw = newImg.getWidth(null) / 2;
			int roth = newImg.getHeight(null) / 2;
			
			int x = img.getX() - minX;
			int y = img.getY() - minY;
			
			if (img.isFlipX()) {
				at.concatenate(DrawTools.SFLIPY);
				//x += newImg.getWidth(null);
				//rotw = -rotw;
			}
			
			if (img.isFlipY()) {
				at.concatenate(DrawTools.SFLIPX);
				//y += newImg.getHeight(null);
				//roth = -roth;
			}

			if (img.getRotation() != 0) {
				at.rotate(Math.toRadians(-img.getRotation()),
						rotw, roth);
			}

			g.setTransform(at);

			if (img.getAlpha() < 255) {
				AlphaComposite ac = AlphaComposite.SrcOver.derive(((float) img
						.getAlpha()) / 255.0f);
				g.setComposite(ac);
			}

			g.drawImage(newImg, x, y, null);
			
			g.setComposite(prec);
			g.setTransform(prev);

			newImg.flush();
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width-1, height-1);

		g.dispose();

		return buff;
	}

	public int getImageX() {
		return -minX;
	}

	public int getImageY() {
		return -minY;
	}

}
