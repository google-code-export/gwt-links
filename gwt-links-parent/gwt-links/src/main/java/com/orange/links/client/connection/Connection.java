package com.orange.links.client.connection;

import java.util.List;

import com.orange.links.client.DiagramController;
import com.orange.links.client.menu.HasContextMenu;
import com.orange.links.client.shapes.DecorationShape;
import com.orange.links.client.shapes.Drawable;
import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;
import com.orange.links.client.utils.MovablePoint;

public interface Connection extends Drawable, HasContextMenu {

	/**
	 * Add a fixed point on a connection called movable point
	 * @param p the point 
	 * @return
	 */
	MovablePoint addMovablePoint(Point p);
	
	/**
	 * Set a decoration a the connection
	 * @param s the shape of the decoration to add
	 */
	void setDecoration(DecorationShape s);
	
	/**
	 * 
	 * @return decoration of the connection 
	 */
	DecorationShape getDecoration();
	
	/**
	 * 
	 * @return list of the movable points
	 */
	List<Point> getMovablePoints();
	
	/**
	 * Remove the decoration on a connection
	 */
	void removeDecoration();
	
	/**
	 * Delete alle the movable point and then, the connection is straight
	 */
	void setStraight();
	
	/**
	 *  Return the start container
	 * @return starting shape of the start widget
	 */
	Shape getStartShape();
	
	/**
	 *  Return the end container
	 * @return ending shape of the end widget
	 */
	Shape getEndShape();
	
	/**
	 *  Highlight Point
	 * @param mousePoint the point near the movable point
	 * @return the highlighted point
	 */
	Point highlightMovablePoint(Point mousePoint);
	
	/**
	 * Test is the mouse is near a connection
	 * @param mousePoint the point where the mouse is
	 * @return true is the mouse is near the connection
	 */
	boolean isMouseNearConnection(Point mousePoint);
	
	/**
	 * Delete the connection from the absolutepanel
	 */
	public void delete();
	
	void setController(DiagramController controller);

}
