package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.model.AlternateFont;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.view.table.AlternateFontsTable;

public class DeleteAlternateFontAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			DeleteAlternateFontAction.class, "eraser.png");
	protected Font font;
	protected AlternateFontsTable table;

	public DeleteAlternateFontAction(AlternateFontsTable table, Font font) {
		this.font = font;
		this.table = table;

		setText("Delete selected alternate");
		setImageDescriptor(icon);
	}

	public void run() {
		AlternateFont alt = table.getSelected();
		if (alt == null) {
			return;
		}

		font.getAlternates().removeElement(alt);
	}

}
