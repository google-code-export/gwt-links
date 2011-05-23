package com.orange.links.client;

import java.util.Date;

import com.allen_sauer.gwt.dnd.client.DragController;
import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandlerAdapter;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.canvas.BackgroundCanvas;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.connection.Connection;
import com.orange.links.client.connection.ConnectionFactory;
import com.orange.links.client.event.ChangeOnDiagramEvent;
import com.orange.links.client.event.ChangeOnDiagramEvent.HasChangeOnDiagramHandlers;
import com.orange.links.client.event.ChangeOnDiagramHandler;
import com.orange.links.client.event.TieLinkEvent;
import com.orange.links.client.event.TieLinkEvent.HasTieLinkHandlers;
import com.orange.links.client.event.TieLinkHandler;
import com.orange.links.client.event.UntieLinkEvent;
import com.orange.links.client.event.UntieLinkEvent.HasUntieLinkHandlers;
import com.orange.links.client.event.UntieLinkHandler;
import com.orange.links.client.menu.ContextMenu;
import com.orange.links.client.menu.HasContextMenu;
import com.orange.links.client.shapes.DecorationShape;
import com.orange.links.client.shapes.DrawableSet;
import com.orange.links.client.shapes.FunctionShape;
import com.orange.links.client.shapes.MouseShape;
import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;
import com.orange.links.client.utils.LinksClientBundle;
import com.orange.links.client.utils.MovablePoint;

/**
 * Controller which manage all the diagram logic
 * 
 * @author Pierre Renaudin (pierre.renaudin.fr@gmail.com)
 * @author David Durham (david.durham.jr@gmail.com)
 * 
 */
public class DiagramController implements HasTieLinkHandlers, HasUntieLinkHandlers, HasChangeOnDiagramHandlers, HasContextMenu {

    /**
     * If the distance between the mouse and segment is under this number in pixels, then,
     * the mouse is considered over the segment
     */
    public static int minDistanceToSegment = 10;

    /**
     * Timer refresh duration, in milliseconds. It defers if the application is running in development mode
     * or in the web mode
     */
    public static int refreshRate = GWT.isScript() ? 25 : 500;

    private DiagramCanvas canvas;
    private DragController dragController;
    private BackgroundCanvas backgroundCanvas;
    private AbsolutePanel widgetPanel;
    private HandlerManager handlerManager;
    private boolean showGrid;

    private ContextMenu canvasMenu;

    private DrawableSet<Connection> connections = new DrawableSet<Connection>();
    private DrawableSet<FunctionShape> shapes = new DrawableSet<FunctionShape>();

    // private Map<Widget, FunctionShape> shapeMap = new HashMap<Widget, FunctionShape>();

    private Point mousePoint = new Point(0, 0);
    private Point mouseOffsetPoint = new Point(0, 0);

    // Drag Edition status
    private boolean inEditionDragMovablePoint = false;
    private boolean inEditionSelectableShapeToDrawConnection = false;
    private boolean inDragBuildArrow = false;
    private boolean inDragMovablePoint = false;

    private Point highlightPoint;
    private Connection highlightConnection;
    private MovablePoint movablePoint;

    private Widget startFunctionWidget;
    private Connection buildConnection;

    long nFrame = 0;
    long previousNFrame = 0;
    long previousTime = 0;
    long fps = 0;

    /**
     * Initialize the controller diagram. Use this constructor to start your diagram. A code sample is : <br/>
     * <br/>
     * <code>
     * 		DiagramCanvas canvas = new MultiBrowserDiagramCanvas(400,400);<br/>
     * 		DiagramController controller = new DiagramController(canvas);<br/>
     * </code> <br/>
     * 
     * @param canvas
     *            the implementation of the canvas where connections and widgets will be drawn
     */
    public DiagramController(final DiagramCanvas canvas) {
        this.canvas = canvas;
        this.backgroundCanvas = new BackgroundCanvas(canvas.getWidth(), canvas.getHeight());

        handlerManager = new HandlerManager(canvas);
        LinksClientBundle.INSTANCE.css().ensureInjected();

        initWidgetPanel(canvas);
        initMouseHandlers(canvas);
        initMenu();

        timer.scheduleRepeating(refreshRate);
        frameTimer.scheduleRepeating(1000);

        ContextMenu.disableBrowserContextMenu(widgetPanel.asWidget().getElement());
        ContextMenu.disableBrowserContextMenu(canvas.asWidget().getElement());
    }

