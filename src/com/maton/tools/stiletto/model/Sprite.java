package com.maton.tools.stiletto.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.eclipse.swt.graphics.GC;

import com.maton.tools.stiletto.model.base.BaseContainer;
import com.maton.tools.stiletto.model.base.Drawable;
import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.model.base.IImageProvider;
import com.maton.tools.stiletto.model.base.Positioned;

public class Sprite extends BaseContainer<Positioned<Image>, Image> implements
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
	public void draw(GC g, int x, int y, float rotation, int alpha) {
		for (Positioned<Image> img : childs) {
			img.draw(g, x, y, rotation, alpha);
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
	public Positioned<Image> createChild(Image value) {
		return new Positioned<Image>(value);
	}
	
	protected void calcBounds() {
		minX = Integer.MAX_VALUE;
		minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (Positioned<Image> img : childs) {
			minX = Math.min(img.getX(), minX);
			minY = Math.min(img.getY(), minY);
			maxX = Math.max(img.getX() + img.getSource().getWidth() - 1, maxX);
			maxY = Math.max(img.getY() + img.getSource().getHeight() - 1, maxY);
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
		
		BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics2D g = buff.createGraphics();
		
		for (Positioned<Image> img : childs) {
			java.awt.Image newImg = img.getSource().getImage();
			
			if (newImg == null) continue;
			
			g.drawImage(newImg, img.getX() - minX, img.getY() - minY, null);
			
			newImg.flush();
		}
		
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
