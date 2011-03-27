package com.maton.tools.stiletto.model.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.maton.tools.stiletto.model.Bundle;

public class ModelOutput {
	public static boolean save(Bundle bundle) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty("resource.loader", "mine"); 
		ve.setProperty("mine.resource.loader.instance", new InternalResourceLoader()); 

		VelocityContext ctx = new VelocityContext();
		ctx.put("bundle", bundle);

		Template tpl = ve.getTemplate("output.vm");

		OutputStream out = null;
		try {
			out = new FileOutputStream(bundle.getFile());
			OutputStreamWriter osw = new OutputStreamWriter(out);

			tpl.merge(ctx, osw);
			
			osw.flush();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				return false;
			}
		}

		return true;
	}
}
