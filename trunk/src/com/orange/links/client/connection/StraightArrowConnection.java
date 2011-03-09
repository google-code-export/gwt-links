package com.orange.links.client.connection;

import com.orange.links.client.DiagramController;
import com.orange.links.client.Shape;
import com.orange.links.client.utils.Point;

public class StraightArrowConnection extends AbstractConnection implements Connection{

	private int arrowLength = 8;
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
		if(!lastPoint)
			return;

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

}
