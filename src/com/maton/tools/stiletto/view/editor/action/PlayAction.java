package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IAnimationPreviewer;

public class PlayAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			PlayAction.class, "control.png");
	protected IAnimationPreviewer previewer;

	public PlayAction(IAnimationPreviewer previewer) {
		this.previewer = previewer;
		setChecked(previewer.isPlay());
		setText("Play/Stop animation");
		setImageDescriptor(icon);
	}

	public void run() {
		boolean active = !previewer.isPlay();
		previewer.setPlay(active);
	}

}
