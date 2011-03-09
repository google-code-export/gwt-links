package com.orange.links.client.canvas;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class MultiBrowserDiagramCanvas extends GWTCanvas implements DiagramCanvas{

	private int width;
	private int height;
	
	public MultiBrowserDiagramCanvas(int width, int height){
		this.width = width;
		this.height = height;
		setBackground();
		getElement().getStyle().setPosition(Position.ABSOLUTE);
		getElement().getStyle().setWidth(width, Unit.PX);
		getElement().getStyle().setHeight(height, Unit.PX);
		setCoordSize(width, height);
		
	}
	
	@Override
	public void setForeground() {
		this.getElement().getStyle().setZIndex(5);
	}

	@Override
	public void setBackground() {
		this.getElement().getStyle().setZIndex(1);
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void setStrokeStyle(CssColor color) {
		setStrokeStyle(color.toString());
	}

	@Override
	public void setFillStyle(CssColor color) {
		setFillStyle(color.toString());
	}
	
	@Override
	public Widget asWidget(){
		return this;
	}

	@Override
	public void setStrokeStyle(String color) {
		setStrokeStyle(new Color(color));
	}

	@Override
	public void setFillStyle(String color) {
		setFillStyle(new Color(color));
	}


}
