package com.maton.tools.stiletto.view.action;

import java.util.Random;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.SpritePool;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class CloneSpriteAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			CloneSpriteAction.class, "application_double.png");

	protected Shell shell;
	protected SpritePool pool;
	protected DefaultTable<Sprite> spriteTable;

	public CloneSpriteAction(Shell shell, SpritePool pool, DefaultTable<Sprite> table) {
		this.shell = shell;
		this.pool = pool;
		this.spriteTable = table;

		setText("Clone Sprite");
		setToolTipText("Create a new sprite based on another");
		setImageDescriptor(icon);
	}

	public void run() {
		Sprite selected = spriteTable.getSelected();
		if (selected == null) {
			return;
		}
		
		InputDialog dialog = new InputDialog(shell, "Clone a Sprite",
				"Enter the name for the cloned Sprite", "sprite"
						+ Math.abs(new Random(System.currentTimeMillis())
								.nextInt()), new Validator());

		if (dialog.open() == Window.OK) {
			pool.cloneElement(selected.getName(), dialog.getValue());
		}
	}

	class Validator implements IInputValidator {

		@Override
		public String isValid(String arg) {
			return arg.length() > 0 ? null : "Name is too short";
		}

	}
}
