package com.maton.tools.stiletto.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.maton.tools.stiletto.view.BundleContainer;

public class OpenAction extends Action {

	ApplicationWindow window;
	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			OpenAction.class, "blue-folder-horizontal.png");

	public OpenAction(ApplicationWindow window) {
		this.window = window;

		setText("Open");
		setToolTipText("Open an existing bundle");
		setImageDescriptor(icon);
	}

	public void run() {
		BundleContainer.getInstance().openBundle();
	}
}
