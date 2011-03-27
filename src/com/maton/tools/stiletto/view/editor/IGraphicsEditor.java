package com.maton.tools.stiletto.view.editor;

public interface IGraphicsEditor {
	public void setShowGuide(boolean showGuide);

	public void setShowGrid(boolean showGrid);
	
	public void setShowSelection(boolean showSelection);

	public boolean isShowGrid();

	public boolean isShowGuide();
	
	public boolean isShowSelection();

	public void refreshGraphics();
}
