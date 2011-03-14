package com.orange.links.client.utils;


public class Point {
	protected int left;
	protected int top;
	private Direction direction;
	
	public Point(int left, int top) {
		this.left = left;
		this.top = top;
	}
	
	public Point(double left, double top) {
		this.left = new Double(left).intValue();
		this.top = new Double(top).intValue();
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setTop(int top){
		this.top = top;
	}
	public int getLeft() {
		return left;
	}

	public int getTop() {
		return top;
	}

	public double distance(Point other){
		return Math.sqrt( (left-other.left)*(left-other.left) +  (top-other.top)*(top-other.top) );
	}

	public Point move(Direction dir, int distance){
		if( dir == Direction.DOWN ){
			return new Point(left, top+distance);
		} else if( dir == Direction.UP ){
			return new Point(left, top-distance);
		} else if( dir == Direction.LEFT ){
			return new Point(left-distance, top);
		} else if( dir == Direction.RIGHT ){
			return new Point(left+distance, top);
		} else {
			throw new IllegalStateException();
		}
	}

	public Point move(Point vector){
		return new Point(left+vector.left, top+vector.top);
	}

	public Point negative(){
		return new Point(-left, -top);
	}

	public static boolean equals(Point p1, Point p2){
		return p1.left == p2.left && p1.top == p2.top;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public void translate(int left, int top){
		this.left += left;
		this.top  += top; 
	}
	
	public boolean equals(Point p){
		return p.getLeft() == left && p.getTop() == top;
	}
	
	@Override
	public String toString(){
		return "[ " + left + " ; " + top + " ]";
	}

}
