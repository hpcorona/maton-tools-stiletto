package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.IBaseModel;

public class WidgetState implements IBaseModel {

	protected String name;
	protected Image source;

	public WidgetState(Image source) {
		this.name = source.getName();
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getSource() {
		return source;
	}

	public void setSource(Image source) {
		this.source = source;
	}
	
	public Object getSelf() {
		return this;
	}

}
