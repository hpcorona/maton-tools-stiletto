package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Actor;
import com.maton.tools.stiletto.view.table.ActionTable;

public class DeleteActionAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteActionAction.class, "eraser.png");
	protected Actor actor;
	protected ActionTable table;

	public DeleteActionAction(ActionTable table, Actor actor) {
		this.actor = actor;
		this.table = table;

		setText("Delete selected action");
		setImageDescriptor(icon);
	}

	public void run() {
		com.maton.tools.stiletto.model.Action action = table.getSelected();
		if (action == null) {
			return;
		}

		actor.removeChild(action);
	}

}
