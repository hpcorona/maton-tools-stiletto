package com.maton.tools.stiletto.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.ImagePool;

public class RefreshPoolAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			RefreshPoolAction.class, "arrow-circle-double.png");

	protected ImagePool pool;

	public RefreshPoolAction(ImagePool pool) {
		this.pool = pool;

		setText("Refresh images");
		setToolTipText("Refresh images from source");
		setImageDescriptor(icon);
	}

	public void run() {
		pool.reload();
	}
}
