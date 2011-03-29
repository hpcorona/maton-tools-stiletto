package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IGraphicsEditor;

public class RefreshAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			RefreshAction.class, "arrow-circle-double.png");
	protected IGraphicsEditor editor;

	public RefreshAction(IGraphicsEditor editor) {
		this.editor = editor;
		setText("Refresh");
		setImageDescriptor(icon);
	}

	public void run() {
		editor.refreshGraphics();
	}

}
