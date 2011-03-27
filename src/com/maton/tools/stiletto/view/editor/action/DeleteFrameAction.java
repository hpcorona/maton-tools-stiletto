package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Animation;
import com.maton.tools.stiletto.model.Frame;
import com.maton.tools.stiletto.view.table.FrameTable;

public class DeleteFrameAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteFrameAction.class, "eraser.png");
	protected Animation animation;
	protected FrameTable table;

	public DeleteFrameAction(FrameTable table, Animation animation) {
		this.animation = animation;
		this.table = table;

		setText("Delete selected frame");
		setImageDescriptor(icon);
	}

	public void run() {
		Frame frame = table.getSelected();
		if (frame == null) {
			return;
		}

		animation.removeChild(frame);
	}

}
