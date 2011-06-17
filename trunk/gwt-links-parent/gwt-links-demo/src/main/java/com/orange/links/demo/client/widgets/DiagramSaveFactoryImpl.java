package com.orange.links.demo.client.widgets;

import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.save.DiagramSaveFactory;

public class DiagramSaveFactoryImpl implements DiagramSaveFactory{

	@Override
	public Widget getFunctionByIdentifier(String identifier, String content) {
		if(identifier.equals(BoxLabel.identifier)){
			return new BoxLabel(content);
		} else if(identifier.equals(SavableImage.identifier)){
			return new SavableImage(content);
		}
		return null;
	}

	@Override
	public Widget getDecorationByIdentifier(String identifier, String content) {
		if(identifier.equals(SavableDecorationLabel.identifier)){
			return new SavableDecorationLabel(content);
		}
		return null;
	}

}
