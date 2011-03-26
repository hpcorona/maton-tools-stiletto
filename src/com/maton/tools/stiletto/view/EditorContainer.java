package com.maton.tools.stiletto.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.maton.tools.stiletto.view.editor.DefaultEditor;
import com.maton.tools.stiletto.view.editor.EditorFactory;

public class EditorContainer {
	
	protected Composite parent;
	protected CTabFolder container;
	
	public EditorContainer(Composite parent) {
		this.parent = parent;
		
		build();
	}
	
	protected void build() {
		container = new CTabFolder(parent, SWT.BORDER);

		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	    container.setSimple(false);
	    container.setUnselectedImageVisible(false);
	    container.setUnselectedCloseVisible(false);
	    
	    container.setMaximizeVisible(true);
	}
	
	public CTabFolder getContainer() {
		return container;
	}
	
	public void save() {
		CTabItem item = container.getSelection();
		
		if (item != null) {
			((DefaultEditor)item.getData()).save();
		}
	}
	
	public void launchEditor(Object obj) {
		CTabItem[] items = container.getItems();
		for (CTabItem item : items) {
			if (item.getData() == obj) {
				container.setSelection(item);
				return;
			}
		}
		
		EditorFactory.launchEditor(container, obj);
	}
}
