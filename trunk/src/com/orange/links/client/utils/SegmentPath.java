package com.orange.links.client.utils;

import java.util.ArrayList;
import java.util.List;

public class SegmentPath {

	private Point startPoint;
	private Point endPoint;
	private List<Point> pointList;

	public SegmentPath(Point startPoint, Point endPoint){
		this.startPoint = startPoint;
		this.endPoint = endPoint;

		pointList = new ArrayList<Point>();
		pointList.add(startPoint);
		pointList.add(endPoint);
	}

	public void add(Point insertPoint, Point startPoint, Point endPoint){
		int insertPosition;
		for(int i=0;i<pointList.size();i++){
			if(endPoint == pointList.get(i)){
				insertPosition = i;
				pointList.add(insertPosition, insertPoint);
				break;
			}
		}
	}

	public List<Point> getPath(){
		return pointList;
	}
	
	public List<Point> getPathWithoutExtremities(){
		if(pointList.size() > 2)
			return pointList.subList(1, pointList.size()-2);
		return new ArrayList<Point>();
	}

	public void straightPath(){
		pointList.clear();
		pointList.add(startPoint);
		pointList.add(endPoint);
	}
}
