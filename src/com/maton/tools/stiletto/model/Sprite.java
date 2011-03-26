package com.maton.tools.stiletto.model;

import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;

public class Sprite extends ModelEventProvider implements Drawable {
	private String name;
	private boolean rendered;
	private ArrayList<Positioned<Image>> images;
	private String imageName;

	public Sprite() {
		name = "untitled";
		rendered = false;
		images = new ArrayList<Positioned<Image>>();
	}

	@Override
	public void draw(GC g, int x, int y, float rotation, int alpha) {
		for (Positioned<Image> img : images) {
			img.draw(g, x, y, rotation, alpha);
		}
	}

	public void addImage(Image img) {
		Positioned<Image> pos = new Positioned<Image>(img);
		images.add(pos);
		notifyNew(pos);
	}

	public int imageCount() {
		return images.size();
	}

	public Positioned<Image> getImage(int idx) {
		return images.get(idx);
	}

	public void removeImage(int idx) {
		Positioned<Image> pos = images.get(idx);
		images.remove(idx);
		notifyDelete(pos);
	}

	public void swapImages(int idx0, int idx1) {
		Positioned<Image> img0 = images.get(idx0);
		images.set(idx0, images.get(idx1));
		images.set(idx1, img0);
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

	public Object[] toArray() {
		return images.toArray();
	}
}
