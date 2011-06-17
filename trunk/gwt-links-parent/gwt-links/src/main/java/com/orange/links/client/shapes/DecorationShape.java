package com.orange.links.client.shapes;

import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;

public class DecorationShape extends AbstractShape {

	private Widget widget;
	
    public DecorationShape(DiagramController controller, Widget widget) {
        super(controller, widget);
        this.widget = widget;
    }
    
    public Widget getWidget(){
    	return this.widget;
    }
    
    @Override
    public void draw() {
    }

    @Override
    public void drawHighlight() {
    }
    
}
