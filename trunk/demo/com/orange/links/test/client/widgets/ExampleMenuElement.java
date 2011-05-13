package com.orange.links.test.client.widgets;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.orange.links.test.client.GwtLinksTestEntryPoint;
import com.orange.links.test.client.example.AbstractExample;

public class ExampleMenuElement extends LayoutPanel{

	private Anchor nameAnchor;
	private Label descriptionLabel;
	private Anchor sourceLink;
	
	private int width = 120;
	private int height = 80;
	
	public ExampleMenuElement(final AbstractExample example, final GwtLinksTestEntryPoint entryPoint){
		setWidth(width + "px");
		setHeight(height + "px");
		
		nameAnchor = new Anchor(example.getName());
		add(nameAnchor);
		nameAnchor.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		setWidgetTopHeight(nameAnchor, 2, Unit.PX, 20, Unit.PX);
		nameAnchor.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				entryPoint.loadExample(example);
			}
		});
		
		descriptionLabel = new Label(example.getDescription());
		add(descriptionLabel);
		setWidgetTopHeight(descriptionLabel, 20, Unit.PX, 50, Unit.PX);
		
		sourceLink = new Anchor("Source Link", example.getSourceLink());
		add(sourceLink);
		setWidgetTopHeight(sourceLink, 55, Unit.PX, 20, Unit.PX);
		
	}
	
}
