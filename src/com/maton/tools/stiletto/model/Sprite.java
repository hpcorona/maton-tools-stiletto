package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;

import com.maton.tools.stiletto.model.base.BaseContainer;
import com.maton.tools.stiletto.model.base.Drawable;
import com.maton.tools.stiletto.model.base.IBaseModel;
import com.maton.tools.stiletto.model.base.Positioned;

public class Sprite extends BaseContainer<Positioned<Image>, Image> implements
		Drawable, IBaseModel {

	private String name;
	private boolean rendered;
	private String imageName;

	public Sprite(String name) {
		super();
		this.name = name;
		rendered = false;
		imageName = "";
	}

	@Override
	public void draw(GC g, int x, int y, float rotation, int alpha) {
		for (Positioned<Image> img : childs) {
			img.draw(g, x, y, rotation, alpha);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isRendered() {
		return rendered;
	}

	public void setRendered(boolean rendered) {
		this.rendered = rendered;

		if (rendered && (imageName == null || imageName.length() == 0)) {
			imageName = name;
		}
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@Override
	public Positioned<Image> createChild(Image value) {
		return new Positioned<Image>(value);
	}

}
