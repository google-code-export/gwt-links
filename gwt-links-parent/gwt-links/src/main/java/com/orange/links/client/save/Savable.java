package com.orange.links.client.save;

public interface Savable {
	
	public String getIdentifier();
	
	public String getContentRepresentation();
	public void setContentRepresentation(String contentRepresentation);
	
}
