package com.maton.tools.stiletto.view.dnd;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;

public class DragSelection implements ISelection {

	protected Control source;
	protected Object object;

	public DragSelection(Control source, Object obj) {
		this.source = source;
		object = obj;
	}

	@Override
	public boolean isEmpty() {
		return object == null;
	}

	public Object getObject() {
		return object;
	}

	public Control getSource() {
		return source;
	}

}
