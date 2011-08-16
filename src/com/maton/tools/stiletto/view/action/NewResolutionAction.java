package com.maton.tools.stiletto.view.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.ResolutionPool;

public class NewResolutionAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			NewResolutionAction.class, "document-attribute-r.png");

	protected Shell shell;
	protected ResolutionPool pool;

	public NewResolutionAction(Shell shell, ResolutionPool pool) {
		this.shell = shell;
		this.pool = pool;

		setText("New Resolution");
		setToolTipText("Create a new resolution");
		setImageDescriptor(icon);
	}

	public void run() {
		InputDialog dialog = new InputDialog(shell, "Create a new Resolution",
				"Enter the name for the new Resolution", "resolution"
						+ Math.abs(new Random(System.currentTimeMillis())
								.nextInt()), new Validator());

		if (dialog.open() == Window.OK) {
			pool.newElement(dialog.getValue());
		}
	}

	class Validator implements IInputValidator {

		@Override
		public String isValid(String arg) {
			return arg.length() > 0 ? null : "Name is too short";
		}

	}
}
