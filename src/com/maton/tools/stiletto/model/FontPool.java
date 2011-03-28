package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class FontPool extends BasePool<Font> {
	
	protected BundleContext ctx;
	
	public FontPool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public Font createElement(String name) {
		return new Font(name);
	}
	
}
