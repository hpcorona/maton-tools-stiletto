package com.maton.tools.stiletto.model.io;

import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.IBaseModel;

public class SpriteImageProxy implements IBaseModel {

	protected Sprite sprite;

	public SpriteImageProxy(Sprite sprite) {
		this.sprite = sprite;
		if (sprite.getImageName() == null
				|| sprite.getImageName().length() == 0) {
			sprite.setImageName(sprite.getName() + "_image");
		}
	}

	@Override
	public String getName() {
		return sprite.getImageName();
	}

	@Override
	public void setName(String name) {
		sprite.setImageName(name);
	}

	public Object getSelf() {
		return sprite;
	}
}
