package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class ActorPool extends BasePool<Actor> {

	protected BundleContext ctx;

	public ActorPool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public Actor createElement(String name) {
		return new Actor(name);
	}

}
