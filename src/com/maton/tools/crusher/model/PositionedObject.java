package com.maton.tools.crusher.model;

public class PositionedObject<T> {
	
	private int x, y;

	private T object;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public PositionedObject(int x, int y, T object) {
		super();
		this.x = x;
		this.y = y;
		this.object = object;
	}

}
