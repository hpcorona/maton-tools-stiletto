package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.IBaseModel;

public class Action implements IBaseModel {

	protected String name;
	protected Animation source;

	public Action(Animation source) {
		this.name = source.getName();
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Animation getSource() {
		return source;
	}

	public void setSource(Animation source) {
		this.source = source;
	}
	
	public Object getSelf() {
		return this;
	}

}
