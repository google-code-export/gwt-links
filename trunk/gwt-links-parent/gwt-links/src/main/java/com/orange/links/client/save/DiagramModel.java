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

public class DiagramModel implements IsSerializable{

	// Diagram Properties
	private int width;
	private int height;
	private boolean hasGrid;

	// Functions
	private Set<FunctionModel> functionRepresentationSet;
	private Map<Widget,String> functionWidgetMap;
	private int id = 0;

	// Links
	private Set<LinkModel> linkRepresentationSet;


	public DiagramModel(){
		functionRepresentationSet = new HashSet<FunctionModel>();
		linkRepresentationSet = new HashSet<LinkModel>();
		functionWidgetMap = new HashMap<Widget, String>();
	}

	public void setDiagramProperties(int width,int height, boolean hasGrid){
		this.width = width;
		this.height = height;
		this.hasGrid = hasGrid;
	}

	public void addFunction(Widget functionWidget){
		FunctionModel function = new FunctionModel();
		function.id = ++id + "";
		function.top = WidgetUtils.getTop(functionWidget);
		function.left = WidgetUtils.getLeft(functionWidget);
		try{
			function.content = ((IsDiagramSerializable) functionWidget).getContentRepresentation();
			function.identifier = ((IsDiagramSerializable) functionWidget).getType();
		}
		catch(ClassCastException e){
			throw new IllegalArgumentException("Widgets must implement the interface Savable to be saved");
		}
		functionRepresentationSet.add(function);
		functionWidgetMap.put(functionWidget,function.id);
	}
	
	public Widget getFunctionById(String id){
		for(Widget w : functionWidgetMap.keySet()){
			if(functionWidgetMap.get(w).equals(id))
				return w;
		}
		return null;
	}
	
	public int getNumberOfStartingLinks(Widget widget){
		String id = functionWidgetMap.get(widget);
		int numberOfStartingLinks = 0;
		for(LinkModel link : linkRepresentationSet){
			if(link.startId.equals(id))
				numberOfStartingLinks++;
		}
		return numberOfStartingLinks;
	}
	
	public int getNumberOfIncomingLinks(Widget widget){
		String id = functionWidgetMap.get(widget);
		int numberOfIncomingLinks = 0;
		for(LinkModel link : linkRepresentationSet){
			if(link.endId.equals(id))
				numberOfIncomingLinks++;
		}
		return numberOfIncomingLinks;
	}
	
	public void addFunction(FunctionModel functionRepresentation){
		functionRepresentationSet.add(functionRepresentation);
	}
	
	public void addLink(Widget startWidget,Widget endWidget,
			int[][] pointList,
			 Connection c){
		LinkModel link = new LinkModel();
		link.pointList = pointList;
		link.startId = functionWidgetMap.get(startWidget);
		link.endId = functionWidgetMap.get(endWidget);
		if(c.getDecoration() != null){
			Widget w = c.getDecoration().getWidget();
			DecorationModel decoration = new DecorationModel();
			try{
				decoration.content = ((IsDiagramSerializable) w).getContentRepresentation();
				decoration.identifier = ((IsDiagramSerializable) w).getType();
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
	
	public void addLink(LinkModel linkRepresentation){
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

	public Set<FunctionModel> getFunctionRepresentationSet() {
		return functionRepresentationSet;
	}

	public Set<LinkModel> getLinkRepresentationSet() {
		return linkRepresentationSet;
	}

}
