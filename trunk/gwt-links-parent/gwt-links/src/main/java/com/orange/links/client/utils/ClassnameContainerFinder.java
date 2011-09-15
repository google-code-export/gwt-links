package com.orange.links.client.utils;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.DOM;

public class ClassnameContainerFinder implements IContainerFinder {

	private static final String PARENT_CLASS_NAME = "dragdrop-boundary";
	
	public boolean isContainer(Element element) {
		String classNames = DOM.getElementAttribute(element, "class");
    	return classNames.contains(PARENT_CLASS_NAME);
	}
	
}
