package com.maton.tools.stiletto.model;

public class CharMetric {

	public char letter;
	public int width;
	public int height;
	public int x;
	public int y;
	public int xadvance;
	public int lineHeight;

	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
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

	public int getXadvance() {
		return xadvance;
	}

	public void setXadvance(int xadvance) {
		this.xadvance = xadvance;
	}

	public int getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}

	public int getIntChar() {
		return (int) letter;
	}
}
