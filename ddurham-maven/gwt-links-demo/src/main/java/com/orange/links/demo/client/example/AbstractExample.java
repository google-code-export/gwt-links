package com.orange.links.demo.client.example;

import com.orange.links.client.DiagramController;

public abstract class AbstractExample {

	
	public abstract String getName();
	public abstract String getDescription();
	public abstract String getSourceLink();

	public abstract void draw(DiagramController controller);
	
}
