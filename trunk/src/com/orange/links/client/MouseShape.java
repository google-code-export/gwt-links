package com.orange.links.client;

import com.orange.links.client.canvas.DiagramCanvas;


public class MouseShape implements Shape {

	private int left = 0;
	private int top = 0;
	private int height = 3;
	private int width = 3;
	
	public MouseShape(int left, int top){
		this.left = left;
		this.top = top;
	}
	
	
	@Override
	public int getLeft() {
		return left;
	}

	@Override
	public int getTop() {
		return top;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}


	public void setLeft(int left) {
		this.left = left;
	}


	public void setTop(int top) {
		this.top = top;
	}

	@Override
	public void drawIfNecessary(DiagramCanvas canvas) {
		// nothing to do
	}

}
