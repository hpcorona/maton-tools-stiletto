package com.maton.tools.stiletto.model;

public class Frame {
	private Sprite source;
	private float time;
	
	public Frame(Sprite source) {
		this.source = source;
		time = 100.0f;
	}

	public Sprite getSource() {
		return source;
	}

	public void setSource(Sprite source) {
		this.source = source;
	}

	public float getTime() {
		return time;
	}

	public void setTime(float time) {
		this.time = time;
	}
}
