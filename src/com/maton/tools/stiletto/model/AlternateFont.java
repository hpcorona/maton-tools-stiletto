package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.IBaseModel;

public class AlternateFont implements IBaseModel {

	protected String resolution;
	protected int size;
	protected int stroke;
	protected int shadowX;
	protected int shadowY;

	public AlternateFont(String resolution) {
		this.resolution = resolution;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public int getShadowX() {
		return shadowX;
	}

	public void setShadowX(int shadowX) {
		this.shadowX = shadowX;
	}

	public int getShadowY() {
		return shadowY;
	}

	public void setShadowY(int shadowY) {
		this.shadowY = shadowY;
	}

}
