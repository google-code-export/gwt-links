package com.orange.links.client.utils;

public class Segment {

	private Point p1;
	private Point p2;
	
	public Segment(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}
	
	public boolean equals(Segment s){
		return s.getP1().equals(p1) && s.getP2().equals(p2);
	}
	
	@Override
	public String toString(){
		return  p1 + " , " + p2;
	}
	
	public double length(){
		return Math.sqrt((p2.getLeft()-p1.getLeft())^2+(p2.getTop()-p1.getTop())^2);
	}

	public void translate(int left, int top){
		p1.translate(left, top);
		p2.translate(left, top);
	}
}
