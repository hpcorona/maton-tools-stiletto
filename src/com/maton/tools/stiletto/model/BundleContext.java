package com.maton.tools.stiletto.model;

import java.io.File;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

public class BundleContext {
	public static final String IMG_PATH = "src";
	public static final String BUILD_PATH = "build";

	public Device device;
	public String projectPath;
	
	public BundleContext(Device device, String projectPath) {
		this.device = device;
		this.projectPath = projectPath;
		
		checkPaths();
	}
	
	protected void checkPaths() {
		String imgPath = getImagePath("");
		File imgFile = new File(imgPath);
		imgFile.mkdir();
		
		String buiPath = getBuildPath("");
		File buiFile = new File(buiPath);
		buiFile.mkdir();
	}

	public Image loadImage(String name) {
		return new Image(device, getImagePath(name));
	}
	
	public String getImagePath(String name) {
		String fileName = projectPath;
		if (!fileName.endsWith(File.separator)) {
			fileName += File.separator;
		}
		fileName += IMG_PATH + File.separator + name;
		
		return fileName;
	}
	
	public String getBuildPath(String name) {
		String fileName = projectPath;
		if (!fileName.endsWith(File.separator)) {
			fileName += File.separator;
		}
		fileName += BUILD_PATH + File.separator + name;
		
		return fileName;
	}
	
}
