package com.maton.tools.stiletto.view.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.SpritePool;

public class NewSpriteAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			NewSpriteAction.class, "document-image.png");

	protected Shell shell;
	protected SpritePool pool;

	public NewSpriteAction(Shell shell, SpritePool pool) {
		this.shell = shell;
		this.pool = pool;

		setText("New Sprite");
		setToolTipText("Create a new sprite");
		setImageDescriptor(icon);
	}

	public void run() {
		InputDialog dialog = new InputDialog(shell, "Create a new Sprite",
				"Enter the name for the new Sprite", "sprite"
						+ Math.abs(new Random(System.currentTimeMillis())
								.nextInt()), new Validator());

		if (dialog.open() == Window.OK) {
			pool.newSprite(dialog.getValue());
		}
	}

	class Validator implements IInputValidator {

		@Override
		public String isValid(String arg) {
			return arg.length() > 0 ? null : "Name is too short";
		}

	}
}
