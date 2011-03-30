package com.maton.tools.stiletto.view.action;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.view.table.DefaultTable;

public class DeleteAction<T extends IBaseModel> extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteAction.class, "eraser.png");

	protected Shell shell;
	protected DefaultTable<T> table;
	protected Bundle bundle;

	public DeleteAction(Shell shell, Bundle bundle, DefaultTable<T> table) {
		this.shell = shell;
		this.table = table;
		this.bundle = bundle;

		setText("Remove the selected item");
		setImageDescriptor(icon);
	}

	public void run() {
		T item = table.getSelected();
		if (item == null) {
			return;
		}

		List<IBaseModel> uses = bundle.findUses(item);

		String message = "The selected item "
				+ item.getName()
				+ " will be removed from this bundle.\n\nThis action is IRREVERSIBLE.";

		if (uses.size() > 0) {
			message += "\n\nThe following elements depends on the item that you want to delete:";

			String items = "";
			for (IBaseModel mod : uses) {
				if (items.length() > 0) {
					items += ", ";
				}

				items += mod.getClass().getSimpleName() + " with name "
						+ mod.getName();
			}

			message += "\n" + items;
		}

		message += "\n\nDo you want to continue?";

		MessageDialog dialog = new MessageDialog(shell, "Remove "
				+ item.getName() + "?", icon.createImage(), message,
				SWT.ICON_WARNING, new String[] { "Yes", "No" }, 1);
		if (dialog.open() == 0) {
			bundle.remove(item);
		}
	}
}
