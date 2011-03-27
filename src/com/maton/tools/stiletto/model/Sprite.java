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

	public Positioned<Image> addImage(Image img) {
		Positioned<Image> pos = new Positioned<Image>(img);
		images.add(pos);
		notifyNew(pos);
		
		return pos;
	}

	public int imageCount() {
		return images.size();
	}

	public Positioned<Image> getImage(int idx) {
		return images.get(idx);
	}
	
	public void removeImage(Positioned<Image> img) {
		images.remove(img);
		notifyDelete(img);
	}

	public void removeImage(int idx) {
		Positioned<Image> pos = images.get(idx);
		removeImage(pos);
	}

	public void swapImages(int idx0, int idx1) {
		if (idx1 < 0) {
			idx1 = images.size() - 1;
		}
		
		Positioned<Image> img0 = images.get(idx0);
		Positioned<Image> img1 = images.get(idx1);
		images.set(idx0, images.get(idx1));
		images.set(idx1, img0);
		
		notifySwap(idx0, idx1, img0, img1);
	}
	
	public void moveImage(int idx0, int idx1) {
		if (idx1 < 0) {
			idx1 = images.size() - 1;
		}
		
		Positioned<Image> img = images.get(idx0);
		
		images.remove(idx0);
		images.add(idx1, img);
		
		notifyMove(idx1, img);
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

	public Positioned<Image> addImage(Image img, int idx) {
		Positioned<Image> pos = new Positioned<Image>(img);
		images.add(idx, pos);
		notifyInsert(pos, idx);
		
		return pos;
	}
	
	public int indexOf(Positioned<Image> img) {
		return images.indexOf(img);
	}

}
