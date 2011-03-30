package com.maton.tools.stiletto.model.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class BaseContainer<T, V> extends ModelEventProvider {

	protected ArrayList<T> childs;

	public BaseContainer() {
		childs = new ArrayList<T>();
	}

	public abstract T createChild(V value);

	public T addChild(V value) {
		T child = createChild(value);

		childs.add(child);
		notifyNew(child);

		return child;
	}

	public int childCount() {
		return childs.size();
	}

	public T getChild(int idx) {
		return childs.get(idx);
	}

	public void removeChild(T child) {
		childs.remove(child);
		notifyDelete(child);
	}

	public void removeChild(int idx) {
		T child = childs.get(idx);
		removeChild(child);
	}

	public void swapChilds(int idx0, int idx1) {
		if (idx1 < 0) {
			idx1 = childs.size() - 1;
		}

		T child0 = childs.get(idx0);
		T child1 = childs.get(idx1);
		childs.set(idx0, childs.get(idx1));
		childs.set(idx1, child0);

		notifySwap(idx0, idx1, child0, child1);
	}

	public void moveChild(int idx0, int idx1) {
		if (idx1 < 0) {
			idx1 = childs.size() - 1;
		}

		T child = childs.get(idx0);

		childs.remove(idx0);
		childs.add(idx1, child);

		notifyMove(idx1, child);
	}

	public Object[] toArray() {
		return childs.toArray();
	}

	public T addChild(V value, int idx) {
		T child = createChild(value);
		childs.add(idx, child);
		notifyInsert(child, idx);

		return child;
	}

	public int indexOf(T child) {
		return childs.indexOf(child);
	}

	public List<T> getList() {
		return Collections.unmodifiableList(childs);
	}
	
	public Object getSelf() {
		return this;
	}

}
