package com.maton.tools.stiletto.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

public class ExitAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ExitAction.class, "door-open-in.png");

	ApplicationWindow window;

	public ExitAction(ApplicationWindow window) {
		this.window = window;

		setText("Exit");
		setToolTipText("Exit Application");
		setImageDescriptor(icon);
	}

	public void run() {
		window.close();
	}

}
