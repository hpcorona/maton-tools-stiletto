package com.maton.tools.stiletto.model;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

public class BundleContext {
	public static final String IMG_PATH = "src";
	public static final String RES_PATH = "resolution";
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

		String resPath = getResolutionPath("");
		File resFile = new File(resPath);
		resFile.mkdir();
	}

	public Image loadImage(String name) {
		return new Image(device, getImagePath(name));
	}

	public Image loadAlternate(String name) {
		return new Image(device, getResolutionPath(name));
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

	public String getResolutionPath(String name) {
		String fileName = projectPath;
		if (!fileName.endsWith(File.separator)) {
			fileName += File.separator;
		}
		fileName += RES_PATH + File.separator + name;
		
		return fileName;
	}

	public java.awt.Image loadAwtImage(String name) {
		try {
			return ImageIO.read(new File(getImagePath(name)));
		} catch (IOException e) {
			return null;
		}
	}
	
	public java.awt.Image loadAwtAlternate(String name) {
		try {
			return ImageIO.read(new File(getResolutionPath(name)));
		} catch (IOException e) {
			return null;
		}
	}
}
