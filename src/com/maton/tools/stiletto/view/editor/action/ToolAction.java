package com.maton.tools.stiletto.view.editor.action;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;

import com.maton.tools.stiletto.view.editor.IBaseEditor;
import com.maton.tools.stiletto.view.editor.ToolType;

public class ToolAction extends Action {

	static ImageDescriptor[] IMAGES = {
			ImageDescriptor.createFromFile(ToolAction.class, "hand.png"),
			ImageDescriptor.createFromFile(ToolAction.class, "arrow-move.png") };

	protected IBaseEditor editor;
	protected ToolType tool;

	public ToolAction(IBaseEditor editor, ToolType tool) {
		this.editor = editor;
		this.tool = tool;

		setText(tool.name());
		setImageDescriptor(IMAGES[tool.ordinal()]);
	}

	public void run() {
		editor.switchTool(tool);
	}

	public ToolType getTool() {
		return tool;
	}

}
