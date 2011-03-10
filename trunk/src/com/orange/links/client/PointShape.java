package com.orange.links.client;

import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.connection.AbstractConnection;
import com.orange.links.client.utils.Point;

public class PointShape implements Shape{

	private Point point;
	
	public PointShape(Point point){
		this.point = point;
	}
	
	@Override
	public int getLeft() {
		return point.getLeft();
	}

	@Override
	public int getTop() {
		return point.getTop();
	}

	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public void drawIfNecessary(DiagramCanvas canvas) {
		canvas.beginPath();
		canvas.setFillStyle(AbstractConnection.defaultConnectionColor);
		canvas.fillRect(getLeft(), getTop(), getWidth(), getHeight());
		canvas.fill();
		canvas.closePath();
	}

}
