package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.IBaseModel;

public class Alternate implements IBaseModel {

	protected String resolution;
	protected String imageName;
	protected org.eclipse.swt.graphics.Image image;
	protected float scaleX;
	protected float scaleY;
	
	public Alternate(String resolution) {
		this.resolution = resolution;
		imageName = "";
		scaleX = 1;
		scaleY = 1;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public org.eclipse.swt.graphics.Image getImage() {
		return image;
	}

	public void setImage(org.eclipse.swt.graphics.Image image) {
		this.image = image;
	}

	public float getScaleX() {
		return scaleX;
	}

	public void setScaleX(float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY() {
		return scaleY;
	}

	public void setScaleY(float scaleY) {
		this.scaleY = scaleY;
	}

	@Override
	public String getName() {
		return resolution;
	}

	@Override
	public void setName(String name) {
		resolution = name;
	}

	@Override
	public Object getSelf() {
		return this;
	}

}
