package com.orange.links.demo.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.connection.Connection;
import com.orange.links.demo.client.widgets.BoxLabel;

public class Example3  extends AbstractExample{

	@Override
	public void draw(DiagramController controller) {
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new Label("World");
		controller.addWidget(labelWorld,200,115);
		
		Widget img = new Image("http://www.abonnement-forfait-mobile.com/wp-content/uploads/2010/12/logo_orange.png");
		img.setSize("48px", "48px");
		controller.addWidget(img,200,25);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		dragController.makeDraggable(img);
		//controller.registerDragController(dragController);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
		Connection c2 = controller.drawStraightConnection(labelHello, img);
		controller.addPointOnConnection(c2, 50, 50);
		controller.addPointOnConnection(c2, 75, 75);
		controller.addPointOnConnection(c2, 150, 25);
	}

	@Override
	public String getName() {
		return "Example 3";
	}

	@Override
	public String getDescription() {
		return "An arrow and a ... complex connection";
	}

	@Override
	public String getSourceLink() {
		return "http://code.google.com/p/gwt-links/source/browse/trunk/demo/com/orange/links/test/client/example/Example3.java";
	}

}
