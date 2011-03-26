package com.maton.tools.stiletto.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.maton.tools.stiletto.view.BundleContainer;

public class NewAction extends Action {

	ApplicationWindow window;
	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			NewAction.class, "blue-document.png");

	public NewAction(ApplicationWindow window) {
		this.window = window;

		setText("New Bundle");
		setToolTipText("Create a new bundle");
		setImageDescriptor(icon);
	}

	public void run() {
		BundleContainer.getInstance().newBundle();
	}
}
