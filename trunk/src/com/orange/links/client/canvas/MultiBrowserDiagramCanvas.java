package com.orange.links.client.canvas;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class MultiBrowserDiagramCanvas implements DiagramCanvas{

	protected int width;
	protected int height;
	private GWTCanvas canvas;
	
	public MultiBrowserDiagramCanvas(int width, int height){
		this.width = width;
		this.height = height;
		this.canvas = new GWTCanvas();
		setBackground();
		getElement().getStyle().setPosition(Position.ABSOLUTE);
		getElement().getStyle().setWidth(width, Unit.PX);
		getElement().getStyle().setHeight(height, Unit.PX);
		canvas.setCoordSize(width, height);
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
		return canvas;
	}

	@Override
	public void setStrokeStyle(String color) {
		canvas.setStrokeStyle(new Color(color));
	}

	@Override
	public void setFillStyle(String color) {
		canvas.setFillStyle(new Color(color));
	}


	@Override
	public void clear() {
		canvas.clear();
	}

	@Override
	public <H extends EventHandler> HandlerRegistration addDomHandler(
			H handler, Type<H> type) {
		return canvas.addDomHandler(handler, type);
	}

	@Override
	public Element getElement() {
		return canvas.getElement();
	}

	@Override
	public void beginPath() {
		canvas.beginPath();
	}

	@Override
	public void closePath() {
		canvas.closePath();
	}

	@Override
	public void lineTo(double x, double y) {
		canvas.lineTo(x, y);
	}

	@Override
	public void moveTo(double x, double y) {
		canvas.moveTo(x, y);
	}

	@Override
	public void fillRect(double x, double y, double w, double h) {
		canvas.fillRect(x, y, w, h);
	}

	@Override
	public void stroke() {
		canvas.stroke();
	}

	@Override
	public void arc(double x, double y, double radius, double startAngle,
			double endAngle, boolean anticlockwise) {
		canvas.arc(x, y, radius, startAngle, endAngle, anticlockwise);
	}

	@Override
	public void fill() {
		canvas.fill();
	}

}
