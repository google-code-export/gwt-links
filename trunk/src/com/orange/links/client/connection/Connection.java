package com.orange.links.client.connection;

import com.orange.links.client.Shape;
import com.orange.links.client.utils.Point;

public interface Connection {

	// Draw the connection on the global canvas
	void draw();
	void addMovablePoint(Point p);
	void setStraight();
	
	// Return the containers
	Shape getStartShape();
	Shape getEndShape();
	
	// Highlight Point
	Point highlightMovablePoint(Point mousePoint);
	boolean isMouseNearConnection(Point mousePoint);

	// Selection Management
	boolean isSelected();
	boolean isSelectable();
	void changeSelection();
	void unselect();
}
