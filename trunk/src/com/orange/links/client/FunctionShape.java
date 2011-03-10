package com.orange.links.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.Point;

public class FunctionShape implements Shape{

	private Widget widget;
	private int selectableAreaRadius = 5;
	private DiagramController controller;
	private CssColor highlightSelectableAreaColor = CssColor.make("#FF6600");

	public FunctionShape(DiagramController controller, Widget widget){
		this.widget = widget;
		this.controller = controller;
	}

	public int getLeft() {
		int containerOffset = 0;
		Element parent = DOM.getParent(widget.getElement());
		while( parent!=null ){
			if( "relative".equals(DOM.getStyleAttribute(parent, "position")) ){
				containerOffset = DOM.getAbsoluteLeft(parent);
				break;
			}
			parent = DOM.getParent(parent);
		}
		return widget.getAbsoluteLeft() - containerOffset;
	}

	public int getTop() {
		int containerOffset = 0;
		Element parent = DOM.getParent(widget.getElement());
		while( parent!=null ){
			if( "relative".equals(DOM.getStyleAttribute(parent, "position")) ){
				containerOffset = DOM.getAbsoluteTop(parent);
				break;
			}
			parent = DOM.getParent(parent);
		}
		return widget.getAbsoluteTop() - containerOffset;
	}

	public int getWidth() {
		return widget.getOffsetWidth();
	}

	public int getHeight() {
		return widget.getOffsetHeight();
	}

	@Override
	public void drawIfNecessary(DiagramCanvas canvas) {
		// Nothing to do
	}

	public boolean isMouseNearSelectableArea(Point mousePoint){
		return getSelectableArea(mousePoint) != null;
	}

	public void highlightSelectableArea(Point mousePoint){
		// Mouse Point
		Point closestSelectablePoint = getSelectableArea(mousePoint);
		if(closestSelectablePoint != null){
			DiagramCanvas canvas = controller.getDiagramCanvas();
			canvas.beginPath();
			canvas.arc(closestSelectablePoint.getLeft(), 
					closestSelectablePoint.getTop(), selectableAreaRadius, 0, Math.PI*2, false);
			canvas.setStrokeStyle(highlightSelectableAreaColor);
			canvas.stroke();
			canvas.setFillStyle(highlightSelectableAreaColor);
			canvas.fill();
			canvas.closePath();
		}
	}
	public Point getSelectableArea(Point p){
		// Center of the selectable areas
		Point centerW = new Point(getLeft()-selectableAreaRadius,getTop()+getHeight()/2);
		Point centerN = new Point(getLeft()+getWidth()/2,getTop()-selectableAreaRadius);
		Point centerS = new Point(getLeft()+getWidth()/2,getTop()+getHeight()+selectableAreaRadius);
		Point centerE = new Point(getLeft()+getWidth()+selectableAreaRadius,getTop()+getHeight()/2);
		if(p.distance(centerW) <= selectableAreaRadius){
			return centerW;
		}
		else if(p.distance(centerN) <= selectableAreaRadius){
			return centerN;
		}
		else if(p.distance(centerS) <= selectableAreaRadius){
			return centerS;
		}
		else if(p.distance(centerE) <= selectableAreaRadius){
			return centerE;
		}
		return null;
	}

}
