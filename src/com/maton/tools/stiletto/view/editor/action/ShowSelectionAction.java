package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IGraphicsEditor;

public class ShowSelectionAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ShowSelectionAction.class, "selection-select.png");
	protected IGraphicsEditor editor;

	public ShowSelectionAction(IGraphicsEditor editor) {
		this.editor = editor;
		setChecked(editor.isShowSelection());
		setText("Show/Hide Selection");
		setImageDescriptor(icon);
	}

	public void run() {
		boolean active = !editor.isShowSelection();
		editor.setShowSelection(active);

		editor.refreshGraphics();
	}

}
