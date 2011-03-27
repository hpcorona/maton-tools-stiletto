package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IAnimationPreviewer;

public class LoopAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			LoopAction.class, "arrow-repeat.png");
	protected IAnimationPreviewer previewer;

	public LoopAction(IAnimationPreviewer previewer) {
		this.previewer = previewer;
		setChecked(previewer.isLoop());
		setText("Loop animation");
		setImageDescriptor(icon);
	}

	public void run() {
		boolean active = !previewer.isLoop();
		previewer.setLoop(active);
	}

}
