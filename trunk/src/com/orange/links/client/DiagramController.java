package com.orange.links.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.connection.StraightArrowConnection;
import com.orange.links.client.connection.StraightConnection;
import com.orange.links.client.utils.ConnectionUtils;
import com.orange.links.client.utils.Point;

public class DiagramController {

	public final static int KEY_D = 68;
	public final static int KEY_CTRL = 17;
	public static final int minDistanceToSegment = 10;

	private DiagramCanvas canvas;

	private Set<Connection> connectionSet;
	//private FunctionContainer highlightFunction;
	private Widget startFunctionWidget;

	private Map<Widget,FunctionShape> shapeMap;

	// setup timer
	private final Timer timer = new Timer() {
		@Override
		public void run() {
			update();
		}
	};

	public DiagramController(final DiagramCanvas canvas){
		this.canvas = canvas;		
		connectionSet = new HashSet<Connection>();
		shapeMap = new HashMap<Widget,FunctionShape>();

		// When the mouse move, search for connections
		canvas.addDomHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				int left = event.getRelativeX(canvas.getElement());
				int top  = event.getRelativeY(canvas.getElement());
				highlightMovablePoint(new Point(left, top));
			}
		}, MouseMoveEvent.getType());

		
		//timer refresh rate, in milliseconds
		int refreshRate = GWT.isScript() ? 5 : 25;
		timer.scheduleRepeating(refreshRate);
	}

	private void highlightMovablePoint(Point p){
		for(Connection c : connectionSet){
			if(c.findHighlightPoint(p) != null){
				// A connection has been found : Set new cursor
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.POINTER);
				return;
			}
		}
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
		// If no movable has been found, remove movable point
		for(Connection c : connectionSet){
			// If a movable point is found, stop this method
			c.setHighlightPoint(null);
		}
	}

	public Connection drawStraightArrowConnection(Widget startWidget, Widget endWidget){
		// Build Shape for the Widgets
		if(!shapeMap.containsKey(startWidget)){
			shapeMap.put(startWidget,new FunctionShape(startWidget));
		}
		Shape startShape = shapeMap.get(startWidget);
		if(!shapeMap.containsKey(endWidget)){
			shapeMap.put(endWidget,new FunctionShape(endWidget));
		}
		Shape endShape = shapeMap.get(endWidget);
		// Create Connection and Store it in the controller
		Connection c = new StraightArrowConnection(this, startShape, endShape);

		connectionSet.add(c);
		return c;
	}

	public Connection drawStraightConnection(Widget startWidget, Widget endWidget){
		// Build Shape for the Widgets
		if(!shapeMap.containsKey(startWidget)){
			shapeMap.put(startWidget,new FunctionShape(startWidget));
		}
		Shape startShape = shapeMap.get(startWidget);
		if(!shapeMap.containsKey(endWidget)){
			shapeMap.put(endWidget,new FunctionShape(endWidget));
		}
		Shape endShape = shapeMap.get(endWidget);
		// Create Connection and Store it in the controller
		Connection c = new StraightConnection(this, startShape, endShape);

		connectionSet.add(c);
		return c;
	}

	public void update(){
		canvas.clear();
		// Redraw Connections
		for(Connection c : connectionSet){
			c.draw();
		}
	}

	public DiagramCanvas getDiagramCanvas(){
		return canvas;
	}

	public Connection drawStraightArrow(Widget startWidget, Widget endWidget,
			boolean selectable) {
		// Build Shape for the Widgets
		if(!shapeMap.containsKey(startWidget)){
			shapeMap.put(startWidget,new FunctionShape(startWidget));
		}
		Shape startShape = shapeMap.get(startWidget);
		if(!shapeMap.containsKey(endWidget)){
			shapeMap.put(endWidget,new FunctionShape(endWidget));
		}
		Shape endShape = shapeMap.get(endWidget);
		// Create Connection and Store it in the controller
		Connection c = new StraightArrowConnection(this, startShape, endShape, selectable);
		connectionSet.add(c);
		// Update the Canvas
		return c;
	}

	public void drawBuildArrow(Widget startFunctionWidget, 
			int startX, int startY){
		canvas.setForeground();
		Shape startShape = new FunctionShape(startFunctionWidget);
		final MouseShape endShape = new MouseShape(startX,startY);
		final Connection c = new StraightArrowConnection(this, startShape, endShape);
		this.startFunctionWidget = startFunctionWidget;
		connectionSet.add(c);

		canvas.addDomHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				int left = event.getRelativeX(canvas.getElement());
				int top = event.getRelativeY(canvas.getElement());
				endShape.setLeft(left);
				endShape.setTop(top);
				Point mousePoint = new Point(left,top);
				/*Set<FunctionContainer> shapeSet = presenter.getFunctionContainerSet();
				if(highlightFunction != null){
					highlightFunction.getFunctionWidget().unselect();
					highlightFunction = null;
				}
				for(FunctionContainer f : shapeSet){
					Rectangle r = new Rectangle(new FunctionShape(f.getFunctionWidget()));
					if(r.isInside(mousePoint)){
						highlightFunction = f;
						highlightFunction.getFunctionWidget().select();
					}
				}*/
			}
		}, MouseMoveEvent.getType());

		canvas.addDomHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {

				// If a function is highlighted, build a connection
				/*if(highlightFunction != null){
					presenter.onCreateConnection(DiagramController.this.startFunctionWidget, 
							highlightFunction.getFunctionWidget());
					highlightFunction = null;
				}*/
				canvas.setBackground();
				connectionSet.remove(c);
			}
		}, MouseUpEvent.getType());
	}

}
