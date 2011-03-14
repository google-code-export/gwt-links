package com.orange.links.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Widget;

public class TieLinkEvent extends GwtEvent<TieLinkHandler>{
	
	public interface HasTieLinkHandlers extends HasHandlers{
		
		HandlerRegistration addTieLinkHandler(TieLinkHandler handler);
	}
	
	private Widget startWidget;
	private Widget endWidget;

	public TieLinkEvent(Widget startWidget, Widget endWidget){
		this.setStartWidget(startWidget);
		this.setEndWidget(endWidget);
	}
	
	private static Type<TieLinkHandler> TYPE;

	public static Type<TieLinkHandler> getType(){
		return TYPE != null ? TYPE : (TYPE = new Type<TieLinkHandler>());
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<TieLinkHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(TieLinkHandler handler) {
		handler.onTieLink(this);
	}


	public Widget getStartWidget() {
		return startWidget;
	}

	public void setStartWidget(Widget startWidget) {
		this.startWidget = startWidget;
	}

	public Widget getEndWidget() {
		return endWidget;
	}

	public void setEndWidget(Widget endWidget) {
		this.endWidget = endWidget;
	}

}
