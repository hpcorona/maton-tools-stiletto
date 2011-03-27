package com.maton.tools.stiletto.view.dnd;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;

public class SourceTransferDefault {

	protected Control source;
	protected Transfer type;
	protected boolean sameWidget;
	protected DragSource dragSource;
	protected IDragProvider requester;

	public SourceTransferDefault(Control source, Transfer type,
			boolean sameWidget, IDragProvider requester) {
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
					LocalSelectionTransfer.getTransfer().setSelection(
							new DragSelection(source, event.data));
				}
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
			}
		});
	}

}
