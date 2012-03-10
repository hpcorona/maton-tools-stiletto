package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class SpritePool extends BasePool<Sprite> {

	public static final Sprite EMPTY = new Sprite("_ empty _");
	
	protected BundleContext ctx;
	
	public SpritePool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}
	
	@Override
	public Sprite getElement(String name) {
		if (name.equals(EMPTY.getName())) {
			return EMPTY;
		}
		
		Sprite spr = super.getElement(name);
		if (spr == null) {
			spr = createElement(name);
		}
		return spr;
	}

	@Override
	public Sprite createElement(String name) {
		return new Sprite(name);
	}
	
	public Sprite cloneElement(String original, String newelement) {
		Sprite orig = getElement(original);
		if (orig == null) {
			return null;
		}
		
		Sprite newelem = createElement(newelement);
		for (Positioned pos : orig.getList()) {
			Positioned newpos = newelem.addChild(pos.getSource());
			newpos.setAlpha(pos.getAlpha());
			newpos.setFlipX(pos.isFlipX());
			newpos.setFlipY(pos.isFlipY());
			newpos.setRotation(pos.getRotation());
			newpos.setX(pos.getX());
			newpos.setY(pos.getY());
		}
		
		elements.add(newelem);
		
		notifyNew(newelem);
		
		return newelem;
	}
	
}
