package com.orange.links.test.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.test.client.widgets.BoxLabel;

public class Example1 extends AbstractExample{

	@Override
	public void draw(DiagramController controller) {
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new BoxLabel("World");
		controller.addWidget(labelWorld,200,115);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		//controller.registerDragController(dragController);
		
		// Add the logic
		controller.drawStraightArrowConnection(labelHello, labelWorld);
		controller.addDeleteOptionInContextualMenu("*Delete*");
		controller.addSetStraightOptionInContextualMenu("*Set Straight*"); 
		controller.addOptionInContextualMenu("Hello World !", new Command() {
			@Override
			public void execute() {
				Window.alert("Hello Mickey ;)");
			}
		});
	}

	@Override
	public String getName() {
		return "Example 1";
	}

	@Override
	public String getDescription() {
		return "Simple direct 'Hello World !' Example";
	}

	@Override
	public String getSourceLink() {
		return "http://code.google.com/p/gwt-links/source/browse/trunk/demo/com/orange/links/test/client/example/Example1.java";
	}

}
