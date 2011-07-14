package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Widget;
import com.maton.tools.stiletto.model.WidgetState;
import com.maton.tools.stiletto.view.table.WidgetStateTable;

public class DeleteWidgetStateAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteWidgetStateAction.class, "eraser.png");
	protected Widget widget;
	protected WidgetStateTable table;

	public DeleteWidgetStateAction(WidgetStateTable table, Widget widget) {
		this.widget = widget;
		this.table = table;

		setText("Delete selected state");
		setImageDescriptor(icon);
	}

	public void run() {
		WidgetState state = table.getSelected();
		if (state == null) {
			return;
		}

		widget.removeChild(state);
	}

}
