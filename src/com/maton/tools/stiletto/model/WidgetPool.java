package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class WidgetPool extends BasePool<Widget> {

	protected BundleContext ctx;

	public WidgetPool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public Widget createElement(String name) {
		return new Widget(name);
	}

}
