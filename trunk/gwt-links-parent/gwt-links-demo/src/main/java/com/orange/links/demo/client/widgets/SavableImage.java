package com.orange.links.demo.client.widgets;

import com.google.gwt.user.client.ui.Image;
import com.orange.links.client.save.Savable;

public class SavableImage extends Image implements Savable{

	public static  String identifier = "image";
	private String content;
	
	public SavableImage(String url){
		super(url);
		this.content = url;
	}
	
	@Override
	public String getIdentifier() {
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
