package com.orange.links.client.canvas;

import com.orange.links.client.utils.LinksClientBundle;


public class ConnectionCanvas extends MultiBrowserDiagramCanvas{

	public ConnectionCanvas(int width, int height) {
		super(width, height);
		this.getElement().getStyle().setZIndex(1);
		this.asWidget().addStyleName(LinksClientBundle.INSTANCE.css().connectionCanvas());
	}

}
