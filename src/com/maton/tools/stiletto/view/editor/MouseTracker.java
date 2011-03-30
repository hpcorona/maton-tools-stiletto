package com.maton.tools.stiletto.view.editor;

public class MouseTracker {
	
	protected IBaseEditor editor;
	protected int x, y;
	protected boolean active;
	
	public MouseTracker(IBaseEditor editor) {
		this.editor = editor;
		active = false;
	}
	
	
	public void mouseDown(int x, int y) {
		this.x = x;
		this.y = y;
		active = true;
	}
	
	public void mouseMove(int x, int y) {
		if (active) {
			int dX = x - this.x;
			int dY = y - this.y;
			
			editor.move(dX, dY);
			
			this.x = x;
			this.y = y;
		}
	}
	
	public void mouseUp() {
		active = false;
	}


	public boolean isActive() {
		return active;
	}
	
}
