package com.maton.tools.crusher.model;

public class NodeSize implements Comparable<NodeSize> {

	private int width, height;
	
	public NodeSize(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getArea() {
		return width * height;
	}

	@Override
	public int compareTo(NodeSize size) {
		if (getArea() > size.getArea()) {
			return 1;
		} else if (getArea() < size.getArea()) {
			return -1;
		}

		return 0;
	}
	
}
