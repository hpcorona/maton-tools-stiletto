package com.maton.tools.stiletto.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

public class PreferencesAction extends Action {

	ApplicationWindow window;
	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			PreferencesAction.class, "gear.png");

	public PreferencesAction(ApplicationWindow window) {
		this.window = window;

		setText("Preferences");
		setToolTipText("Program configuration");
		setImageDescriptor(icon);
	}

	public void run() {
		// TODO Run
	}
}
