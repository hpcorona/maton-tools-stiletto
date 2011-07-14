package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BaseContainer;
import com.maton.tools.stiletto.model.base.IBaseModel;

public class Widget extends BaseContainer<WidgetState, Image> implements
		IBaseModel {

	protected String name;
	
	public Widget(String name) {
		super();
		this.name = name;
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
	public WidgetState createChild(Image value) {
		return new WidgetState(value);
	}

}
