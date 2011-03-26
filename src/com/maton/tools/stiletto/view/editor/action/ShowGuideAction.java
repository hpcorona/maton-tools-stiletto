package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IGraphicsEditor;

public class ShowGuideAction extends Action {

	static ImageDescriptor icon = ImageDescriptor.createFromFile(
			ShowGuideAction.class, "guide.png");
	protected IGraphicsEditor editor;

	public ShowGuideAction(IGraphicsEditor editor) {
		this.editor = editor;
		setChecked(editor.isShowGuide());
		setText("Show/Hide Guide");
		setImageDescriptor(icon);
	}

	public void run() {
		boolean active = !editor.isShowGuide();
		editor.setShowGuide(active);

		editor.refreshGraphics();
	}

}
