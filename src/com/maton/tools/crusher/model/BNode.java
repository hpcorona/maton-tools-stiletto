package com.maton.tools.crusher.model;

import java.util.ArrayList;

public class BNode<T> {

	private int x, y, width, height;
	
	private T object;
	
	private BNode<T> node1, node2;
	
	private FillType fillType = FillType.BiggerFirst;

	public BNode(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public BNode(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	public boolean append(T obj, NodeSize size) {
		if (!canBeInside(size)) {
			return false;
		} 
		
		if (object == null) {
			object = obj;
			
			createNewNodes(size);
			
			return true;
		} else {
			if (node1.canBeInside(size) && node2.canBeInside(size)) {
				if (node1.getArea() < node2.getArea()) {
					if (fillType == FillType.BiggerFirst) {
						return node2.append(obj, size);
					} else {
						return node1.append(obj, size);
					}
				} else {
					if (fillType == FillType.SmallerFirst) {
						return node2.append(obj, size);
					} else {
						return node1.append(obj, size);
					}
				}
			} else if (node1.canBeInside(size)) {
				return node1.append(obj, size);
			} else if (node2.canBeInside(size)) {
				return node2.append(obj, size);
			} else {
				return false;
			}
		}
	}
	
	public int getArea() {
		return width * height;
	}

	public boolean canBeInside(NodeSize size) {
		if (object == null) {
			return width >= size.getWidth() && height >= size.getHeight();
		} else {
			return node1.canBeInside(size) || node2.canBeInside(size);
		}
	}
	
	private void createNewNodes(NodeSize size) {
		if (node1 != null || node2 != null) {
			throw new RuntimeException("Cannot re-create the child empty nodes");
		}
		
		int d1 = (int)Math.abs(((width - size.getWidth()) * size.getHeight()) - (width * (height - size.getHeight())));
		int d2 = (int)Math.abs(((size.getWidth() * (height - size.getHeight())) - (height * (width - size.getWidth()))));
		
		if (d1 > d2) {
			node1 = new BNode<T>(x + size.getWidth(), y, width - size.getWidth(), size.getHeight());
			node2 = new BNode<T>(x, y + size.getHeight(), width, height - size.getHeight());
		} else {
			node1 = new BNode<T>(x + size.getWidth(), y, width - size.getWidth(), height);
			node2 = new BNode<T>(x, y + size.getHeight(), size.getWidth(), height - size.getHeight());
		}
	}
	
	public ArrayList<PositionedObject<T>> getObjectPositions() {
		ArrayList<PositionedObject<T>> objs = new ArrayList<PositionedObject<T>>();
		
		fillPositions(objs);
		
		return objs;
	}
	
	private void fillPositions(ArrayList<PositionedObject<T>> objs) {
		if (object != null) {
			objs.add(new PositionedObject<T>(x, y, object));
			
			node1.fillPositions(objs);
			node2.fillPositions(objs);
		}
	}
	
	public int getFreeArea() {
		if (object == null) {
			return getArea();
		} else {
			return node1.getFreeArea() + node2.getFreeArea();
		}
	}

	public FillType getFillType() {
		return fillType;
	}

	public void setFillType(FillType fillType) {
		this.fillType = fillType;
	}
	
}
