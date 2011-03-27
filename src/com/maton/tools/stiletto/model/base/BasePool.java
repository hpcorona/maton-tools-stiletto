package com.maton.tools.stiletto.model.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class BasePool<T extends IBaseModel> extends ModelEventProvider {

	protected ArrayList<T> elements;
	
	public BasePool() {
		elements = new ArrayList<T>();
	}
	
	public Object[] toArray() {
		return elements.toArray();
	}
	
	public abstract T createElement(String name);

	public T newElement(String name) {
		T elem = createElement(name);
		
		elements.add(elem);
		
		notifyNew(elem);
		
		return elem;
	}
	
	public void removeElement(T element) {
		elements.remove(element);
		notifyDelete(element);
	}
	
	public List<T> getList() {
		return Collections.unmodifiableList(elements);
	}

	public void clear() {
		elements.clear();
	}

	public T getElement(String name) {
		for (T elem : elements) {
			if (elem.getName().equals(name)) {
				return elem;
			}
		}
		
		return null;
	}
	
}
