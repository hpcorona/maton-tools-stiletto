package com.maton.tools.stiletto.model.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public abstract class ModelEventProvider {
	
	protected Set<IModelListener> modelListeners = new HashSet<IModelListener>();
	
	public void addModelListener(IModelListener listener) {
		modelListeners.add(listener);
	}
	
	public void removeModelListener(IModelListener listener) {
		modelListeners.remove(listener);
	}
	
	public void notifyNew(Object obj) {
		Iterator<IModelListener> iterator = modelListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().notifyNew(obj);
		}
	}
	
	public void notifyChange(Object obj) {
		Iterator<IModelListener> iterator = modelListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().notifyChange(obj);
		}
	}
	
	public void notifyDelete(Object obj) {
		Iterator<IModelListener> iterator = modelListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().notifyDelete(obj);
		}
	}
	
	public void notifyInsert(Object obj, int idx) {
		Iterator<IModelListener> iterator = modelListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().notifyInsert(obj, idx);
		}
	}
	
	public void notifySwap(int idx0, int idx1, Object obj0, Object obj1) {
		if (idx1 >= idx0) {
			notifyDelete(obj1);
			notifyInsert(obj0, idx1);
			notifyDelete(obj0);
			notifyInsert(obj1, idx0);
		} else {
			notifySwap(idx1, idx0, obj1, obj0);
		}
	}
	
	public void notifyMove(int idx1, Object obj) {
		notifyDelete(obj);
		notifyInsert(obj, idx1);
	}
}
