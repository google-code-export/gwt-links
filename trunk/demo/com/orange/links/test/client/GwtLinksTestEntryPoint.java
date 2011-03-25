package com.orange.links.test.client;


import java.util.Date;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
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
import com.orange.links.client.utils.Point;

public class GwtLinksTestEntryPoint implements EntryPoint {
	
	private int tabWidth = 400;
	private int tabHeight = 400;
	private DiagramController controller;
	private HorizontalPanel globalPanel;
	
	public void onModuleLoad() {
		
		globalPanel = new HorizontalPanel();
		RootPanel.get().add(globalPanel);
		
		// Create the drawing zone
		MultiBrowserDiagramCanvas canvas = new MultiBrowserDiagramCanvas(tabWidth,tabHeight);
		controller = new DiagramController(canvas);
		controller.showGrid(true);
		
		Widget w = controller.getView();
		w.getElement().getStyle().setMargin(10, Unit.PX);
		w.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		
		globalPanel.add(w);
		drawExample2();
		
		// DEBUG : Panel with informations
		VerticalPanel rightPanel = new VerticalPanel();
		
		VerticalPanel infoPanel = new VerticalPanel();
		Label exampleLabel = new Label("EXAMPLES");
		exampleLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		infoPanel.add(exampleLabel);
		Anchor example1Anchor = new Anchor("Example 1");
		example1Anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clear();
				drawExample1();
			}
		});
		infoPanel.add(example1Anchor);
		Anchor example2Anchor = new Anchor("Example 2");
		example2Anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clear();
				drawExample2();
			}
		});
		infoPanel.add(example2Anchor);
		Anchor example3Anchor = new Anchor("Example 3");
		example3Anchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				clear();
				drawExample3();
			}
		});
		infoPanel.add(example3Anchor);
		Label infoLabel = new Label("INFORMATIONS");
		infoLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		infoPanel.add(infoLabel);
		
		infoPanel.add(new Label("Refresh Rate : " + DiagramController.refreshRate + "ms"));
		infoPanel.add(new HTML("Source Page :  <a href=\"http://goo.gl/bevvN\">http://goo.gl/bevvN</a>"));
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
		timer.scheduleRepeating(25);
		editionPanel.getElement().getStyle().setProperty("border", "1px solid #cccccc");
		editionPanel.getElement().getStyle().setPadding(10, Unit.PX);
		editionPanel.getElement().getStyle().setMargin(10, Unit.PX);
		editionPanel.getElement().getStyle().setWidth(300, Unit.PX);
		rightPanel.add(editionPanel);
		
		// Scroll Panel to show events
		final VerticalPanel eventPanel = new VerticalPanel();
		final long now = new Date().getTime();
		controller.addTieLinkHandler(new TieLinkHandler() {
			@Override
			public void onTieLink(TieLinkEvent event) {
				Label tieLabel = new Label(new Date().getTime() - now + " | Event received - TieLinkEvent");
				tieLabel.getElement().getStyle().setColor("green");
				eventPanel.add(tieLabel);
			}
		});
		controller.addUntieLinkHandler(new UntieLinkHandler() {
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
		panel.add(new Label("FPS : " + controller.getFps()));
		// InDragBuildArrow
		if(controller.isInDragBuildArrow()){
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
		if(controller.isInDragMovablePoint()){
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
		if(controller.isInEditionDragMovablePoint()){
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
		if(controller.isInEditionSelectableShapeToDrawConnection()){
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
	
	private void clear(){
		controller.clearDiagram();
	}
	
	private void drawExample1(){
		
		// Create the elements
		Widget labelHello = new Label("Hello");
		labelHello.getElement().getStyle().setPadding(10, Unit.PX);
		labelHello.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelHello.getElement().getStyle().setBorderColor("#bbbbbb");
		labelHello.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelHello.getElement().getStyle().setBackgroundColor("#dddddd");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new Label("World");
		labelWorld.getElement().getStyle().setPadding(10, Unit.PX);
		labelWorld.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelWorld.getElement().getStyle().setBorderColor("#bbbbbb");
		labelWorld.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelWorld.getElement().getStyle().setBackgroundColor("#dddddd");
		controller.addWidget(labelWorld,200,115);
		
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
	}
	
	private void drawExample2(){
		// Create the elements
		Widget labelHello = new Label("Hello");
		labelHello.getElement().getStyle().setPadding(10, Unit.PX);
		labelHello.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelHello.getElement().getStyle().setBorderColor("#bbbbbb");
		labelHello.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelHello.getElement().getStyle().setBackgroundColor("#dddddd");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new Label("World");
		labelWorld.getElement().getStyle().setPadding(10, Unit.PX);
		labelWorld.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelWorld.getElement().getStyle().setBorderColor("#bbbbbb");
		labelWorld.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelWorld.getElement().getStyle().setBackgroundColor("#dddddd");
		controller.addWidget(labelWorld,200,115);
		
		Widget img = new Image("http://www.abonnement-forfait-mobile.com/wp-content/uploads/2010/12/logo_orange.png");
		img.setSize("48px", "48px");
		controller.addWidget(img,200,25);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		dragController.makeDraggable(img);
		
		// Add the logic
		Connection c1 = controller.drawStraightArrowConnection(labelHello, labelWorld);
		controller.drawStraightConnection(labelHello, img);
		Label decorationLabel = new Label("Mickey");
		decorationLabel.getElement().getStyle().setBackgroundColor("#FFFFFF");
		decorationLabel.getElement().getStyle().setPadding(5, Unit.PX);
		decorationLabel.getElement().getStyle().setProperty("border", "1px solid black");
		controller.addDecoration(decorationLabel, c1);
		
	}
	
	private void drawExample3(){
		// Create the elements
		Widget labelHello = new Label("Hello");
		labelHello.getElement().getStyle().setPadding(10, Unit.PX);
		labelHello.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelHello.getElement().getStyle().setBorderColor("#bbbbbb");
		labelHello.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelHello.getElement().getStyle().setBackgroundColor("#dddddd");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new Label("World");
		labelWorld.getElement().getStyle().setPadding(10, Unit.PX);
		labelWorld.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelWorld.getElement().getStyle().setBorderColor("#bbbbbb");
		labelWorld.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelWorld.getElement().getStyle().setBackgroundColor("#dddddd");
		controller.addWidget(labelWorld,200,115);
		
		Widget img = new Image("http://www.abonnement-forfait-mobile.com/wp-content/uploads/2010/12/logo_orange.png");
		img.setSize("48px", "48px");
		controller.addWidget(img,200,25);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		dragController.makeDraggable(img);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
		Connection c2 = controller.drawStraightConnection(labelHello, img);
		c2.addMovablePoint(new Point(50, 50));
	}
}
