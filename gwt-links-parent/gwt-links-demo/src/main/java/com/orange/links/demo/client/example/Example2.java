package com.orange.links.demo.client.example;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.orange.links.client.DiagramController;
import com.orange.links.client.event.NewFunctionEvent;
import com.orange.links.client.event.NewFunctionHandler;
import com.orange.links.demo.client.widgets.DiagramSaveFactoryImpl;

public class Example2 extends AbstractExample{

	@Override
	public void draw(DiagramController controller) {
		// Create the elements
		/*Widget labelHello = new BoxLabel("Hello");
		controller.addWidget(labelHello,25,115);
		
		Widget labelWorld = new BoxLabel("World");
		controller.addWidget(labelWorld,200,115);
		
		Widget img = new SavableImage("http://coria09.univ-tln.fr/public/logo_orange.jpg");
		img.setSize("48px", "48px");
		controller.addWidget(img,200,25);
		
		// Add DnD logic
		PickupDragController dragController = new PickupDragController(controller.getView(), true);
		dragController.makeDraggable(labelHello);
		dragController.makeDraggable(labelWorld);
		dragController.makeDraggable(img);
		controller.registerDragController(dragController);
		
		// Add the logic
		Connection c1 = controller.drawStraightArrowConnection(labelHello, labelWorld);
		Connection c2 = controller.drawStraightConnection(labelHello, img);
		Label decorationLabel = new Label("Mickey");
		decorationLabel.getElement().getStyle().setBackgroundColor("#FFFFFF");
		decorationLabel.getElement().getStyle().setPadding(5, Unit.PX);
		decorationLabel.getElement().getStyle().setProperty("border", "1px solid black");
		controller.addDecoration(decorationLabel, c1);
		controller.addPointOnConnection(c2, 150, 25);*/
		
		final PickupDragController dragController = new PickupDragController(controller.getView(), true);
		controller.registerDragController(dragController);
		controller.addNewFunctionHandler(new NewFunctionHandler() {
			@Override
			public void onNewFunction(NewFunctionEvent event) {
				dragController.makeDraggable(event.getFunction());
			}
		});
		
		String xmlDiagram = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<diagram width=\"400\" heigth=\"400\" grid=\"true\">"+
		"<function left=\"280\" top=\"26\" id=\"2\" identifier=\"image\">"+
		"	http://coria09.univ-tln.fr/public/logo_orange.jpg"+
		"</function>"+
		"<function left=\"280\" top=\"116\" id=\"1\" identifier=\"boxlabel\">"+
		"	World"+
		"</function>"+
		"<function left=\"26\" top=\"116\" id=\"3\" identifier=\"boxlabel\">"+
		"	Hello"+
		"</function>"+
		"<link startid=\"3\" endid=\"2\" type=\"straight\" >"+
		"	<point x=\"50\" y=\"50\"/>"+
		"</link>"+
		"<link startid=\"3\" endid=\"1\" type=\"straightarrow\">"+
		"	<decoration identifier=\"savabledecorationlabel\">"+
		"		Mickey"+
		"	</decoration>"+
		"</link>"+
		"</diagram>";
		
		controller.importDiagram(xmlDiagram, new DiagramSaveFactoryImpl());
		
		GWT.log(controller.exportDiagram());
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
		return "http://code.google.com/p/gwt-links/source/browse/trunk/gwt-links-parent/gwt-links-demo/src/main/java/com/orange/links/demo/client/example/Example2.java";
	}

}
