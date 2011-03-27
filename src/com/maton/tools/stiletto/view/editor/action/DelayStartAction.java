package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IAnimationPreviewer;

public class DelayStartAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DelayStartAction.class, "clock-select.png");
	protected IAnimationPreviewer previewer;

	public DelayStartAction(IAnimationPreviewer previewer) {
		this.previewer = previewer;
		setChecked(previewer.isDelayStart());
		setText("Delay Start");
		setImageDescriptor(icon);
	}

	public void run() {
		boolean active = !previewer.isDelayStart();
		previewer.setDelayStart(active);
	}

}
