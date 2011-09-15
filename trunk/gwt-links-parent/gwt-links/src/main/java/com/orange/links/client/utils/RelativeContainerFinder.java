package com.orange.links.client.utils;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;

public class RelativeContainerFinder implements IContainerFinder {

	public boolean isContainer(Element element) {
		return "relative".equals(DOM.getStyleAttribute(element, "position"));
	}
	
}
