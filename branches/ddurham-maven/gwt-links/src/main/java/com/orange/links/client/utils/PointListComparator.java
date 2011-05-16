package com.orange.links.client.utils;

import java.util.Comparator;

public class PointListComparator implements Comparator<Point>{

	@Override
	public int compare(Point startPoint, Point otherPoint) {
		return new Double(startPoint.distance(otherPoint)).intValue();
	}

}
