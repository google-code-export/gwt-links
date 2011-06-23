package com.orange.links.demo.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.demo.client.widgets.BoxLabel;

public class Example4 extends AbstractExample{

	@Override
	public void draw() {		
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new BoxLabel("World");
		controller.addWidget(labelWorld,200,115);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		controller.registerDragController(dragController);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
	}

	@Override
	public String getName() {
		return "Example 4";
	}

	@Override
	public String getDescription() {
		return "An arrow and a scroll panel";
	}

	@Override
	public String getSourceLink() {
		return "http://code.google.com/p/gwt-links/source/browse/trunk/gwt-links-parent/gwt-links-demo/src/main/java/com/orange/links/demo/client/example/Example4.java";
	}

	@Override
	public Widget asWidget() {
		Widget widgetPanel = controller.getView();
		widgetPanel.getElement().getStyle().setMargin(0, Unit.PX);
		widgetPanel.getElement().getStyle().setProperty("border", "0px");
		
		controller.setFrameSize(300, 300);
		return controller.getViewAsScrollPanel();
	}

}