package com.maton.tools.stiletto.view.dnd;

import com.maton.tools.stiletto.model.Action;
import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.Positioned;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.Widget;
import com.maton.tools.stiletto.model.WidgetState;

public class TransferType {

	public static final DefaultTransferType IMAGE;
	public static final DefaultTransferType FRAMED;
	public static final DefaultTransferType SPRITE;
	public static final DefaultTransferType ANIMATION;
	public static final DefaultTransferType POSITIONEDIMG;
	public static final DefaultTransferType FRAME;
	public static final DefaultTransferType ACTOR;
	public static final DefaultTransferType ACTION;
	public static final DefaultTransferType WIDGETSTATE;
	public static final DefaultTransferType WIDGET;

	public static final int IMAGE_TYPE;
	public static final int FRAMED_TYPE;
	public static final int SPRITE_TYPE;
	public static final int ANIMATION_TYPE;
	public static final int POSITIONEDIMG_TYPE;
	public static final int FRAME_TYPE;
	public static final int ACTOR_TYPE;
	public static final int ACTION_TYPE;
	public static final int WIDGETSTATE_TYPE;
	public static final int WIDGET_TYPE;

	static {
		IMAGE = new DefaultTransferType(Image.class.getName());
		FRAMED = new DefaultTransferType(Image.class.getName() + "_Framed");
		SPRITE = new DefaultTransferType(Sprite.class.getName());
		ANIMATION = new DefaultTransferType(Animation.class.getName());
		POSITIONEDIMG = new DefaultTransferType(Positioned.class.getName() + "_Image");
		FRAME = new DefaultTransferType(Frame.class.getName());
		ACTOR = new DefaultTransferType(Actor.class.getName());
		ACTION = new DefaultTransferType(Action.class.getName());
		WIDGETSTATE = new DefaultTransferType(WidgetState.class.getName() + "_Image");
		WIDGET = new DefaultTransferType(Widget.class.getName());

		IMAGE_TYPE = IMAGE.getType();
		FRAMED_TYPE = FRAMED.getType();
		SPRITE_TYPE = SPRITE.getType();
		ANIMATION_TYPE = ANIMATION.getType();
		POSITIONEDIMG_TYPE = POSITIONEDIMG.getType();
		FRAME_TYPE = FRAME.getType();
		ACTOR_TYPE = ACTOR.getType();
		ACTION_TYPE = ACTION.getType();
		WIDGETSTATE_TYPE = WIDGETSTATE.getType();
		WIDGET_TYPE = WIDGET.getType();
	}

}
