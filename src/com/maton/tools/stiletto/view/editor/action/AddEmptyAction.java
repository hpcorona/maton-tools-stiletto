package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Sprite;

public class AddEmptyAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			AddEmptyAction.class, "image-empty.png");
	static final Sprite EMPTY;
	
	static {
		EMPTY = new Sprite();
		EMPTY.setName("< empty >");
	}
	
	protected Animation animation;
	
	public AddEmptyAction(Animation animation) {
		this.animation = animation;
		setText("Add an empty frame");
		setImageDescriptor(icon);
	}
	
	public void run() {
		animation.addFrame(EMPTY);
	}
	
}
