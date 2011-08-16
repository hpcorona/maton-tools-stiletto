package com.maton.tools.stiletto.model;

import java.io.File;
import java.io.FilenameFilter;

import com.maton.tools.stiletto.model.base.BasePool;

public class ResolutionPool extends BasePool<Resolution> {
	
	protected BundleContext ctx;
	
	public ResolutionPool(BundleContext ctx) {
		super();
		this.ctx = ctx;
	}

	@Override
	public Resolution createElement(String name) {
		return new Resolution(name);
	}
	
	public String[] getAlternates() {
		File f = new File(ctx.getResolutionPath(""));
		File[] pngs = f.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File arg0, String arg1) {
				return arg1.toLowerCase().endsWith(".png");
			}
		});
		
		String[] alts = new String[pngs.length];
		for (int i = 0; i < pngs.length; i++) {
			String fn = pngs[i].getName();
			alts[i] = fn.substring(0, fn.length() - 4);
		}

		return alts;
	}
	
}
