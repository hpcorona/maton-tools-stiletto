package com.maton.tools.stiletto.view.outline;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

public abstract class DefaultOutline {
	
	protected ExpandBar parent;
	protected ExpandItem item;
	protected Composite container;
	protected ToolBarManager toolbar;
	
	public DefaultOutline(ExpandBar parent, int idx) {
		this.parent = parent;
		item = new ExpandItem(parent, SWT.NONE, idx);
	}
	
	protected abstract Control createControl(Composite parent);
	protected abstract ToolBarManager createToolBarManager(Composite parent);
	
	protected void build() {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, true);
		layout.marginBottom = 0;
		layout.marginHeight = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		layout.marginTop = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		container.setLayout(layout);

		GridData gd = null;
		
		toolbar = createToolBarManager(container);
		if (toolbar != null) {
			gd = new GridData();
			gd.horizontalAlignment = SWT.FILL;
			gd.verticalAlignment = SWT.FILL;
			gd.grabExcessHorizontalSpace = true;
			gd.minimumHeight = 20;
			toolbar.getControl().setLayoutData(gd);
			toolbar.update(true);
		}

		Control child = createControl(container);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		child.setLayoutData(gd);

		item.setControl(container);
	}
}
