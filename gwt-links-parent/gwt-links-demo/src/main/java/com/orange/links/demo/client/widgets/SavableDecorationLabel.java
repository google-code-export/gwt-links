package com.orange.links.demo.client.widgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.orange.links.client.save.Savable;

public class SavableDecorationLabel extends Label implements Savable{
	
	public static String identifier = "savabledecorationlabel";
	public String content;
	
	public SavableDecorationLabel(String content){
		super(content);
		this.content = content;
		getElement().getStyle().setBackgroundColor("#FFFFFF");
		getElement().getStyle().setPadding(5, Unit.PX);
		getElement().getStyle().setProperty("border", "1px solid black");
	}
	
	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public String getContentRepresentation() {
		return content;
	}

	@Override
	public void setContentRepresentation(String contentRepresentation) {
		this.content = contentRepresentation;
	}

}
