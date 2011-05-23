package com.orange.links.demo.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.menu.ContextMenu;
import com.orange.links.client.menu.HasContextMenu;
import com.orange.links.demo.client.widgets.BoxLabel;

public class Example1 extends AbstractExample{

	@Override
	public void draw(DiagramController controller) {
		// Create the elements
		Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
        LabelWithMenu hasMenu = new LabelWithMenu("Context Menu");
		controller.addWidget(hasMenu,200,115);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
        dragController.makeDraggable(hasMenu);
        
		//controller.registerDragController(dragController);
		
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
		return "Simple direct 'Hello World !' Example";
	}

	@Override
	public String getSourceLink() {
		return "http://code.google.com/p/gwt-links/source/browse/trunk/demo/com/orange/links/test/client/example/Example1.java";
	}

}

class LabelWithMenu extends BoxLabel implements HasContextMenu {
    
    ContextMenu customMenu;
    
    public LabelWithMenu(String text) {
        super(text);
        customMenu = new ContextMenu();
        for (final String name : new String[] { "Custom 1", "Custom 2", "Custom 3" }) {
            customMenu.addItem(new MenuItem(name, new Command() {
                @Override
                public void execute() {
                    Window.alert(name + " clicked");
                }
            }));
        }
    }

    @Override
    public ContextMenu getContextMenu() {
        return customMenu;
    }
    
}
