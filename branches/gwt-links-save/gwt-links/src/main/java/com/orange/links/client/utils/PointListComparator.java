package com.orange.links.client.utils;

import java.util.Comparator;

import com.orange.links.client.shapes.Point;

public class PointListComparator implements Comparator<Point>{

	@Override
	public int compare(Point startPoint, Point otherPoint) {
		return new Double(startPoint.distance(otherPoint)).intValue();
	}

}