    protected void initMouseHandlers(final DiagramCanvas canvas) {
        canvas.addDomHandler(new MouseMoveHandler() {
            @Override
            public void onMouseMove(MouseMoveEvent event) {
                DiagramController.this.onMouseMove(event);
            }
        }, MouseMoveEvent.getType());

        canvas.addDomHandler(new MouseDownHandler() {
            @Override
            public void onMouseDown(MouseDownEvent event) {
                DiagramController.this.onMouseDown(event);
            }
        }, MouseDownEvent.getType());

        canvas.addDomHandler(new MouseUpHandler() {
            @Override
            public void onMouseUp(MouseUpEvent event) {
                DiagramController.this.onMouseUp(event);
            }
        }, MouseUpEvent.getType());
    }

    protected void initWidgetPanel(final DiagramCanvas canvas) {
        widgetPanel = new AbsolutePanel();
        widgetPanel.getElement().getStyle().setWidth(canvas.getWidth(), Unit.PX);
        widgetPanel.getElement().getStyle().setHeight(canvas.getHeight(), Unit.PX);
        widgetPanel.add(canvas.asWidget());
    }

    protected void initMenu() {
        canvasMenu = new ContextMenu();
        canvasMenu.addItem(new MenuItem("About", new Command() {
            @Override
            public void execute() {
                Window.alert("http://gwt-links.googlecode.com/");
            }
        }));
    }

    @Override
    public ContextMenu getContextMenu() {
        return canvasMenu;
    }

    public void pauseRefresh() {
        timer.cancel();
    }

    public void runRefresh() {
        timer.scheduleRepeating(refreshRate);
    }

    /**
     * Clear the diagram (connections and widgets)
     */
    public void clearDiagram() {
        connections.clear();
        // shapeMap.clear();
        shapes.clear();
        startFunctionWidget = null;
        buildConnection = null;

        // Restart widgetPanel
        widgetPanel.clear();
        widgetPanel.add(canvas.asWidget());
        canvas.getElement().getStyle().setPosition(Position.ABSOLUTE);
        showGrid(showGrid);
    }

    /**
     * Draw a straight connection with an arrow between two GWT widgets.
     * The arrow is pointing to the second widget
     * 
     * @param startWidget
     *            Start widget
     * @param endWidget
     *            End Widget
     * @return the created new connection between the two widgets
     */
    public Connection drawStraightArrowConnection(Widget startWidget, Widget endWidget) {
        return drawConnection(ConnectionFactory.ARROW, startWidget, endWidget);
    }

    private <C extends Connection> C drawConnection(ConnectionFactory<C> cf, Widget start, Widget end) {
        FunctionShape startShape = new FunctionShape(this, start);
        if (!shapes.contains(startShape)) {
            shapes.add(startShape);
        }

        FunctionShape endShape = new FunctionShape(this, end);
        if (!shapes.contains(endShape)) {
            shapes.add(endShape);
        }
        
        return drawConnection(cf, startShape, endShape);
    }

    private <C extends Connection> C drawConnection(ConnectionFactory<C> cf, Shape start, Shape end) {
        // Create Connection and Store it in the controller
        C c = cf.create(start, end);
        c.setController(this);
        connections.add(c);
        start.addConnection(c);
        end.addConnection(c);
        return c;
    }

