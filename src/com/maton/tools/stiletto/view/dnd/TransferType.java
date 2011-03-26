package com.maton.tools.stiletto.view.dnd;

import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;

public class TransferType {

	public static final DefaultTransferType IMAGE;
	public static final DefaultTransferType SPRITE;
	public static final DefaultTransferType ANIMATION;

	public static final int IMAGE_TYPE;
	public static final int SPRITE_TYPE;
	public static final int ANIMATION_TYPE;

	static {
		IMAGE = new DefaultTransferType(Image.class.getName());
		SPRITE = new DefaultTransferType(Sprite.class.getName());
		ANIMATION = new DefaultTransferType(Animation.class.getName());

		IMAGE_TYPE = IMAGE.getType();
		SPRITE_TYPE = SPRITE.getType();
		ANIMATION_TYPE = ANIMATION.getType();
	}

}
