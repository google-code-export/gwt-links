package com.orange.links.client.save;

import com.google.gwt.user.client.rpc.IsSerializable;


public class LinkModel implements IsSerializable {
	public String startId;
	public String endId;
	public DecorationModel decoration;
	public int[][] pointList;
	public String type;
	public LinkModel(){};
}