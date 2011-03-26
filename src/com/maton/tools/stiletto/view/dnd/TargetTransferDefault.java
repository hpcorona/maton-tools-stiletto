package com.maton.tools.stiletto.view.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Control;

public class TargetTransferDefault {

	protected Control target;
	protected Transfer type;
	protected DropTarget dropTarget;
	protected DropReceiver receiver;

	public TargetTransferDefault(Control target, Transfer type,
			DropReceiver receiver) {
		this.target = target;
		this.type = type;
		this.receiver = receiver;

		build();
	}

	protected void build() {
		int operations = DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_MOVE;

		dropTarget = new DropTarget(target, operations);
		Transfer[] trans = new Transfer[] { type };
		dropTarget.setTransfer(trans);
		
		dropTarget.addDropListener(new DropTargetListener() {

			@Override
			public void dropAccept(DropTargetEvent event) {
				System.out.println("accept");
				// Si drag enter acepta, pos ya
			}

			@Override
			public void drop(DropTargetEvent event) {
				System.out.println("drop");
				receiver.drop(event);
			}

			@Override
			public void dragOver(DropTargetEvent event) {
				System.out.println("over");
				event.feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_SELECT;
				// Nada, no nos interesa
			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				System.out.println("Changed");
				dragEnter(event); // Revalidamos
			}

			@Override
			public void dragLeave(DropTargetEvent event) {
				System.out.println("leave");
				// Nada, ya lo hizo todo dragEnter
			}

			@Override
			public void dragEnter(DropTargetEvent event) {
				System.out.println("dragEnter");
				if (event == null || event.currentDataType == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				boolean supported = false;

				TransferData[] types = type.getSupportedTypes();
				for (TransferData td : types) {
					if (td.type == event.currentDataType.type) {
						supported = true;
						break;
					}
				}

				System.out.println(supported);

				if (supported) {
					event.detail = DND.DROP_COPY;
				} else {
					event.detail = DND.DROP_NONE;
				}
			}
		});

	}

}
