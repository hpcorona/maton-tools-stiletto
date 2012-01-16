package com.maton.tools.stiletto.cmd;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.maton.tools.stiletto.cmd.helper.CommandProcessMonitor;
import com.maton.tools.stiletto.model.Bundle;
import com.maton.tools.stiletto.model.Font;
import com.maton.tools.stiletto.model.Resolution;
import com.maton.tools.stiletto.process.BundleBuilder;

public class Build {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage:");
			System.out.println("\t java com.maton.tools.stiletto.cmd.Build <STILETTO FILE>");
			System.exit(1);
		}
		File bndl = new File(args[0]);
		if (!bndl.exists()) {
			System.out.println("File not found: " + bndl.getAbsolutePath());
			System.exit(1);
		}
		
		Bundle bundle = new Bundle(null, bndl);
		System.out.println(args[0]);

		boolean fontErr = false;
		List<Font> fonts = bundle.getFonts().getList();
		for (Font font : fonts) {
			if (!font.isValid()) {
				System.err.println("Font not installed: " + font.getFace());
				fontErr = true;
			}
		}
		
		if (fontErr) {
			System.exit(1);
		}
		
		try {
			BundleBuilder bldr = new BundleBuilder(bundle, null);
			bldr.run(CommandProcessMonitor.getInstance());
			
			List<Resolution> lst =  bundle.getResolutions().getList();
			for (Resolution res : lst) {
				bldr = new BundleBuilder(bundle, res);
				bldr.run(CommandProcessMonitor.getInstance());
			}
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("=== ALL DONE ===");
		System.exit(0);
	}
}
