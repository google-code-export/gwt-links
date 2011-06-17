package com.orange.links.client.save;

import com.google.gwt.user.client.ui.Widget;

public interface DiagramSaveFactory {

	public Widget getFunctionByIdentifier(String identifier, String content);
	public Widget getDecorationByIdentifier(String identifier, String content);
}
