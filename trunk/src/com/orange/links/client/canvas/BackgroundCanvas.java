package com.orange.links.client.canvas;

public class BackgroundCanvas extends MultiBrowserDiagramCanvas{

	private final int cellSize = 10;

	public BackgroundCanvas(int width, int height) {
		super(width, height);
		getElement().getStyle().setZIndex(0);
		initGrid();
	}

	private void initGrid(){
		for (double x = 1.5; x < width; x += cellSize) {
			moveTo(x, 0);
			lineTo(x, height);
		}
		for (double y = 1.5; y < height; y += cellSize) {
			moveTo(0, y);
			lineTo(width, y);
		}
		setStrokeStyle("#eee");
		stroke();
	}


}
