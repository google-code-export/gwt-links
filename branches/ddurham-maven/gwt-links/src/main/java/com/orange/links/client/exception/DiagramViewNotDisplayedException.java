package com.orange.links.client.exception;

public class DiagramViewNotDisplayedException extends RuntimeException {

	private static final long serialVersionUID = 4145792483236913230L;

	public DiagramViewNotDisplayedException(){
		super("Add the view in your application. Try : RootPanel.get().add(controller.getView());" +
				"You can also add the view in a Panel for example : myPanel.add(controller.getView());");
	}
	
}
