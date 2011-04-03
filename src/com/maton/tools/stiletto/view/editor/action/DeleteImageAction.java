package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.Positioned;
import com.maton.tools.stiletto.model.Sprite;
import com.maton.tools.stiletto.view.table.PositionedTable;

public class DeleteImageAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteImageAction.class, "eraser.png");
	protected Sprite sprite;
	protected PositionedTable table;

	public DeleteImageAction(PositionedTable table, Sprite sprite) {
		this.sprite = sprite;
		this.table = table;

		setText("Delete selected image");
		setImageDescriptor(icon);
	}

	public void run() {
		Positioned img = table.getSelected();
		if (img == null) {
			return;
		}

		sprite.removeChild(img);
	}

}
