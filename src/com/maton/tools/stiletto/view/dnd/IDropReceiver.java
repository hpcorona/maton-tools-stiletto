package com.maton.tools.stiletto.view.dnd;

import org.eclipse.swt.widgets.Control;

public interface IDropReceiver {
	public void move(Object data, int idx);

	public void drop(Control source, Object data, int idx);
}
