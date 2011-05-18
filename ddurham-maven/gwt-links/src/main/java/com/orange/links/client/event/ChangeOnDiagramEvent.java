package com.orange.links.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class ChangeOnDiagramEvent extends GwtEvent<ChangeOnDiagramHandler>{

	public interface HasChangeOnDiagramHandlers extends HasHandlers{

		HandlerRegistration addChangeOnDiagramHandler(ChangeOnDiagramHandler handler);
	}


	private static Type<ChangeOnDiagramHandler> TYPE;

	public static Type<ChangeOnDiagramHandler> getType(){
		return TYPE != null ? TYPE : (TYPE = new Type<ChangeOnDiagramHandler>());
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeOnDiagramHandler> getAssociatedType() {
		return getType();
	}

	@Override
	protected void dispatch(ChangeOnDiagramHandler handler) {
		handler.onChangeOnDiagram(this);
	}


}
