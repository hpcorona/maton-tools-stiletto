package com.maton.tools.stiletto.view.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Table;

public class SourceTransferDefault {

	protected Table source;
	protected Transfer type;
	protected boolean sameWidget;
	protected DragSource dragSource;
	protected DragRequester requester;

	public SourceTransferDefault(Table source, Transfer type,
			boolean sameWidget, DragRequester requester) {
		this.source = source;
		this.type = type;
		this.sameWidget = sameWidget;
		this.requester = requester;

		build();
	}

	protected void build() {
		int operations = DND.DROP_COPY;

		if (sameWidget) {
			operations = operations | DND.DROP_MOVE;
		}

		dragSource = new DragSource(source, operations);
		Transfer[] trans = new Transfer[] { type };
		dragSource.setTransfer(trans);

		dragSource.addDragListener(new DragSourceListener() {
			@Override
			public void dragStart(DragSourceEvent event) {
				event.doit = requester.canDrag();
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				if (type.isSupportedType(event.dataType)) {
					event.data = requester.drag();
				}
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
			}
		});
	}

}
