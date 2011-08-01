package com.orange.links.demo.client.example;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.connection.Connection;
import com.orange.links.demo.client.widgets.BoxLabel;
import com.orange.links.demo.client.widgets.LabelWithMenu;

public class Example1 extends AbstractExample{
	
	@Override
	public void draw() {
		
		controller.setAllowingUserInteractions(false);
		
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
        LabelWithMenu hasMenu = new LabelWithMenu("Context Menu");
		controller.addWidget(hasMenu,200,115);
		
		
		// Add the logic
		Connection con = controller.drawStraightArrowConnection(labelHello, hasMenu);
		con.getContextMenu().addItem(new MenuItem("Hello World !", new Command() {
			@Override
			public void execute() {
				Window.alert("Hello Mickey ;)");
			}
		}));
	}

	@Override
	public String getName() {
		return "Example 1";
	}

	@Override
	public String getDescription() {
		return "Simple without User Interactions";
	}

	@Override
	public String getSourceLink() {
		return "http://code.google.com/p/gwt-links/source/browse/trunk/gwt-links-parent/gwt-links-demo/src/main/java/com/orange/links/demo/client/example/Example1.java";
	}

	@Override
	public Widget asWidget() {
		return controller.getView();
	}

}


