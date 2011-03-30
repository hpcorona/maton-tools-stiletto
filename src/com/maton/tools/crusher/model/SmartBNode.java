package com.maton.tools.crusher.model;

import java.util.ArrayList;

public class SmartBNode<T> {
	
	private BNode<T> bnode1;
	
	private BNode<T> bnode2;
	
	private ArrayList<T> tooBig1;
	
	private ArrayList<T> tooBig2;
	
	private boolean discartNotAdded;
	
	public SmartBNode(int width, int height, boolean discartNodAdded) {
		this(width, height);
		
		this.discartNotAdded = discartNodAdded;
	}
	
	public SmartBNode(int width, int height) {
		bnode1 = new BNode<T>(width, height);
		bnode1.setFillType(FillType.BiggerFirst);
		
		tooBig1 = new ArrayList<T>();
		
		bnode2 = new BNode<T>(width, height);
		bnode2.setFillType(FillType.SmallerFirst);
		
		tooBig2 = new ArrayList<T>();
		
		discartNotAdded = false;
	}
	
	public BNode<T> getWinningBNode() {
		if (discartNotAdded && tooBig1.size() != tooBig2.size()) {
			if (tooBig1.size() > tooBig2.size()) {
				return bnode2;
			}
			return bnode1;
		}
		if (bnode1.getFreeArea() <= bnode2.getFreeArea()) {
			return bnode1;
		}
		
		return bnode2;
	}

	public ArrayList<T> getWinningBigs() {
		if (discartNotAdded && tooBig1.size() != tooBig2.size()) {
			if (tooBig1.size() > tooBig2.size()) {
				return tooBig2;
			}
			return tooBig1;
		}

		if (bnode1.getFreeArea() <= bnode2.getFreeArea()) {
			return tooBig1;
		}
		
		return tooBig2;
	}

	public void append(T object, NodeSize size) {
		if (!bnode1.append(object, size)) {
			tooBig1.add(object);
		}
		if (!bnode2.append(object, size)) {
			tooBig2.add(object);
		}
	}
}
