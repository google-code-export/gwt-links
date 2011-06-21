package com.orange.links.demo.client.widgets;

import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.save.DiagramWidgetFactory;

public class DiagramSaveFactoryImpl implements DiagramWidgetFactory{

	@Override
	public Widget getFunctionByType(String type, String content) {
		if(type.equals(BoxLabel.identifier)){
			return new BoxLabel(content);
		} else if(type.equals(SavableImage.identifier)){
			return new SavableImage(content);
		}
		return null;
	}

	@Override
	public Widget getDecorationByType(String type, String content) {
		if(type.equals(SavableDecorationLabel.identifier)){
			return new SavableDecorationLabel(content);
		}
		return null;
	}

}
