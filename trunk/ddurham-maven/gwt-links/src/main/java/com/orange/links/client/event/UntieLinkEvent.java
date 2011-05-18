package com.orange.links.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.connection.Connection;

public class UntieLinkEvent extends GwtEvent<UntieLinkHandler>{
	
	public interface HasUntieLinkHandlers extends HasHandlers{
		
		HandlerRegistration addUntieLinkHandler(UntieLinkHandler handler);
	}
	
	private Widget startWidget;
	private Widget endWidget;
	private Connection connection;

	public UntieLinkEvent(Widget startWidget, Widget endWidget,Connection c){
		this.setStartWidget(startWidget);
		this.setEndWidget(endWidget);
		this.setConnection(c);
	}
	
	private static Type<UntieLinkHandler> TYPE;

	public static Type<UntieLinkHandler> getType(){
		return TYPE != null ? TYPE : (TYPE = new Type<UntieLinkHandler>());
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UntieLinkHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(UntieLinkHandler handler) {
		handler.onUntieLink(this);
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


	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}
