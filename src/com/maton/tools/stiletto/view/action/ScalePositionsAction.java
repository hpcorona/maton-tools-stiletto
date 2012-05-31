package com.maton.tools.stiletto.view.action;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.Positioned;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.model.SpritePool;

public class ScalePositionsAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ScalePositionsAction.class, "gear.png");

	protected Shell shell;
	protected SpritePool pool;

	public ScalePositionsAction(Shell shell, SpritePool pool) {
		this.shell = shell;
		this.pool = pool;

		setText("Scale all");
		setToolTipText("Scale all positions by an amount (warning, danger)");
		setImageDescriptor(icon);
	}

	public void run() {
		InputDialog dialog = new InputDialog(shell, "Scale all positions",
				"Enter the scale multiplication factor", "1", new Validator());

		if (dialog.open() == Window.OK) {
			float val = Float.parseFloat(dialog.getValue());
			if (val != 0 && val != 1) {
				List<Sprite> sprites = pool.getList();
				for (Sprite spr : sprites) {
					List<Positioned> childs = spr.getList();
					for (Positioned pos : childs) {
						pos.setX((int)(pos.getX() * val));
						pos.setY((int)(pos.getY() * val));
					}
				}
			}
		}
	}

	class Validator implements IInputValidator {

		@Override
		public String isValid(String arg) {
			try {
				Float.parseFloat(arg);
			} catch (Throwable e) {
				return "Please enter a valor decimal number (1 means no scale)";
			}
			
			return null;
		}

	}
}
