package com.maton.tools.stiletto.model.base;

import java.util.ArrayList;

public class NameResolver {
	
	protected ArrayList<IBaseModel> names;
	protected String suffix;
	
	public NameResolver(String suffix) {
		names = new ArrayList<IBaseModel>();
		this.suffix = suffix;
	}
	
	public void add(IBaseModel model) {
		if (isHere(model)) {
			return;
		}
		
		String name = model.getName();
		int idx = 1;
		String newName = null;
		
		while (tryName(name) == false) {
			newName = name + suffix + idx;
			idx++;
		}
		
		if (newName != null) {
			name = newName;
			model.setName(newName);
		}
		
		names.add(model);
	}
	
	private boolean isHere(IBaseModel model) {
		for (IBaseModel m : names) {
			if (m.getSelf() == model.getSelf()) {
				return true;
			}
		}
		
		return false;
	}

	public boolean tryName(String name) {
		for (IBaseModel m : names) {
			if (name.equals(m.getName())) {
				return false;
			}
		}
		
		return true;
	}
	
	public void free() {
		names.clear();
	}
}
