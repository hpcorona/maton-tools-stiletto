package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Alternate;
import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.view.table.AlternatesTable;

public class DeleteAlternateAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteAlternateAction.class, "eraser.png");
	protected Image image;
	protected AlternatesTable table;

	public DeleteAlternateAction(AlternatesTable table, Image image) {
		this.image = image;
		this.table = table;

		setText("Delete selected alternate");
		setImageDescriptor(icon);
	}

	public void run() {
		Alternate alt = table.getSelected();
		if (alt == null) {
			return;
		}

		image.getAlternates().removeElement(alt);
	}

}
