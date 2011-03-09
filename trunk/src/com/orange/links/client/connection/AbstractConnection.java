package com.orange.links.client.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.canvas.dom.client.CssColor;
import com.orange.links.client.DiagramController;
import com.orange.links.client.PointShape;
import com.orange.links.client.Shape;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.ConnectionUtils;
import com.orange.links.client.utils.Point;
import com.orange.links.client.utils.PointListComparator;
import com.orange.links.client.utils.Segment;

public abstract class AbstractConnection {

	protected Shape startShape;
	protected Shape endShape;
	protected Set<Segment> segmentSet;
	protected DiagramController controller;
	protected DiagramCanvas canvas;

	protected boolean selectable;
	protected boolean selected;

	protected CssColor defaultConnectionColor = CssColor.make("#000000");
	protected CssColor connectionColor = defaultConnectionColor;
	protected CssColor selectedConnectionColor = CssColor.make("#FF6600");

	protected Point highlightPoint;
	protected List<Point> movablePoints;


	public AbstractConnection(DiagramController controller, Shape startShape, Shape endShape){
		this.controller = controller;
		this.startShape = startShape;
		this.endShape = endShape;
		this.movablePoints = new ArrayList<Point>();
		this.segmentSet = new HashSet<Segment>();
		this.canvas = controller.getDiagramCanvas();
	}

	public AbstractConnection(DiagramController controller, Shape startShape, Shape endShape, boolean selectable){
		this(controller,startShape,endShape);
		this.selectable = selectable;
	}

	protected abstract void draw(Point p1, Point p2, boolean lastPoint);
	
	public void draw(){
		if(highlightPoint != null)
			highlightPoint(highlightPoint);

		// Sort the movable points
		Collections.sort(movablePoints, new PointListComparator());

		// Reset the segments
		segmentSet = new HashSet<Segment>();
		
		// Draw each segment
		Shape s1 = startShape;
		for(Point p : movablePoints){
			Shape s2 = new PointShape(p);
			Segment seg = ConnectionUtils.computeSegment(s1,s2);
			draw(seg.getP1(), seg.getP2(), false);
			segmentSet.add(seg);
			s1 = s2;
		}
		// Draw last segment
		Segment seg = ConnectionUtils.computeSegment(s1,endShape);
		segmentSet.add(seg);
		draw(seg.getP1(), seg.getP2(),true);
	}

	public void addMovablePoint(Point p){
		movablePoints.add(p);
	}

	public void updateMovablePoint(Point p){
		if(movablePoints.contains(p)){
			movablePoints.remove(p);
			movablePoints.add(p);
		}
	}
	
	public Point findHighlightPoint(Point p){
		for(Segment s : segmentSet){
			if(ConnectionUtils.distanceToSegment(s, p) < DiagramController.minDistanceToSegment){
				Point hPoint = ConnectionUtils.projectionOnSegment(s, p);
				setHighlightPoint(hPoint);
				return hPoint;
			}
		}
		return null;
	}
	
	public void highlightPoint(Point p) {
		DiagramCanvas canvas = controller.getDiagramCanvas();
		canvas.beginPath();
		canvas.arc(p.getLeft(), p.getTop(), 5, 0, Math.PI*2, false);
		canvas.closePath();
		canvas.setStrokeStyle("#ccc");
		canvas.stroke();
		canvas.setFillStyle("#ccc");
		canvas.fill();
	}

	public Shape getStartShape() {
		return startShape;
	}

	public Shape getEndShape() {
		return endShape;
	}

	public void changeSelection() {
		if(selectable){
			if(!isSelected()){
				selected = true;
				connectionColor = selectedConnectionColor;
			}
			else{
				selected = false;
				connectionColor = defaultConnectionColor;
			}
		}
	}

	public boolean isSelectable() {
		return selectable;
	}

	public void unselect() {
		selected = false;
		connectionColor = defaultConnectionColor;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public Point getHighlightPoint() {
		return highlightPoint;
	}

	public void setHighlightPoint(Point highlightPoint) {
		this.highlightPoint = highlightPoint;
	}

}
