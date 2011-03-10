package com.orange.links.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.canvas.DiagramCanvas;

public class FunctionShape implements Shape{

	private Widget widget;
	
	public FunctionShape(Widget widget){
		this.widget = widget;
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

}
