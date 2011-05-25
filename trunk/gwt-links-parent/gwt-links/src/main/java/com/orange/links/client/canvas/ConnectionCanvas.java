package com.orange.links.client.canvas;

import com.google.gwt.dom.client.Style.Unit;

public class ConnectionCanvas extends MultiBrowserDiagramCanvas{

	public ConnectionCanvas(int width, int height) {
		super(width, height);
		getElement().getStyle().setZIndex(1);
		getElement().getStyle().setTop(0, Unit.PX);
		getElement().getStyle().setLeft(0, Unit.PX);
		asWidget().addStyleName("connection-canvas");
	}

}
