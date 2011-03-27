package com.maton.tools.stiletto.model;

import java.util.ArrayList;

public class AnimationPool extends ModelEventProvider {

	protected BundleContext ctx;
	protected ArrayList<Animation> animations;

	public AnimationPool(BundleContext ctx) {
		this.ctx = ctx;
		animations = new ArrayList<Animation>();
	}

	public Object[] toArray() {
		return animations.toArray();
	}

	public Animation newAnimation(String value) {
		Animation anim = new Animation();
		anim.setName(value);

		notifyNew(anim);

		return anim;
	}

}
