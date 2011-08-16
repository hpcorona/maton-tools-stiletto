package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class AlternatePool extends BasePool<Alternate> {

	@Override
	public Alternate createElement(String name) {
		return new Alternate(name);
	}

}
