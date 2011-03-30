package com.maton.tools.stiletto.process.base;

import java.awt.Image;

import com.maton.tools.crusher.IPart;

public class ImagePart implements IPart {

	protected Object provider;
	protected Image image;
	protected int width;
	protected int height;
	protected int area;
	protected int outputX;
	protected int outputY;
	protected String name;

	public ImagePart(String name, Object provider, Image img) {
		this.name = name;
		this.provider = provider;
		image = img;
		width = image.getWidth(null);
		height = image.getHeight(null);
		area = width * height;
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof ImagePart) {
			ImagePart pobj = (ImagePart) obj;

			if (pobj.getArea() > getArea()) {
				return 1;
			} else if (pobj.getArea() < getArea()) {
				return -1;
			}

			return 0;
		}

		return -1;
	}

	@Override
	public int getArea() {
		return area;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Image getImage() {
		return image;
	}

	public int getOutputX() {
		return outputX;
	}

	public void setOutputX(int outputX) {
		this.outputX = outputX;
	}

	public int getOutputY() {
		return outputY;
	}

	public void setOutputY(int outputY) {
		this.outputY = outputY;
	}

	@Override
	public String getName() {
		return name;
	}

	public Object getProvider() {
		return provider;
	}

}
