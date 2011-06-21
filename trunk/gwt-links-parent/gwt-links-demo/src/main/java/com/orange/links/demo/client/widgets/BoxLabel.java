package com.orange.links.demo.client.widgets;

import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasAllTouchHandlers;
import com.google.gwt.event.dom.client.TouchCancelEvent;
import com.google.gwt.event.dom.client.TouchCancelHandler;
import com.google.gwt.event.dom.client.TouchEndEvent;
import com.google.gwt.event.dom.client.TouchEndHandler;
import com.google.gwt.event.dom.client.TouchMoveEvent;
import com.google.gwt.event.dom.client.TouchMoveHandler;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Label;
import com.orange.links.client.save.IsDiagramSerializable;

public class BoxLabel extends Label implements HasAllTouchHandlers,IsDiagramSerializable{

	public static String identifier = "boxlabel";
	String content;
	
	public BoxLabel(String text){
		super(text);
		this.content = text;
		getElement().getStyle().setPadding(10, Unit.PX);
		getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		getElement().getStyle().setBorderColor("#bbbbbb");
		getElement().getStyle().setBorderWidth(1, Unit.PX);
		getElement().getStyle().setBackgroundColor("#dddddd");
	}
	
	@Override
	public HandlerRegistration addTouchStartHandler(TouchStartHandler handler){
		return addDomHandler(handler, TouchStartEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchEndHandler(TouchEndHandler handler){
		return addDomHandler(handler, TouchEndEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler){
		return addDomHandler(handler, TouchMoveEvent.getType());
	}
	
	@Override
	public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler){
		return addDomHandler(handler, TouchCancelEvent.getType());
	}

	@Override
	public String getType() {
		return this.identifier;
	}

	@Override
	public String getContentRepresentation() {
		return this.content;
	}

	@Override
	public void setContentRepresentation(String contentRepresentation) {
		this.content = contentRepresentation;
	}
}
