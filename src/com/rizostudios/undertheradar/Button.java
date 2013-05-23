package com.rizostudios.undertheradar;

public abstract class Button {
	public int x, y;
	public int w, h;
	
	public Button(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public abstract void draw();
	public abstract void run();
}