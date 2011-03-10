package com.orange.links.client;

import com.orange.links.client.canvas.DiagramCanvas;


public interface Shape {

	public int getLeft();
	public int getTop();
	public int getWidth();
	public int getHeight();
	public void drawIfNecessary(DiagramCanvas canvas);
	
}
