package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.IBaseModel;

public class Resolution implements IBaseModel {
	
	protected String name;
	protected float scale;
	protected String basedOn;
	
	public Resolution(String name) {
		this.name = name;
		scale = 1;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getSelf() {
		return this;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getBasedOn() {
		return basedOn;
	}

	public void setBasedOn(String basedOn) {
		this.basedOn = basedOn;
	}

}
