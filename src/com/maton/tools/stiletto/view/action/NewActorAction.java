package com.maton.tools.stiletto.view.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.ActorPool;

public class NewActorAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			NewActorAction.class, "document-attribute.png");

	protected Shell shell;
	protected ActorPool pool;

	public NewActorAction(Shell shell, ActorPool pool) {
		this.shell = shell;
		this.pool = pool;

		setText("New Actor");
		setToolTipText("Create a new actor");
		setImageDescriptor(icon);
	}

	public void run() {
		InputDialog dialog = new InputDialog(shell, "Create a new Actor",
				"Enter the name for the new Actor", "actor"
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
