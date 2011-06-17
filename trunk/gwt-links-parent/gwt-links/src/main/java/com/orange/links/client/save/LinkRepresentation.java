package com.orange.links.client.save;

import com.google.gwt.user.client.rpc.IsSerializable;


public class LinkRepresentation implements IsSerializable {
	public String startId;
	public String endId;
	public DecorationRepresentation decoration;
	public int[][] pointList;
	public String type;
	public LinkRepresentation(){};
}