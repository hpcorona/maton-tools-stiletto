package com.maton.tools.stiletto.model;

import java.util.ArrayList;

import org.eclipse.swt.graphics.GC;

public class Animation extends ModelEventProvider {
	private String name;
	private ArrayList<Frame> frames;

	public Animation() {
		name = "unnamed";
		frames = new ArrayList<Frame>();
	}

	public void draw(GC g, int x, int y, float rotation, int alpha, int frameIdx) {
		Frame frame = frames.get(frameIdx);
		frame.getSource().draw(g, x, y, rotation, alpha);
	}

	public Frame addFrame(Sprite img) {
		Frame pos = new Frame(img);
		frames.add(pos);
		notifyNew(pos);

		return pos;
	}

	public int frameCount() {
		return frames.size();
	}

	public Frame getFrame(int idx) {
		return frames.get(idx);
	}

	public void removeFrame(Frame img) {
		frames.remove(img);
		notifyDelete(img);
	}

	public void removeFrame(int idx) {
		Frame pos = frames.get(idx);
		removeFrame(pos);
	}

	public void swapFrames(int idx0, int idx1) {
		if (idx1 < 0) {
			idx1 = frames.size() - 1;
		}

		Frame img0 = frames.get(idx0);
		Frame img1 = frames.get(idx1);
		frames.set(idx0, frames.get(idx1));
		frames.set(idx1, img0);

		notifySwap(idx0, idx1, img0, img1);
	}

	public void moveFrame(int idx0, int idx1) {
		if (idx1 < 0) {
			idx1 = frames.size() - 1;
		}

		Frame img = frames.get(idx0);

		frames.remove(idx0);
		frames.add(idx1, img);

		notifyMove(idx1, img);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object[] toArray() {
		return frames.toArray();
	}

	public Frame addFrame(Sprite img, int idx) {
		Frame pos = new Frame(img);
		frames.add(idx, pos);
		notifyInsert(pos, idx);

		return pos;
	}

	public int indexOf(Frame img) {
		return frames.indexOf(img);
	}
	
	public int getFrameForTime(int currentTime) {
		int ntime = currentTime;
		
		if (frames.size() == 0) {
			return -1;
		}
		
		int idx = 0;
		for (Frame f : frames) {
			if (ntime < f.getTime()) {
				return idx;
			} else {
				ntime -= f.getTime();
				idx++;
			}
		}
		
		return getFrameForTime(ntime);
	}

}
