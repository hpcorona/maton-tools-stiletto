package com.maton.tools.stiletto.view;

import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class Application extends ApplicationWindow {

	ImageDescriptor imgIcon = ImageDescriptor.createFromFile(getClass(), "toolbox.png");
	
	protected BundleContainer bundles;
	protected Toolbar toolbar;

	public Application() {
		this(null);
		addToolBar(SWT.BORDER | SWT.WRAP);
	}

	public Application(Shell shell) {
		super(shell);
	}

	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Mat—n Supertools - Stiletto");
	}

	protected void initializeBounds() {
		getShell().setSize(800, 600);
		getShell().setLocation(40, 40);
	}

	protected ToolBarManager createToolBarManager(int style) {
		toolbar = new Toolbar(this, style);
		
		return toolbar.getToolbar();
	}
	
	protected Control createContents(Composite parent) {
		bundles = new BundleContainer(this, parent);
		
		return bundles.getContainer();
	}
	
}
