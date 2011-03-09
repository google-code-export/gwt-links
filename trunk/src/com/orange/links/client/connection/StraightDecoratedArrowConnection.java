package com.orange.links.client.connection;

import com.google.gwt.canvas.dom.client.CssColor;
import com.orange.links.client.DiagramController;
import com.orange.links.client.Shape;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.ConnectionUtils;
import com.orange.links.client.utils.Point;

public class StraightDecoratedArrowConnection extends StraightArrowConnection{

	String decoration = "#";
	CssColor decorationBackgroundColor = CssColor.make("#eee 1");
	CssColor decorationFontColor = CssColor.make("#000 1");
	CssColor decorationBorderColor = CssColor.make("#000 1");
	int decorationBackgroundSize = 15;
	int decorationMarginLeft = 8;
	int decorationMarginTop = 3;
	
	public StraightDecoratedArrowConnection(DiagramController controller,
			Shape startShape, Shape endShape) {
		super(controller, startShape, endShape);
	}
	
	public StraightDecoratedArrowConnection(DiagramController controller,
			Shape startShape, Shape endShape, boolean selectable) {
		super(controller, startShape, endShape, selectable);
	}
	
	@Override
	public void draw(){
		super.draw();
		/*Point centerPoint = ConnectionUtils.middle(startPoint, endPoint);
		DiagramCanvas canvas = controller.getDiagramCanvas();
		canvas.beginPath();
		
		int leftRect = centerPoint.getLeft() - decorationBackgroundSize/2;
		int topRect = centerPoint.getTop() - decorationBackgroundSize/2;
		
		// Decoration Background
		canvas.setFillStyle(decorationBackgroundColor);
		canvas.fillRect(leftRect, topRect , 
				decorationBackgroundSize, decorationBackgroundSize);
		
		// Decoration borders
		canvas.moveTo(leftRect, topRect);
		canvas.lineTo(leftRect+decorationBackgroundSize, topRect);
		canvas.lineTo(leftRect+decorationBackgroundSize, topRect+decorationBackgroundSize);
		canvas.lineTo(leftRect, topRect+decorationBackgroundSize);
		canvas.lineTo(leftRect, topRect);
		canvas.setStrokeStyle(decorationBorderColor);
		canvas.stroke();*/
		
		// Decoration Text
		
		/* canvas.setFillStyle(decorationFontColor);
		canvas.setTextAlign(TextAlign.CENTER);
		canvas.setFont("bold 12px arial");
		canvas.fillText(decoration,leftRect+decorationMarginLeft, 
				topRect+decorationBackgroundSize-decorationMarginTop,decorationBackgroundSize); */
		
		canvas.closePath();
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public String getDecoration() {
		return decoration;
	}

}
