package com.orange.links.client.connection;

import java.util.List;

import com.orange.links.client.DiagramController;
import com.orange.links.client.Shape;
import com.orange.links.client.utils.Point;

public class StraightArrowConnection extends AbstractConnection implements Connection{

	private int arrowLength = 8;
	private int cubicMargin = 5;
	private double arrowAngle = Math.PI/8;
	
	public StraightArrowConnection(DiagramController controller, Shape startShape, Shape endShape){
		super(controller, startShape, endShape);
	}

	public StraightArrowConnection(DiagramController controller, Shape startShape, Shape endShape, boolean selectable){
		super(controller, startShape, endShape, selectable);
	}

	@Override
	public void draw(Point p1, Point p2, boolean lastPoint) {

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
		
		canvas.beginPath();
		canvas.moveTo(p0.getLeft(),p0.getTop());
		
		for(int i = 1; i < pointList.size()-1 ; i++){
			p0 = pointList.get(i-1);
			p1 = pointList.get(i);
			canvas.lineTo(p1.getLeft(), p1.getTop());
		}
		
		p0 = p1;
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
