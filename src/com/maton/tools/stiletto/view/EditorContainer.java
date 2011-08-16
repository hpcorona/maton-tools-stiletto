package com.maton.tools.stiletto.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.view.editor.DefaultEditor;
import com.maton.tools.stiletto.view.editor.EditorFactory;

public class EditorContainer {

	protected Composite parent;
	protected CTabFolder container;
	protected Bundle bundle;

	public EditorContainer(Composite parent, Bundle bundle) {
		this.parent = parent;
		this.bundle = bundle;

		build();
	}

	protected void build() {
		container = new CTabFolder(parent, SWT.BORDER);
		container.setData(bundle);

		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		container.setSimple(false);
		container.setUnselectedImageVisible(false);
		container.setUnselectedCloseVisible(false);
		container.setUnselectedCloseVisible(true);
		
		container.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				DefaultEditor editor = (DefaultEditor)((CTabItem)event.item).getData();
				
				editor.dispose();
				super.close(event);
			}
		});
	}

	public CTabFolder getContainer() {
		return container;
	}

	public void save() {
		CTabItem item = container.getSelection();

		if (item != null) {
			((DefaultEditor) item.getData()).save();
		}
	}

	public void launchEditor(Object obj) {
		CTabItem[] items = container.getItems();
		for (CTabItem item : items) {
			DefaultEditor editor = (DefaultEditor)item.getData();
			if (editor.getData() == obj) {
				container.setSelection(item);
				return;
			}
		}

		EditorFactory.launchEditor(container, obj);
	}
}
