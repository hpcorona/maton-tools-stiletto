package com.maton.tools.stiletto.view;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;

import com.maton.tools.stiletto.view.action.BuildAction;
import com.maton.tools.stiletto.view.action.ExitAction;
import com.maton.tools.stiletto.view.action.NewAction;
import com.maton.tools.stiletto.view.action.OpenAction;
import com.maton.tools.stiletto.view.action.PreferencesAction;
import com.maton.tools.stiletto.view.action.SaveAction;

public class Toolbar {
	
	private static Toolbar INSTANCE;

	protected ApplicationWindow window;
	protected ToolBarManager toolbar;
	protected Action newAction, saveAction, openAction,
			preferencesAction, buildAction, exitAction;

	public Toolbar(ApplicationWindow window, int style) {
		if (INSTANCE != null) {
			throw new RuntimeException("Cannot instance two times the toolbar");
		}
		
		INSTANCE = this;
		
		this.window = window;
		toolbar = new ToolBarManager(style);

		build();
		
		enable(false);
	}

	protected void build() {
		toolbar.add(newAction = new NewAction(window));
		toolbar.add(openAction = new OpenAction(window));
		toolbar.add(saveAction = new SaveAction(window));
		toolbar.add(new Separator());
		toolbar.add(buildAction = new BuildAction(window));
		toolbar.add(new Separator());
		toolbar.add(preferencesAction = new PreferencesAction(window));
		toolbar.add(new Separator());
		toolbar.add(exitAction = new ExitAction(window));
	}
	
	public ToolBarManager getToolbar() {
		return toolbar;
	}
	
	public void enable(boolean enable) {
		saveAction.setEnabled(enable);
		buildAction.setEnabled(enable);
	}
	
	public static Toolbar getInstance() {
		return INSTANCE;
	}
	
}
