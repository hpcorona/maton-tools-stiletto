package com.maton.tools.stiletto.model.base;

public interface IModelListener {
	public void notifyInsert(Object obj, int idx);
	public void notifyNew(Object obj);
	public void notifyChange(Object obj);
	public void notifyDelete(Object obj);
}
