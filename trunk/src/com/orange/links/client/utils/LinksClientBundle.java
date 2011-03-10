package com.orange.links.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LinksClientBundle extends ClientBundle{

	interface LinksCssResource extends CssResource {
		
		@ClassName("connection-popup")
		public String connectionPopup();
		
		@ClassName("connection-popup-item")
		public String connectionPopupItem();
		
	}
	
	static final LinksClientBundle INSTANCE = GWT.create(LinksClientBundle.class);

	@Source("GwtLinksStyle.css")
	LinksCssResource css();

}
