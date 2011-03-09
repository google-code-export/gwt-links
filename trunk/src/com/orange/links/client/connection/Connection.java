package com.orange.links.client.connection;

import com.orange.links.client.Shape;
import com.orange.links.client.utils.Point;
import com.orange.links.client.utils.Segment;

public interface Connection {

	// Draw the connection on the global canvas
	void draw();
	void addMovablePoint(Point p);
	void updateMovablePoint(Point p);
	
	// Return the containers
	Shape getStartShape();
	Shape getEndShape();
	
	// Highlight Point
	void setHighlightPoint(Point p);
	Point getHighlightPoint();
	Point findHighlightPoint(Point p);

	// Selection Management
	boolean isSelected();
	boolean isSelectable();
	void changeSelection();
	void unselect();
}
