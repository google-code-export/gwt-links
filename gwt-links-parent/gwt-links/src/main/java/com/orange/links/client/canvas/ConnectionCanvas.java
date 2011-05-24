package com.orange.links.client.canvas;

public class ConnectionCanvas extends MultiBrowserDiagramCanvas{

	public ConnectionCanvas(int width, int height) {
		super(width, height);
		getElement().getStyle().setZIndex(1);
	}

}
