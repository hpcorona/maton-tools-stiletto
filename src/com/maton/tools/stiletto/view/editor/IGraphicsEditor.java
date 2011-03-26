package com.maton.tools.stiletto.view.editor;

public interface IGraphicsEditor {
	public void setShowGuide(boolean showGuide);

	public void setShowGrid(boolean showGrid);

	public boolean isShowGrid();

	public boolean isShowGuide();

	public void refreshGraphics();
}
