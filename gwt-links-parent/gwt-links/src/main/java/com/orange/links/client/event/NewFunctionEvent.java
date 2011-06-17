package com.orange.links.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Widget;

public class NewFunctionEvent extends GwtEvent<NewFunctionHandler>{
	
	public interface HasNewFunctionHandlers extends HasHandlers{
		
		HandlerRegistration addNewFunctionHandler(NewFunctionHandler handler);
	}
	
	private Widget function;

	public NewFunctionEvent(Widget function){
		this.function = function;
	}
	
	private static Type<NewFunctionHandler> TYPE;

	public static Type<NewFunctionHandler> getType(){
		return TYPE != null ? TYPE : (TYPE = new Type<NewFunctionHandler>());
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<NewFunctionHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(NewFunctionHandler handler) {
		handler.onNewFunction(this);
	}


	public Widget getFunction() {
		return function;
	}

}
