package com.orange.links.client.save;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.connection.StraightConnection;
import com.orange.links.client.utils.WidgetUtils;

public class DiagramRepresentation implements IsSerializable{

	// Diagram Properties
	private int width;
	private int height;
	private boolean hasGrid;

	// Functions
	private Set<FunctionRepresentation> functionRepresentationSet;
	private Map<Widget,String> functionWidgetMap;
	private int id = 0;

	// Links
	private Set<LinkRepresentation> linkRepresentationSet;


	public DiagramRepresentation(){
		functionRepresentationSet = new HashSet<FunctionRepresentation>();
		linkRepresentationSet = new HashSet<LinkRepresentation>();
		functionWidgetMap = new HashMap<Widget, String>();
	}

	public void setDiagramProperties(int width,int height, boolean hasGrid){
		this.width = width;
		this.height = height;
		this.hasGrid = hasGrid;
	}

	public void addFunction(Widget functionWidget){
		FunctionRepresentation function = new FunctionRepresentation();
		function.id = ++id + "";
		function.top = WidgetUtils.getTop(functionWidget);
		function.left = WidgetUtils.getLeft(functionWidget);
		try{
			function.content = ((Savable) functionWidget).getContentRepresentation();
			function.identifier = ((Savable) functionWidget).getIdentifier();
		}
		catch(ClassCastException e){
			throw new IllegalArgumentException("Widgets must implement the interface Savable to be saved");
		}
		functionRepresentationSet.add(function);
		functionWidgetMap.put(functionWidget,function.id);
	}
	
	public void addFunction(FunctionRepresentation functionRepresentation){
		functionRepresentationSet.add(functionRepresentation);
	}
	
	public void addLink(Widget startWidget,Widget endWidget,
			int[][] pointList,
			 Connection c){
		LinkRepresentation link = new LinkRepresentation();
		link.pointList = pointList;
		link.startId = functionWidgetMap.get(startWidget);
		link.endId = functionWidgetMap.get(endWidget);
		if(c.getDecoration() != null){
			Widget w = c.getDecoration().getWidget();
			DecorationRepresentation decoration = new DecorationRepresentation();
			try{
				decoration.content = ((Savable) w).getContentRepresentation();
				decoration.identifier = ((Savable) w).getIdentifier();
			}
			catch(ClassCastException e){
				throw new IllegalArgumentException("Decoration must implement the interface Savable");
			}
			link.decoration = decoration;
		}
		if(c instanceof StraightConnection){
			link.type = "straight";
		} else {
			link.type = "straightarrow";
		}
		linkRepresentationSet.add(link);
	}
	
	public void addLink(LinkRepresentation linkRepresentation){
		linkRepresentationSet.add(linkRepresentation);
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

	public boolean isHasGrid() {
		return hasGrid;
	}

	public void setHasGrid(boolean hasGrid) {
		this.hasGrid = hasGrid;
	}

	public Set<FunctionRepresentation> getFunctionRepresentationSet() {
		return functionRepresentationSet;
	}

	public Set<LinkRepresentation> getLinkRepresentationSet() {
		return linkRepresentationSet;
	}

}
