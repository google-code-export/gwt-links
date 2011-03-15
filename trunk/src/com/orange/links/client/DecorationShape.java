package com.orange.links.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class DecorationShape implements Shape{

	private Widget widget;
	private DiagramController controller;

	public DecorationShape(DiagramController controller, Widget widget){
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
	
	public Widget asWidget(){
		return widget;
	}

}
