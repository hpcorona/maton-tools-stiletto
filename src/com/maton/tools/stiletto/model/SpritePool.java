package com.maton.tools.stiletto.model;

import java.util.ArrayList;

public class SpritePool extends ModelEventProvider {

	protected BundleContext ctx;
	protected ArrayList<Sprite> sprites;
	
	public SpritePool(BundleContext ctx) {
		this.ctx = ctx;
		sprites = new ArrayList<Sprite>();
	}
	
	public Object[] toArray() {
		return sprites.toArray();
	}

	public Sprite newSprite(String value) {
		Sprite spr = new Sprite();
		spr.setName(value);
		
		notifyNew(spr);
		
		return spr;
	}
	
}
