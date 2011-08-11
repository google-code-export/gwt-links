package com.orange.links.client.canvas;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;


public interface DiagramCanvas {

	/**
	 * Give this diagram canvas as a GWT widget
	 * @return
	 */
	Widget asWidget();
	
	/**
	 * Clear the diagram (the connections for example)
	 */
	void clear();
	
	/**
	 * Change the z-index to display the canvas in foreground
	 */
	void setForeground();
	
	/**
	 * Change the z-index to display the canvas in background
	 */
	void setBackground();
	
	/**
	 * Get width
	 * @return the width of the canvas
	 */
	int getWidth();
	
	/**
	 * Width
	 * @param the width of the canvas
	 */
	void setWidth(int width);

	/**
	 * Get height
	 * @return height of the canvas
	 */
	int getHeight();
	
	/**
	 * Set height
	 * @param height of the canvas
	 */
	void setHeight(int height);
	
	/**
	 * Specific methods for Canvas
	 */
	
	<H extends EventHandler> HandlerRegistration addDomHandler(H handler, DomEvent.Type<H> type);
	Element getElement();
	void beginPath();
	void closePath();
	void lineTo(double x, double y);
	void moveTo(double x, double y);
	void setStrokeStyle(CssColor color);
	void setStrokeStyle(String color);
	void setFillStyle(CssColor color);
	void setFillStyle(String color);
	void fillRect(double x, double y, double w, double h);
	void stroke();
	void arc(double x, double y, double radius,  double startAngle, double endAngle, boolean anticlockwise);
	void fill();
	void bezierCurveTo(double cpx, double cpy, double x, double y);
	void setGlobalAlpha(double alpha);
}
