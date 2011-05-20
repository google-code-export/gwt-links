package com.orange.links.client.connection;

import java.util.List;

import com.orange.links.client.DiagramController;
import com.orange.links.client.exception.DiagramViewNotDisplayedException;
import com.orange.links.client.shapes.Point;
import com.orange.links.client.shapes.Shape;
import com.orange.links.client.utils.Segment;

public class StraightArrowConnection extends AbstractConnection {

	private int arrowLength = 8;
	private int cubicMargin = 25;
	private double arrowAngle = Math.PI/8;
	
	public StraightArrowConnection(DiagramController controller, Shape startShape, Shape endShape) throws DiagramViewNotDisplayedException{
		super(controller, startShape, endShape);
	}

	@Override
	public void draw(Point p1, Point p2, boolean lastPoint){

		canvas.beginPath();
		canvas.moveTo(p1.getLeft(),p1.getTop());
		canvas.lineTo(p2.getLeft(), p2.getTop());
		
		// If it is not the last point, 
		if(!lastPoint){
			canvas.setStrokeStyle(connectionColor);
			canvas.stroke();
			canvas.closePath();
			return;
		}

		double linkAngle = Math.acos((p2.getLeft()-p1.getLeft())/Math.sqrt(Math.pow(p2.getLeft()-p1.getLeft(),2)+Math.pow(p2.getTop()-p1.getTop(),2)));
		if(p2.getTop()<p1.getTop())
			linkAngle = linkAngle * -1;

		canvas.moveTo(p2.getLeft(),p2.getTop());
		double a1Left = p2.getLeft() - arrowLength*Math.cos(linkAngle - arrowAngle);
		double a1Top = p2.getTop() - arrowLength*Math.sin(linkAngle - arrowAngle);
		canvas.lineTo(a1Left, a1Top);

		canvas.moveTo(p2.getLeft(),p2.getTop());
		double a2Left = p2.getLeft() - arrowLength*Math.cos(linkAngle + arrowAngle);
		double a2Top = p2.getTop() - arrowLength*Math.sin(linkAngle + arrowAngle);
		canvas.lineTo(a2Left, a2Top);

		canvas.setStrokeStyle(connectionColor);
		canvas.stroke();
		canvas.closePath();
	}

	@Override
	protected void draw(List<Point> pointList) {
		Point p0 = pointList.get(0);
		Point p1 = pointList.get(1);
		Point p2;
		
		canvas.beginPath();
		canvas.moveTo(p0.getLeft(),p0.getTop());
		
		// Curve only for intermediate point
		for(int i = 1; i < pointList.size()-1 ; i++){
			p0 = pointList.get(i-1);
			p1 = pointList.get(i);
			p2 = pointList.get(i+1);
			
			double marginPrev = p1.distance(p0) < cubicMargin ? p1.distance(p0)/4 : cubicMargin;
			double marginNext = p1.distance(p2) < cubicMargin ? p1.distance(p2)/4 : cubicMargin;
			
			// source point
			Segment prevSegment = new Segment(p0, p1);
			double prevAngle = prevSegment.getAngleWithTop();
			double srcx = p1.getLeft()-marginPrev*Math.cos(prevAngle);
			double srcy = p1.getTop()-marginPrev*Math.sin(prevAngle);
			
			// control point
			double cpx = p1.getLeft();
			double cpy = p1.getTop();
			
			// destination point
			Segment nextSegment = new Segment(p1, p2);
			double nextAngle = nextSegment.getAngleWithTop();
			double x = p1.getLeft()+marginNext*Math.cos(nextAngle);
			double y = p1.getTop()+marginNext*Math.sin(nextAngle);
			
			canvas.lineTo(srcx, srcy);
			canvas.bezierCurveTo(cpx, cpy, x, y);
		}
		
		
		p0 = pointList.get(pointList.size()-2);
		p1 = pointList.get(pointList.size()-1);
		canvas.lineTo(p1.getLeft(), p1.getTop());
		
		double linkAngle = Math.acos((p1.getLeft()-p0.getLeft())/Math.sqrt(Math.pow(p1.getLeft()-p0.getLeft(),2)+Math.pow(p1.getTop()-p0.getTop(),2)));
		if(p1.getTop()<p0.getTop())
			linkAngle = linkAngle * -1;

		canvas.moveTo(p1.getLeft(),p1.getTop());
		double a1Left = p1.getLeft() - arrowLength*Math.cos(linkAngle - arrowAngle);
		double a1Top = p1.getTop() - arrowLength*Math.sin(linkAngle - arrowAngle);
		canvas.lineTo(a1Left, a1Top);

		canvas.moveTo(p1.getLeft(),p1.getTop());
		double a2Left = p1.getLeft() - arrowLength*Math.cos(linkAngle + arrowAngle);
		double a2Top = p1.getTop() - arrowLength*Math.sin(linkAngle + arrowAngle);
		canvas.lineTo(a2Left, a2Top);

		canvas.setStrokeStyle(connectionColor);
		canvas.stroke();
		canvas.closePath();
	}

}
