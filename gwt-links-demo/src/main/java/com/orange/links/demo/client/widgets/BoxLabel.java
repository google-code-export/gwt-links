package com.orange.links.demo.client.widgets;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;

public class BoxLabel extends Label{

	public BoxLabel(String text){
		super(text);
		getElement().getStyle().setPadding(10, Unit.PX);
		getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		getElement().getStyle().setBorderColor("#bbbbbb");
		getElement().getStyle().setBorderWidth(1, Unit.PX);
		getElement().getStyle().setBackgroundColor("#dddddd");
	}
}
