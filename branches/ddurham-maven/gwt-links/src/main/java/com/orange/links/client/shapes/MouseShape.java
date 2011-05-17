package com.orange.links.client.shapes;

import com.orange.links.client.utils.Point;


public class MouseShape implements Shape {

	private Point mousePoint;
	private int height = 1;
	private int width = 1;
	
	public MouseShape(Point mousePoint){
		this.mousePoint = mousePoint;
	}
	
	
	@Override
	public int getLeft() {
		return mousePoint.getLeft();
	}

	@Override
	public int getTop() {
		return mousePoint.getTop();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
