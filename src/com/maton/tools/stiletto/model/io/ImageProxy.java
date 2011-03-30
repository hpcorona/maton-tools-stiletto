package com.maton.tools.stiletto.model.io;

import com.maton.tools.stiletto.model.Image;
import com.maton.tools.stiletto.model.base.IBaseModel;

public class ImageProxy implements IBaseModel {

	protected Image image;

	public ImageProxy(Image image) {
		this.image = image;
		if (image.getExportName() == null
				|| image.getExportName().length() == 0) {
			String name = image.getName();
			int idx = name.lastIndexOf('.');
			image.setExportName(name.substring(0, idx));
		}
	}

	@Override
	public String getName() {
		return image.getExportName();
	}

	@Override
	public void setName(String name) {
		image.setExportName(name);
	}

	public Object getSelf() {
		return image;
	}
}
