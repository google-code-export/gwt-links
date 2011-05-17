package com.orange.links.demo.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.connection.Connection;
import com.orange.links.demo.client.widgets.BoxLabel;

public class Example2 extends AbstractExample{

	@Override
	public void draw(DiagramController controller) {
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new BoxLabel("World");
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
		Connection c1 = controller.drawStraightArrowConnection(labelHello, labelWorld);
		controller.drawStraightConnection(labelHello, img);
		Label decorationLabel = new Label("Mickey");
		decorationLabel.getElement().getStyle().setBackgroundColor("#FFFFFF");
		decorationLabel.getElement().getStyle().setPadding(5, Unit.PX);
		decorationLabel.getElement().getStyle().setProperty("border", "1px solid black");
		controller.addDecoration(decorationLabel, c1);
	}

	@Override
	public String getName() {
		return "Example 2";
	}

	@Override
	public String getDescription() {
		return "Two connections and a decoration";
	}

	@Override
	public String getSourceLink() {
		return "http://code.google.com/p/gwt-links/source/browse/trunk/demo/com/orange/links/test/client/example/Example2.java";
	}

}
