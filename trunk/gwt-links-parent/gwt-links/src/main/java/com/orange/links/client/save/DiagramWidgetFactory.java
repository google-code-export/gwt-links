package com.orange.links.client.save;

import com.google.gwt.user.client.ui.Widget;

public interface DiagramWidgetFactory {

	public Widget getFunctionByType(String type, String content);
	public Widget getDecorationByType(String type, String content);
}
