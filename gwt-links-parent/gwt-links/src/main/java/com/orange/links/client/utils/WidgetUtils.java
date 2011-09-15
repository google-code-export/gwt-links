package com.orange.links.client.utils;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

public class WidgetUtils {
	
    protected static IContainerFinder containerFinder = new ClassnameContainerFinder();
    
	public static int getLeft(Widget widget) {
		int containerOffset = 0;
		Element parent = DOM.getParent(widget.getElement());
		while( parent!=null ){
			if( containerFinder.isContainer(parent) ){
				containerOffset = DOM.getAbsoluteLeft(parent);
				break;
			}
			parent = DOM.getParent(parent);
		}
		return widget.getAbsoluteLeft() - containerOffset;
	}

	public static int getTop(Widget widget) {
		int containerOffset = 0;
		Element parent = DOM.getParent(widget.getElement());
		while( parent!=null ){
			if( containerFinder.isContainer(parent) ){
				containerOffset = DOM.getAbsoluteTop(parent);
				break;
			}
			parent = DOM.getParent(parent);
		}
		return widget.getAbsoluteTop() - containerOffset;
	}

	public static int getWidth(Widget widget) {
		return widget.getOffsetWidth();
	}

	public static int getHeight(Widget widget) {
		return widget.getOffsetHeight();
	}
	
}
