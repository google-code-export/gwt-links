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
import com.orange.links.client.utils.Rectangle;

public class DiagramController {

	public final static int KEY_D = 68;
	public final static int KEY_CTRL = 17;
	public static final int minDistanceToSegment = 10;
	//timer refresh rate, in milliseconds
	public static final int refreshRate = GWT.isScript() ? 5 : 25;

	private DiagramCanvas canvas;
	private Map<Widget,FunctionShape> shapeMap;
	private Set<Connection> connectionSet;

	private Point mousePoint;

	// Drag Edition status
	private boolean inEditionDragMovablePoint = false;
	private boolean inEditionSelectableShapeToDrawConnection = false;

	private Point highlightPoint;
	private Connection highlightConnection;

	private Widget startFunctionWidget;
	private Connection buildConnection;

	// setup timer
	private final Timer timer = new Timer() {
		@Override
		public void run() {
			update();
		}
	};

	public void update(){
		//Restore canvas
		canvas.clear();

		// Redraw Connections
		for(Connection c : connectionSet){
			c.draw();
		}

		// Search for selectable area
		for(Widget w : shapeMap.keySet()){
			FunctionShape s = shapeMap.get(w);
			if(s.isMouseNearSelectableArea(mousePoint)){
				s.highlightSelectableArea(mousePoint);
				inEditionSelectableShapeToDrawConnection = true;
				startFunctionWidget = w;
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.POINTER);
				return;
			}
		}

		// Don't go deeper if in edition mode
		if(inEditionSelectableShapeToDrawConnection){
			// If mouse over a widget, highlight it
			Widget w = getWidgetUnderMouse();
			if(w != null){
				highlightWidget(w);
			}
			clearAnimationsOnCanvas();
			return;
		}

		// Test if in Drag Movable Point
		for(Connection c : connectionSet){
			if(c.isMouseNearConnection(mousePoint)){
				c.highlightMovablePoint(mousePoint);
				inEditionDragMovablePoint = true;
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.POINTER);
				return;
			}
			inEditionDragMovablePoint = false;
		}

		clearAnimationsOnCanvas();
	}

	public void clearAnimationsOnCanvas(){
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
	}

	public DiagramController(final DiagramCanvas canvas){
		this.canvas = canvas;	
		mousePoint = new Point(0,0);
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
			popupPanel.setPopupPosition(mousePoint.getLeft(), mousePoint.getTop());
			popupPanel.show();
		}
	}

	private void onMouseMove(MouseMoveEvent event){
		int mouseX = event.getRelativeX(canvas.getElement());
		int mouseY = event.getRelativeY(canvas.getElement());
		mousePoint.setLeft(mouseX);
		mousePoint.setTop(mouseY);
	}

	private void onMouseUp(MouseUpEvent event){
		// Test if Right Click
		if(event.isControlKeyDown()){
			event.preventDefault();
			onCtrlClick();
			return;
		}

		if(inEditionSelectableShapeToDrawConnection){
			Widget widgetSelected = getWidgetUnderMouse();
			if(widgetSelected != null){
				drawStraightArrow(startFunctionWidget, widgetSelected, true);
			}
			canvas.setBackground();
			connectionSet.remove(buildConnection);
			inEditionSelectableShapeToDrawConnection = false;
			buildConnection = null;
			for(Widget w : shapeMap.keySet()){
				removeHighlightWidget(w);
			}
			clearAnimationsOnCanvas();
		}

		if(highlightPoint != null)
			inEditionDragMovablePoint = false;
	}

	private void onMouseDown(MouseDownEvent event){
		if(inEditionSelectableShapeToDrawConnection){
			drawBuildArrow(startFunctionWidget, mousePoint);
		}

		if(highlightPoint != null){
			highlightConnection.addMovablePoint(highlightPoint);
			inEditionDragMovablePoint = true;
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
			if(c.isMouseNearConnection(mousePoint))
				return c;
		}
		return null;
	}

	public Connection drawStraightArrowConnection(Widget startWidget, Widget endWidget){
		// Build Shape for the Widgets
		if(!shapeMap.containsKey(startWidget)){
			shapeMap.put(startWidget,new FunctionShape(this,startWidget));
		}
		Shape startShape = shapeMap.get(startWidget);
		if(!shapeMap.containsKey(endWidget)){
			shapeMap.put(endWidget,new FunctionShape(this,endWidget));
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
			shapeMap.put(startWidget,new FunctionShape(this,startWidget));
		}
		Shape startShape = shapeMap.get(startWidget);
		if(!shapeMap.containsKey(endWidget)){
			shapeMap.put(endWidget,new FunctionShape(this,endWidget));
		}
		Shape endShape = shapeMap.get(endWidget);
		// Create Connection and Store it in the controller
		Connection c = new StraightConnection(this, startShape, endShape);

		connectionSet.add(c);
		return c;
	}

	public DiagramCanvas getDiagramCanvas(){
		return canvas;
	}

	public Connection drawStraightArrow(Widget startWidget, Widget endWidget,
			boolean selectable) {
		// Build Shape for the Widgets
		if(!shapeMap.containsKey(startWidget)){
			shapeMap.put(startWidget,new FunctionShape(this,startWidget));
		}
		Shape startShape = shapeMap.get(startWidget);
		if(!shapeMap.containsKey(endWidget)){
			shapeMap.put(endWidget,new FunctionShape(this,endWidget));
		}
		Shape endShape = shapeMap.get(endWidget);
		// Create Connection and Store it in the controller
		Connection c = new StraightArrowConnection(this, startShape, endShape, selectable);
		connectionSet.add(c);
		// Update the Canvas
		return c;
	}

	public void drawBuildArrow(Widget startFunctionWidget, Point mousePoint){
		canvas.setForeground();
		Shape startShape = new FunctionShape(this,startFunctionWidget);
		final MouseShape endShape = new MouseShape(mousePoint);
		final Connection c = new StraightArrowConnection(this, startShape, endShape);
		connectionSet.add(c);
		buildConnection = c;
	}

	/*
	 * Build arrow utils method
	 */
	
	private Widget getWidgetUnderMouse(){
		for(Widget w : shapeMap.keySet()){
			Rectangle r = new Rectangle(shapeMap.get(w));
			removeHighlightWidget(w);
			if(r.isInside(mousePoint)){
				return w;
			}
		}
		return null;
	}
	
	private void highlightWidget(Widget w) {
		w.addStyleName(LinksClientBundle.INSTANCE.css().translucide());
	}
	
	private void removeHighlightWidget(Widget w){
		w.removeStyleName(LinksClientBundle.INSTANCE.css().translucide());
	}
}
