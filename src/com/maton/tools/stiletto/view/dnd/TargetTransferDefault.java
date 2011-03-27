package com.maton.tools.stiletto.view.dnd;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TargetTransferDefault {

	protected Control target;
	protected Transfer[] types;
	protected DropTarget dropTarget;
	protected IDropReceiver receiver;

	public TargetTransferDefault(Control target, Transfer[] types,
			IDropReceiver receiver) {
		this.target = target;
		this.types = types;
		this.receiver = receiver;

		build();
	}

	public TargetTransferDefault(Control target, Transfer type,
			IDropReceiver receiver) {
		this(target, new Transfer[] { type }, receiver);
	}

	protected void build() {
		int operations = DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_MOVE;

		dropTarget = new DropTarget(target, operations);
		dropTarget.setTransfer(types);

		dropTarget.addDropListener(new DropTargetListener() {

			@Override
			public void dropAccept(DropTargetEvent event) {
				// Si drag enter acepta, pos ya
			}

			@Override
			public void drop(DropTargetEvent event) {
				int idx = -1;
				if (event.item instanceof TableItem) {
					TableItem item = (TableItem) event.item;
					Table tbl = item.getParent();
					idx = tbl.indexOf(item);
				}

				if (event.data instanceof DragSelection) {
					DragSelection sel = (DragSelection) event.data;
					
					if (sel.getSource() == target) {
						receiver.move(sel.getObject(), idx);
					} else {
						receiver.drop(sel.getSource(), sel.getObject(), idx);
					}
				} else {
					receiver.drop(null, event.data, idx);
				}
			}

			@Override
			public void dragOver(DropTargetEvent event) {
				// Nada, no nos interesa
			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				dragEnter(event); // Revalidamos
			}

			@Override
			public void dragLeave(DropTargetEvent event) {
				// Nada, ya lo hizo todo dragEnter
			}

			@Override
			public void dragEnter(DropTargetEvent event) {
				if (event == null || event.currentDataType == null) {
					event.detail = DND.DROP_NONE;
					return;
				}

				boolean supported = false;

				for (Transfer type : types) {
					TransferData[] types = type.getSupportedTypes();
					for (TransferData td : types) {
						if (td.type == event.currentDataType.type) {
							supported = true;
							break;
						}
					}

					if (supported) {
						break;
					}
				}

				if (supported) {
					event.detail = DND.DROP_COPY;
				} else {
					event.detail = DND.DROP_NONE;
				}
			}
		});

	}

}
