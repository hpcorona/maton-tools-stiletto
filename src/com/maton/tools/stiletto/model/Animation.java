package com.maton.tools.stiletto.model;

import java.util.ArrayList;

public class Animation {
	private String name;
	private ArrayList<Frame> frames;
	
	public Animation() {
		name = "unnamed";
		frames = new ArrayList<Frame>();
	}
	
	public void addFrame(Sprite spr) {
		frames.add(new Frame(spr));
	}
	
	public int frameCount() {
		return frames.size();
	}
	
	public Frame getFrame(int idx) {
		return frames.get(idx);
	}
	
	public void removeFrame(int idx) {
		frames.remove(idx);
	}
	
	public void swapFrames(int idx0, int idx1) {
		Frame img0 = frames.get(idx0);
		frames.set(idx0, frames.get(idx1));
		frames.set(idx1, img0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
