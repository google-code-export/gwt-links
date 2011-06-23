package com.orange.links.demo.client.example;

import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;

public abstract class AbstractExample {

	protected DiagramController controller;
	
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getSourceLink();

	public abstract void draw();
	public abstract Widget asWidget();
	
	public void setDiagramController(DiagramController controller){
		this.controller = controller;
	}
	
}
