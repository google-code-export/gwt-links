package com.orange.links.client.shapes;

public class MouseShape extends Point {

    private int height = 1;
    private int width = 1;

    public MouseShape(Point mousePoint) {
        super(mousePoint.left, mousePoint.top);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

}
