package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BaseContainer;
import com.maton.tools.stiletto.model.base.IBaseModel;

public class Actor extends BaseContainer<Action, Animation> implements
		IBaseModel {

	protected String name;
	
	public Actor(String name) {
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
	public Action createChild(Animation value) {
		return new Action(value);
	}

}
