package com.orange.links.client;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.Couple;
import com.orange.links.client.utils.Direction;
import com.orange.links.client.utils.Point;

public class FunctionShape extends AbstractShape{

	private int selectableAreaRadius = 7;
	private CssColor highlightSelectableAreaColor = CssColor.make("#FF6600");

	public FunctionShape(DiagramController controller, Widget widget){
		super(controller, widget);
	}

	public boolean isMouseNearSelectableArea(Point mousePoint){
		return getSelectableArea(mousePoint) != null;
	}

	public void highlightSelectableArea(Point mousePoint){
		// Mouse Point
		Couple<Direction,Point> couple = getSelectableArea(mousePoint);
		Direction direction = couple.getFirstArg();
		Point closestSelectablePoint = couple.getSecondArg();
		if(closestSelectablePoint != null){
			DiagramCanvas canvas = controller.getDiagramCanvas();
			canvas.beginPath();
			canvas.arc(closestSelectablePoint.getLeft(), 
					closestSelectablePoint.getTop(), selectableAreaRadius,
					direction.getAngle()-Math.PI/2,
					direction.getAngle()+Math.PI/2,
					direction.equals(Direction.N) || direction.equals(Direction.S));
			canvas.setStrokeStyle(highlightSelectableAreaColor);
			canvas.stroke();
			canvas.setFillStyle(highlightSelectableAreaColor);
			canvas.fill();
			canvas.closePath();
		}
	}
	
	public Couple<Direction,Point> getSelectableArea(Point p){
		// Center of the selectable areas
		Point centerW = new Point(getLeft(),getTop()+getHeight()/2);
		Point centerN = new Point(getLeft()+getWidth()/2,getTop());
		Point centerS = new Point(getLeft()+getWidth()/2,getTop()+getHeight()-1);
		Point centerE = new Point(getLeft()+getWidth()-1,getTop()+getHeight()/2);
		if(p.distance(centerW) <= selectableAreaRadius){
			return new Couple<Direction, Point>(Direction.W, centerW);
		}
		else if(p.distance(centerN) <= selectableAreaRadius){
			return new Couple<Direction, Point>(Direction.N, centerN);
		}
		else if(p.distance(centerS) <= selectableAreaRadius){
			return new Couple<Direction, Point>(Direction.S, centerS);
		}
		else if(p.distance(centerE) <= selectableAreaRadius){
			return new Couple<Direction, Point>(Direction.E, centerE);
		}
		return null;
	}

	
}
