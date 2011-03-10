package com.orange.links.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.connection.StraightArrowConnection;
import com.orange.links.client.connection.StraightConnection;
import com.orange.links.client.utils.LinksClientBundle;
import com.orange.links.client.utils.Point;

public class DiagramController {

	public final static int KEY_D = 68;
	public final static int KEY_CTRL = 17;
	public static final int minDistanceToSegment = 10;
	//timer refresh rate, in milliseconds
	public static final int refreshRate = GWT.isScript() ? 5 : 25;

	private DiagramCanvas canvas;
	private Map<Widget,FunctionShape> shapeMap;
	private Set<Connection> connectionSet;

	private int mouseX;
	private int mouseY;

	// Drag Edition status
	private boolean inDragMovablePoint = false;
	private Point highlightPoint;
	private Connection highlightConnection;

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
		LinksClientBundle.INSTANCE.css().ensureInjected();

		// ON MOUSE MOVE
		canvas.addDomHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				DiagramController.this.onMouseMove(event);
			}
		}, MouseMoveEvent.getType());

		// ON MOUSE DOWN
		canvas.addDomHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				DiagramController.this.onMouseDown(event);
			}
		}, MouseDownEvent.getType());

		// ON MOUSE UP
		canvas.addDomHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				DiagramController.this.onMouseUp(event);
			}
		}, MouseUpEvent.getType());


		timer.scheduleRepeating(refreshRate);
	}

	/*
	 * EVENTS
	 */

	private void onCtrlClick(){
		final Connection c = findConnectionNearMouse();
		if(c != null){
			final PopupPanel popupPanel = new PopupPanel(true);
			MenuBar popupMenuBar = new MenuBar(true);
			MenuItem deleteItem = new MenuItem("Delete", true, new Command() {
				@Override
				public void execute() {
					deleteConnection(c);
					popupPanel.hide();
				}
			});
			MenuItem straightItem = new MenuItem("Set straight", true, new Command() {
				@Override
				public void execute() {
					setStraightConnection(c);
					popupPanel.hide();
				}
			});

			popupPanel.setStyleName(LinksClientBundle.INSTANCE.css().connectionPopup());
			deleteItem.addStyleName(LinksClientBundle.INSTANCE.css().connectionPopupItem());
			straightItem.addStyleName(LinksClientBundle.INSTANCE.css().connectionPopupItem());

			popupMenuBar.addItem(deleteItem);
			popupMenuBar.addItem(straightItem);

			popupMenuBar.setVisible(true);
			popupPanel.add(popupMenuBar);
			popupPanel.setPopupPosition(mouseX, mouseY);
			popupPanel.show();
		}
	}

	private void onMouseMove(MouseMoveEvent event){
		mouseX = event.getRelativeX(canvas.getElement());
		mouseY = event.getRelativeY(canvas.getElement());
		if(!inDragMovablePoint){
			highlightMovablePoint(new Point(mouseX, mouseY));
		}
		else{
			highlightPoint.setLeft(mouseX);
			highlightPoint.setTop(mouseY);
		}
	}

	private void onMouseUp(MouseUpEvent event){
		// Test if Right Click
		if(event.isControlKeyDown()){
			event.preventDefault();
			onCtrlClick();
			return;
		}
		
		if(highlightPoint != null)
			inDragMovablePoint = false;
	}

	private void onMouseDown(MouseDownEvent event){
		if(highlightPoint != null){
			highlightConnection.addMovablePoint(highlightPoint);
			inDragMovablePoint = true;
		}
	}
	
	/*
	 * CONNECTION MANAGEMENT METHODS
	 */
	
	private void deleteConnection(Connection c){
		connectionSet.remove(c);
	}
	
	private void setStraightConnection(Connection c){
		c.setStraight();
	}

	private Connection findConnectionNearMouse(){
		for(Connection c : connectionSet){
			if(c.isPointNearConnection(new Point(mouseX,mouseY)))
				return c;
		}
		return null;
	}

	private void highlightMovablePoint(Point p){
		for(Connection c : connectionSet){
			highlightPoint = c.findHighlightPoint(p) ;
			if(highlightPoint!= null){
				// A connection has been found : Set new cursor
				highlightConnection = c;
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
		return;
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
