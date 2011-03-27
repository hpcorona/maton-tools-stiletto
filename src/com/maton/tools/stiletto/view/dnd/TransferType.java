package com.maton.tools.stiletto.view.dnd;

import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.base.Positioned;

public class TransferType {

	public static final DefaultTransferType IMAGE;
	public static final DefaultTransferType SPRITE;
	public static final DefaultTransferType ANIMATION;
	public static final DefaultTransferType POSITIONEDIMG;
	public static final DefaultTransferType FRAME;
	public static final DefaultTransferType ACTOR;
	public static final DefaultTransferType ACTION;

	public static final int IMAGE_TYPE;
	public static final int SPRITE_TYPE;
	public static final int ANIMATION_TYPE;
	public static final int POSITIONEDIMG_TYPE;
	public static final int FRAME_TYPE;
	public static final int ACTOR_TYPE;
	public static final int ACTION_TYPE;

	static {
		IMAGE = new DefaultTransferType(Image.class.getName());
		SPRITE = new DefaultTransferType(Sprite.class.getName());
		ANIMATION = new DefaultTransferType(Animation.class.getName());
		POSITIONEDIMG = new DefaultTransferType(Positioned.class.getName() + "_Image");
		FRAME = new DefaultTransferType(Frame.class.getName());
		ACTOR = new DefaultTransferType(Frame.class.getName());
		ACTION = new DefaultTransferType(Frame.class.getName());

		IMAGE_TYPE = IMAGE.getType();
		SPRITE_TYPE = SPRITE.getType();
		ANIMATION_TYPE = ANIMATION.getType();
		POSITIONEDIMG_TYPE = POSITIONEDIMG.getType();
		FRAME_TYPE = FRAME.getType();
		ACTOR_TYPE = ACTOR.getType();
		ACTION_TYPE = ACTION.getType();
	}

}
