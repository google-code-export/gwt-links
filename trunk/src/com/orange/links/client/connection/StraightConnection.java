package com.orange.links.client.connection;

import com.orange.links.client.DiagramController;
import com.orange.links.client.Shape;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.Point;

public class StraightConnection extends AbstractConnection implements Connection{

	public StraightConnection(DiagramController controller, Shape startShape,
			Shape endShape) {
		super(controller, startShape, endShape);
	}
	
	public StraightConnection(DiagramController controller, Shape startShape,
			Shape endShape, boolean selectable) {
		super(controller, startShape, endShape,selectable);
	}

	@Override
	public void draw(Point p1, Point p2, boolean lastPoint) {
		DiagramCanvas canvas = controller.getDiagramCanvas();
	
		canvas.beginPath();
		canvas.moveTo(p1.getLeft(),p1.getTop());
		canvas.lineTo(p2.getLeft(), p2.getTop());

		canvas.setStrokeStyle(connectionColor);
		canvas.stroke();
		canvas.closePath();
	}


}
