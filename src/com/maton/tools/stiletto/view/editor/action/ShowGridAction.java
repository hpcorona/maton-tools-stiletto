package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IGraphicsEditor;

public class ShowGridAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ShowGridAction.class, "grid-dot.png");
	protected IGraphicsEditor editor;

	public ShowGridAction(IGraphicsEditor editor) {
		this.editor = editor;
		setChecked(editor.isShowGrid());
		setText("Show/Hide Grid");
		setImageDescriptor(icon);
	}

	public void run() {
		boolean active = !editor.isShowGrid();
		editor.setShowGrid(active);

		editor.refreshGraphics();
	}

}
