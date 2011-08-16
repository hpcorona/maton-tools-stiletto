package com.maton.tools.stiletto.model;

import com.maton.tools.stiletto.model.base.BasePool;

public class AlternateFontPool extends BasePool<AlternateFont> {

	@Override
	public AlternateFont createElement(String name) {
		return new AlternateFont(name);
	}

}
