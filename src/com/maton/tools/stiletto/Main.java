package com.maton.tools.stiletto;

import org.eclipse.swt.widgets.Display;

import com.maton.tools.stiletto.view.Application;

public class Main {
	public static void main(String[] args) {
		Application app = new Application();
		app.setBlockOnOpen(true);
		app.open();
		Display.getCurrent().dispose();
	}
}
