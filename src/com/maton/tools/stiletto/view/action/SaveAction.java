package com.maton.tools.stiletto.view.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;

import com.maton.tools.stiletto.view.BundleContainer;
import com.maton.tools.stiletto.view.BundleEditor;

public class SaveAction extends Action {

	ApplicationWindow window;
	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			SaveAction.class, "disk.png");

	public SaveAction(ApplicationWindow window) {
		this.window = window;

		setText("Save");
		setToolTipText("Save the bundle");
		setImageDescriptor(icon);
	}

	public void run() {
		BundleEditor editor = BundleContainer.getInstance().getCurrent();

		if (editor == null)
			return;

		editor.save();
	}
}
