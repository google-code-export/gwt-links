package com.orange.links.test.client;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.canvas.MultiBrowserDiagramCanvas;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.utils.Point;

public class GwtLinksTestEntryPoint implements EntryPoint {
	
	public void onModuleLoad() {
		
		HorizontalPanel globalPanel = new HorizontalPanel();
		RootPanel.get().add(globalPanel);
		
		// Create the drawing zone
		MultiBrowserDiagramCanvas canvas = new MultiBrowserDiagramCanvas(800,600);
		AbsolutePanel absolutePanel = new AbsolutePanel();
		absolutePanel.getElement().getStyle().setWidth(800, Unit.PX);
		absolutePanel.getElement().getStyle().setHeight(600, Unit.PX);
		absolutePanel.add(canvas.asWidget());
		globalPanel.add(absolutePanel);
		
		// Create the elements
		Widget labelHello = new Label("Hello");
		labelHello.getElement().getStyle().setZIndex(3);
		labelHello.getElement().getStyle().setPadding(10, Unit.PX);
		labelHello.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelHello.getElement().getStyle().setBorderColor("#bbbbbb");
		labelHello.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelHello.getElement().getStyle().setBackgroundColor("#dddddd");
		absolutePanel.add(labelHello,25,115);
		
		Widget labelWorld = new Label("World");
		labelWorld.getElement().getStyle().setZIndex(3);
		labelWorld.getElement().getStyle().setPadding(10, Unit.PX);
		labelWorld.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
		labelWorld.getElement().getStyle().setBorderColor("#bbbbbb");
		labelWorld.getElement().getStyle().setBorderWidth(1, Unit.PX);
		labelWorld.getElement().getStyle().setBackgroundColor("#dddddd");
		absolutePanel.add(labelWorld,200,115);
		
		Widget img = new Image("http://www.abonnement-forfait-mobile.com/wp-content/uploads/2010/12/logo_orange.png");
		img.setSize("48px", "48px");
		img.getElement().getStyle().setZIndex(3);
		absolutePanel.add(img,200,25);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(absolutePanel, true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		dragController.makeDraggable(img);
		
		// Add the logic
		DiagramController controller = new DiagramController(canvas);
		Connection c1 = controller.drawStraightArrowConnection(labelHello, labelWorld);
		Connection c2 = controller.drawStraightConnection(labelHello, img);
		c2.addMovablePoint(new Point(50, 50));
		
		// DEBUG : Panel with informations
		VerticalPanel infoPanel = new VerticalPanel();
		infoPanel.add(new Label("Refresh Rate : " + DiagramController.refreshRate));
		globalPanel.add(infoPanel);
	}
}
