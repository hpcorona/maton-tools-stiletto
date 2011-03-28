package com.maton.tools.stiletto.view.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.FontPool;

public class NewFontAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			NewFontAction.class, "document-attribute.png");

	protected Shell shell;
	protected FontPool pool;

	public NewFontAction(Shell shell, FontPool pool) {
		this.shell = shell;
		this.pool = pool;

		setText("New Font");
		setToolTipText("Create a new font");
		setImageDescriptor(icon);
	}

	public void run() {
		InputDialog dialog = new InputDialog(shell, "Create a new Font",
				"Enter the name for the new Font", "font"
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
