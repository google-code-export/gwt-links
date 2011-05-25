package com.orange.links.demo.client;


import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.canvas.MultiBrowserDiagramCanvas;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.event.TieLinkEvent;
import com.orange.links.client.event.TieLinkHandler;
import com.orange.links.client.event.UntieLinkEvent;
import com.orange.links.client.event.UntieLinkHandler;
import com.orange.links.client.shapes.Drawable;
import com.orange.links.client.shapes.DrawableSet;
import com.orange.links.demo.client.example.AbstractExample;
import com.orange.links.demo.client.example.Example1;
import com.orange.links.demo.client.example.Example2;
import com.orange.links.demo.client.example.Example3;
import com.orange.links.demo.client.widgets.ExampleMenuElement;

public class GwtLinksTestEntryPoint implements EntryPoint {
	
	private int tabWidth = 400;
	private int tabHeight = 400;
	private DiagramController currentController;
	private HorizontalPanel globalPanel;
	
	private AbstractExample[] examples = {
			new Example1(),
			new Example2(),
			new Example3()
	};
	
	public void onModuleLoad() {
		VerticalPanel mainPanel = new VerticalPanel();
		RootPanel.get().add(mainPanel);
		
		HorizontalPanel menuPanel = new HorizontalPanel();
		for(int i = 0 ; i < examples.length ; i++){
			menuPanel.add(new ExampleMenuElement(examples[i], this));
		}
		menuPanel.getElement().getStyle().setMargin(10, Unit.PX);
		mainPanel.add(menuPanel);
		
		globalPanel = new HorizontalPanel();
		mainPanel.add(globalPanel);
		
		loadExample(examples[1]);
		
		// DEBUG : Panel with informations
		VerticalPanel rightPanel = new VerticalPanel();
		
		VerticalPanel infoPanel = new VerticalPanel();
		Label infoLabel = new Label("INFORMATIONS");
		infoLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		infoPanel.add(infoLabel);
		
		infoPanel.add(new Label("Refresh Rate : " + DiagramController.refreshRate + "ms"));
		infoPanel.add(new HTML("Source Page :  <a href=\"http://goo.gl/Vt4wN\">http://goo.gl/Vt4wN</a>"));
		infoPanel.add(new HTML("Contextual Menu :  Right Click on a connection"));
		infoPanel.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		infoPanel.getElement().getStyle().setPadding(10, Unit.PX);
		infoPanel.getElement().getStyle().setMargin(10, Unit.PX);
		infoPanel.getElement().getStyle().setWidth(300, Unit.PX);
		rightPanel.add(infoPanel);
		
		// Vertical Panel to display edition property
		final VerticalPanel editionPanel = new VerticalPanel();
		Timer timer = new Timer() {
			@Override
			public void run() {
				updateEditionPanel(editionPanel);
			}
		};
		timer.scheduleRepeating(500);
		editionPanel.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		editionPanel.getElement().getStyle().setPadding(10, Unit.PX);
		editionPanel.getElement().getStyle().setMargin(10, Unit.PX);
		editionPanel.getElement().getStyle().setWidth(300, Unit.PX);
		rightPanel.add(editionPanel);
		
		// Scroll Panel to show events
		final VerticalPanel eventPanel = new VerticalPanel();
		final long now = new Date().getTime();
		currentController.addTieLinkHandler(new TieLinkHandler() {
			@Override
			public void onTieLink(TieLinkEvent event) {
				Label tieLabel = new Label(new Date().getTime() - now + " | Event received - TieLinkEvent");
				tieLabel.getElement().getStyle().setColor("green");
				eventPanel.add(tieLabel);
			}
		});
		currentController.addUntieLinkHandler(new UntieLinkHandler() {
			@Override
			public void onUntieLink(UntieLinkEvent event) {
				Label tieLabel = new Label(new Date().getTime() - now + " | Event received - UntieLinkEvent");
				tieLabel.getElement().getStyle().setColor("red");
				eventPanel.add(tieLabel);
			}
		});
		eventPanel.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		eventPanel.getElement().getStyle().setPadding(10, Unit.PX);
		eventPanel.getElement().getStyle().setMargin(10, Unit.PX);
		eventPanel.getElement().getStyle().setWidth(300, Unit.PX);
		rightPanel.add(eventPanel);
		globalPanel.add(rightPanel);
	}
	
	private void updateEditionPanel(VerticalPanel panel){
		panel.clear();
		panel.add(new Label("FPS : " + currentController.getFps()));
	
		/*DrawableSet<Connection> unsynchroSet = currentController.getUnsynchronizedConnections();
		for(Drawable d : unsynchroSet){
			panel.add(new Label("Connection #" + d.hashCode() + " : AllowSynchro? " + d.allowSynchronized()));
		}*/
		//		panel.add(new Label("Mouse Coords : " + currentController.getMousePoint()));
		
		// InDragBuildArrow
		if(currentController.isInDragBuildArrow()){
			Label dragBuildLabel = new Label("Active : inDragBuildArrow");
			dragBuildLabel.getElement().getStyle().setColor("green");
			panel.add(dragBuildLabel);
		}
		else{
			Label dragBuildLabel = new Label("Inactive : inDragBuildArrow");
			dragBuildLabel.getElement().getStyle().setColor("red");
			panel.add(dragBuildLabel);
		}
		// InDragMovablePoint
		if(currentController.isInDragMovablePoint()){
			Label dragMovableLabel = new Label("Active : InDragMovablePoint");
			dragMovableLabel.getElement().getStyle().setColor("green");
			panel.add(dragMovableLabel);
		}
		else{
			Label dragMovableLabel = new Label("Inactive : InDragMovablePoint");
			dragMovableLabel.getElement().getStyle().setColor("red");
			panel.add(dragMovableLabel);
		}
		// InEditionDragMovablePoint
		if(currentController.isInEditionDragMovablePoint()){
			Label dragBuildLabel = new Label("Active : InEditionDragMovablePoint");
			dragBuildLabel.getElement().getStyle().setColor("green");
			panel.add(dragBuildLabel);
		}
		else{
			Label dragBuildLabel = new Label("Inactive : InEditionDragMovablePoint");
			dragBuildLabel.getElement().getStyle().setColor("red");
			panel.add(dragBuildLabel);
		}
		// InEditionSelectableShapeToDrawConnection
		if(currentController.isInEditionSelectableShapeToDrawConnection()){
			Label dragBuildLabel = new Label("Active : InEditionToDrawConnection");
			dragBuildLabel.getElement().getStyle().setColor("green");
			panel.add(dragBuildLabel);
		}
		else{
			Label dragBuildLabel = new Label("Inactive : InEditionToDrawConnection");
			dragBuildLabel.getElement().getStyle().setColor("red");
			panel.add(dragBuildLabel);
		}
	}
	
	public void loadExample(AbstractExample example){
		int index = 0;
		if(currentController != null){
			currentController.clearDiagram();
		}
		else{
			// Create the drawing zone
			currentController = new DiagramController(tabWidth,tabHeight);
			currentController.showGrid(true);
			Widget w = currentController.getView();
			w.getElement().getStyle().setMargin(10, Unit.PX);
			w.getElement().getStyle().setProperty("border", "1px solid #cccccc");
			globalPanel.insert(w, index);
		}
		
		example.draw(currentController);
	}
}
