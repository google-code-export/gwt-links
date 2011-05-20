package com.orange.links.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface LinksClientBundle extends ClientBundle{

	interface LinksCssResource extends CssResource {
		
		@ClassName("links-connection-popup")
		public String connectionPopup();
		
		@ClassName("links-connection-popup-item")
		public String connectionPopupItem();
		
		@ClassName("links-highlight-widget")
		public String highlightWidget();
		
		@ClassName("links-translucide")
		public String translucide();
	}
	
	static final LinksClientBundle INSTANCE = GWT.create(LinksClientBundle.class);

	@Source("GwtLinksStyle.css")
	LinksCssResource css();

}
