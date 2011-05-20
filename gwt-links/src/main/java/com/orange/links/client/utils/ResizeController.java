package com.orange.links.client.utils;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Widget;

public class ResizeController {

	private static int resizeMargin = 8;
	private boolean inResizeMode = false;
	private int x = 0;
	private int y = 0;
	private int width = 0;
	private int height = 0;
	
	public void assignResizeProperties(final Widget w){
		
		w.addHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				x = event.getRelativeX(w.getElement());
				y = event.getRelativeY(w.getElement());
				width = w.getOffsetWidth();
				height = w.getOffsetHeight();
				if(x < resizeMargin && y < resizeMargin){
					w.getElement().getStyle().setCursor(Cursor.NW_RESIZE);
				}
				else if(x < resizeMargin){
					w.getElement().getStyle().setCursor(Cursor.W_RESIZE);
				}
				else if(y < resizeMargin){
					w.getElement().getStyle().setCursor(Cursor.N_RESIZE);
					w.getElement().getStyle().setHeight(height, Unit.PX);
				}
				else{
					w.getElement().getStyle().setCursor(Cursor.DEFAULT);
				}
			}
		},MouseMoveEvent.getType());
		
		w.addHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.stopPropagation();
				if(x < resizeMargin || y < resizeMargin
						|| x >  width - resizeMargin || y > height - resizeMargin)
					inResizeMode = true;
			}
		}, MouseDownEvent.getType());
		
		w.addHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				event.stopPropagation();
				inResizeMode = false;
			}
		}, MouseUpEvent.getType());
		
	}
	
}