    /**
     * Draw a straight connection between two GWT widgets. The arrow is pointing to the second widget
     * 
     * @param startWidget
     *            Start widget
     * @param endWidget
     *            End Widget
     * @return the created new connection between the two widgets
     */
    public Connection drawStraightConnection(Widget startWidget, Widget endWidget) {
        return drawConnection(ConnectionFactory.STRAIGHT, startWidget, endWidget);
    }

    /**
     * Add a widget on the diagram
     * 
     * @param w
     *            the widget to add
     * @param left
     *            left margin with the absolute panel
     * @param top
     *            top margin with the absolute panel
     */
    public void addWidget(final Widget w, int left, int top) {
        w.getElement().getStyle().setZIndex(3);
        final FunctionShape shape = new FunctionShape(this, w);
        shapes.add(shape);
        if (w instanceof HasContextMenu) {
            w.addDomHandler(new MouseUpHandler() {
                @Override
                public void onMouseUp(MouseUpEvent event) {
                    if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
                        showMenu((HasContextMenu) w, event.getClientX(), event.getClientY());
                    }
                }
            }, MouseUpEvent.getType());
        }
        if (dragController != null) {
            dragController.addDragHandler(new DragHandlerAdapter() {

                @Override
                public void onDragEnd(DragEndEvent event) {
                    shape.setSynchronized(true);
                    shape.getConnections().allowSynchronized(true);
                }
                
                @Override
                public void onDragStart(DragStartEvent event) {
                    shape.setSynchronized(false);
                    shape.getConnections().allowSynchronized(false);
                }
            });
        }
        widgetPanel.add(w, left, top);
    }

    public void addWidgetAtMousePoint(final Widget w) {
        addWidget(w, mousePoint.getLeft(), mousePoint.getTop());
    }

    /**
     * Add a widget as a decoration on a connection
     * 
     * @param decoration
     *            widget that will be in the middle of the connection
     * @param decoratedConnection
     *            the connection where the decoration will be put
     */
    public void addDecoration(Widget decoration, Connection decoratedConnection) {
        decoration.getElement().getStyle().setZIndex(10);
        decoration.getElement().getStyle().setPosition(Position.ABSOLUTE);
        widgetPanel.add(decoration);
        decoratedConnection.setDecoration(new DecorationShape(this, decoration));
    }

    /**
     * Remove a decoration from the diagram
     * 
     * @param decoratedConnection
     *            connection where the decoration will be deleted
     */
    public void removeDecoration(Connection decoratedConnection) {
        DecorationShape decoShape = decoratedConnection.getDecoration();
        if (decoShape != null) {
            widgetPanel.remove(decoShape.asWidget());
            decoratedConnection.removeDecoration();
        }
    }

    /**
     * Add an segment on a path by adding a point on the connection
     * 
     * @param c
     *            the connection where the point will be added
     * @param left
     *            Left margin in pixels
     * @param top
     *            Top margin in pixels
     */
    public void addPointOnConnection(Connection c, int left, int top) {
        c.addMovablePoint(new Point(left, top));
    }

    /**
     * Change the background of the canvas by displaying or not a gray grid.
     * 
     * @param showGrid
     *            if true, show a grid, else don't
     */
    public void showGrid(boolean showGrid) {
        this.showGrid = showGrid;
        backgroundCanvas.initGrid();
        if (this.showGrid) {
            widgetPanel.add(backgroundCanvas.asWidget());
        } else {
            widgetPanel.remove(backgroundCanvas.asWidget());
        }
    }

    /**
     * Get the diagram canvas
     * 
     * @return the diagram canvas
     */
    public DiagramCanvas getDiagramCanvas() {
        return canvas;
    }

    /**
     * 
     * @return the view where the widgets are displayed
     */
    public AbsolutePanel getView() {
        return widgetPanel;
    }

    /**
     * Register a drag controller to control the refresh rate
     * 
     * @param dragController
     *            The DragController used to handle the drags on widgets
     */
    public void registerDragController(DragController dragController) {
        this.dragController = dragController;
        // Register on drag controller
        dragController.addDragHandler(new DragHandlerAdapter() {
            @Override
            public void onPreviewDragStart(DragStartEvent event) throws VetoDragException {
                runRefresh();
            }
            
            @Override
            public void onDragStart(DragStartEvent event) {
            }

            @Override
            public void onPreviewDragEnd(DragEndEvent event) throws VetoDragException {
            }

            @Override
            public void onDragEnd(DragEndEvent event) {
                pauseRefresh();
            }
        });
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }

    @Override
    public HandlerRegistration addUntieLinkHandler(UntieLinkHandler handler) {
        return handlerManager.addHandler(UntieLinkEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addTieLinkHandler(TieLinkHandler handler) {
        return handlerManager.addHandler(TieLinkEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addChangeOnDiagramHandler(ChangeOnDiagramHandler handler) {
        return handlerManager.addHandler(ChangeOnDiagramEvent.getType(), handler);
    }

    /**
     * 
     * @return true if on click, the connection will receive a new movable point
     */
    public boolean isInEditionDragMovablePoint() {
        return inEditionDragMovablePoint;
    }

    /**
     * 
     * @return true if on click, a new build arrow will be drawn between the
     */
    public boolean isInEditionSelectableShapeToDrawConnection() {
        return inEditionSelectableShapeToDrawConnection;
    }

    /**
     * 
     * @return true if the user is building an arrow (the mouse is down and a arrow is displayed)
     */
    public boolean isInDragBuildArrow() {
        return inDragBuildArrow;
    }

    /**
     * 
     * @return true if the user is dragging a movable point (the mouse is down and a curve is displayed)
     */
    public boolean isInDragMovablePoint() {
        return inDragMovablePoint;
    }

    /**
     * 
     * @return true if a grid is displayed in background
     */
    public boolean isShowGrid() {
        return showGrid;
    }

    // setup timer
    private final Timer timer = new Timer() {
        @Override
        public void run() {
            nFrame++;
            update();
        }
    };

    private final Timer frameTimer = new Timer() {
        @Override
        public void run() {
            long now = new Date().getTime();
            fps = (now - previousTime) != 0 ? (nFrame - previousNFrame) * 1000 / (now - previousTime) : 0;
            previousNFrame = nFrame;
            previousTime = now;
        }
    };

    /**
     * 
     * @return the fps which are really displayed (frame per second)
     */
    public long getFps() {
        return fps;
    }

    private void update() {
        redrawConnections();

        // Search for selectable area
        if (!inDragBuildArrow) {
            for (FunctionShape s : shapes) {
                if (s.isMouseNearSelectableArea(mousePoint)) {
                    s.highlightSelectableArea(mousePoint);
                    inEditionSelectableShapeToDrawConnection = true;
                    startFunctionWidget = s.asWidget();
                    RootPanel.getBodyElement().getStyle().setCursor(Cursor.POINTER);
                    return;
                }
                inEditionSelectableShapeToDrawConnection = false;
            }
        } else {
            // Don't go deeper if in edition mode
            // If mouse over a widget, highlight it
            FunctionShape s = getShapeUnderMouse();
            if (s != null) {
                s.drawHighlight();
            }
            clearAnimationsOnCanvas();
        }

        // Test if in Drag Movable Point

        if (!inDragMovablePoint && !inDragBuildArrow) {
            for (Connection c : connections) {
                if (c.isMouseNearConnection(mousePoint)) {
                    highlightPoint = c.highlightMovablePoint(mousePoint);
                    highlightConnection = getConnectionNearMouse();
                    inEditionDragMovablePoint = true;
                    RootPanel.getBodyElement().getStyle().setCursor(Cursor.POINTER);
                    return;
                }
                inEditionDragMovablePoint = false;
            }
        }

        clearAnimationsOnCanvas();
    }

    /**
     * If any connections need to be redrawn, clear the canvas and redraw all lines.
     */
    protected void redrawConnections() {
        if (!connections.isSynchronized()) {
            canvas.clear();
            connections.draw();
        }
    }

    private void clearAnimationsOnCanvas() {
        RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
    }

    private void showContextualMenu() {
        final Connection c = getConnectionNearMouse();
        if (c != null) {
            showMenu(c);
            return;
        }

        showMenu(this);
    }

    private void showMenu(final HasContextMenu c) {
        showMenu(c, mouseOffsetPoint.getLeft(), mouseOffsetPoint.getTop());
    }

    private void showMenu(final HasContextMenu c, int left, int top) {
        ContextMenu menu = c.getContextMenu();
        if (menu != null) {
            menu.setPopupPosition(left, top);
            menu.show();
        }
    }

    private void onMouseMove(MouseMoveEvent event) {
        int mouseX = event.getRelativeX(canvas.getElement());
        int mouseY = event.getRelativeY(canvas.getElement());
        mousePoint.setLeft(mouseX);
        mousePoint.setTop(mouseY);

        int offsetMouseX = event.getClientX();
        int offsetMouseY = event.getClientY();
        mouseOffsetPoint.setLeft(offsetMouseX);
        mouseOffsetPoint.setTop(offsetMouseY);
    }

    private void onMouseUp(MouseUpEvent event) {

        // Test if Right Click
        if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
            event.stopPropagation();
            event.preventDefault();
            showContextualMenu();
            return;
        }

        if (inDragMovablePoint) {
            movablePoint.setFixed(true);
            inDragMovablePoint = false;
            return;
        }

        if (inDragBuildArrow) {
            Widget widgetSelected = getShapeUnderMouse().asWidget();
            if (widgetSelected != null && startFunctionWidget != widgetSelected) {
                Connection c = drawStraightArrowConnection(startFunctionWidget, widgetSelected);
                fireEvent(new TieLinkEvent(startFunctionWidget, widgetSelected, c));
            }
            canvas.setBackground();
            connections.remove(buildConnection);
            inDragBuildArrow = false;
            buildConnection = null;
            for (FunctionShape s : shapes) {
                if (!s.isSynchronized()) {
                    s.draw();
                }
            }
            clearAnimationsOnCanvas();
        }

        if (inEditionDragMovablePoint) {
            inEditionDragMovablePoint = false;
            clearAnimationsOnCanvas();
        }
    }

    private void onMouseDown(MouseDownEvent event) {
        // Test if Right Click
        if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
            return;
        }

        if (inEditionSelectableShapeToDrawConnection) {
            inDragBuildArrow = true;
            inEditionSelectableShapeToDrawConnection = false;
            drawBuildArrow(startFunctionWidget, mousePoint);
            return;
        }

        if (inEditionDragMovablePoint) {
            inDragMovablePoint = true;
            inEditionDragMovablePoint = false;
            movablePoint = highlightConnection.addMovablePoint(highlightPoint);
            movablePoint.setTrackPoint(mousePoint);
            return;
        }
    }

    /*
     * CONNECTION MANAGEMENT METHODS
     */

    public void deleteConnection(Connection c) {
        connections.remove(c);
        removeDecoration(c);
    }

    private Connection getConnectionNearMouse() {
        for (Connection c : connections) {
            if (c.isMouseNearConnection(mousePoint)) {
                return c;
            }
        }
        return null;
    }

    private void drawBuildArrow(Widget startFunctionWidget, Point mousePoint) {
        canvas.setForeground();
        Shape startShape = new FunctionShape(this, startFunctionWidget);
        final MouseShape endShape = new MouseShape(mousePoint);
        buildConnection = drawConnection(ConnectionFactory.ARROW, startShape, endShape);
        buildConnection.allowSynchronized(false);
        connections.add(buildConnection);
    }

    public Point getMousePoint() {
        return mousePoint;
    }

    private FunctionShape getShapeUnderMouse() {
        for (FunctionShape s : shapes) {
            if (mousePoint.isInside(s)) {
                return s;
            }
        }
        return null;
    }

}
