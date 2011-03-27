package com.maton.tools.stiletto.model;

import org.eclipse.swt.graphics.GC;

import com.maton.tools.stiletto.model.base.BaseContainer;
import com.maton.tools.stiletto.model.base.IBaseModel;

public class Animation extends BaseContainer<Frame, Sprite> implements IBaseModel {

	private String name;

	public Animation(String name) {
		super();
		this.name = name;
	}

	public void draw(GC g, int x, int y, float rotation, int alpha, int frameIdx) {
		Frame frame = childs.get(frameIdx);
		frame.getSource().draw(g, x, y, rotation, alpha);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFrameForTime(int currentTime) {
		int ntime = currentTime;

		if (childs.size() == 0) {
			return -1;
		}

		int idx = 0;
		for (Frame f : childs) {
			if (ntime < f.getTime()) {
				return idx;
			} else {
				ntime -= f.getTime();
				idx++;
			}
		}

		return getFrameForTime(ntime);
	}

	@Override
	public Frame createChild(Sprite value) {
		return new Frame(value);
	}

}
