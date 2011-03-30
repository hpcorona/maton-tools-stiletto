package com.maton.tools.crusher;

import java.awt.Image;

@SuppressWarnings("rawtypes")
public interface IPart extends Comparable {

	public int getArea();

	public int getWidth();

	public int getHeight();

	public Image getImage();
	
	public void setOutputX(int x);
	
	public void setOutputY(int y);
	
	public String getName();

}
