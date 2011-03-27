package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class AnimationPool extends BasePool<Animation> {

	protected BundleContext ctx;

	public AnimationPool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public Animation createElement(String name) {
		return new Animation(name);
	}

}
