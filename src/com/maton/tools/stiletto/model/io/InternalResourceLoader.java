package com.maton.tools.stiletto.model.io;

import java.io.InputStream;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;

public class InternalResourceLoader extends FileResourceLoader {
	@Override
	public InputStream getResourceStream(String name)
			throws ResourceNotFoundException {
		InputStream is = InternalResourceLoader.class.getResourceAsStream(name);
		
		if (is == null) {
			throw new ResourceNotFoundException("Cannot find " + name);
		}
		
		return is;
	}
}
