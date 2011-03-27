package com.maton.tools.stiletto.view.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.AnimationPool;

public class NewAnimationAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			NewAnimationAction.class, "document-film.png");

	protected Shell shell;
	protected AnimationPool pool;

	public NewAnimationAction(Shell shell, AnimationPool pool) {
		this.shell = shell;
		this.pool = pool;

		setText("New Animation");
		setToolTipText("Create a new animation");
		setImageDescriptor(icon);
	}

	public void run() {
		InputDialog dialog = new InputDialog(shell, "Create a new Animation",
				"Enter the name for the new Animation", "animation"
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
