package com.maton.tools.stiletto.model;

public class Frame {
	private Sprite source;
	private int time;
	
	public Frame(Sprite source) {
		this.source = source;
		time = 100;
	}

	public Sprite getSource() {
		return source;
	}

	public void setSource(Sprite source) {
		this.source = source;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
