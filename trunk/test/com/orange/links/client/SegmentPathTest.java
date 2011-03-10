package com.orange.links.client;

import junit.framework.TestCase;

import com.orange.links.client.utils.Point;
import com.orange.links.client.utils.SegmentPath;

public class SegmentPathTest extends TestCase{

	public void testComplete(){
		Point sPoint = new Point(1,1);
		Point ePoint = new Point(5,4);
		SegmentPath segmentPath = new SegmentPath(sPoint, ePoint);
		
		// Add point
		Point p1 = new Point(3,1);
		segmentPath.add(p1, sPoint, ePoint);
		assertTrue(segmentPath.getPath().get(0) == sPoint);
		assertTrue(segmentPath.getPath().get(1) == p1);
		assertTrue(segmentPath.getPath().get(2) == ePoint);
		
		// Add point
		Point p2 = new Point(5,2);
		segmentPath.add(p2, p1, ePoint);
		assertTrue(segmentPath.getPath().get(0) == sPoint);
		assertTrue(segmentPath.getPath().get(1) == p1);
		assertTrue(segmentPath.getPath().get(2) == p2);
		assertTrue(segmentPath.getPath().get(3) == ePoint);
		
		// Add point
		Point p3 = new Point(4,1);
		segmentPath.add(p3, p1, p2);
		assertTrue(segmentPath.getPath().get(0) == sPoint);
		assertTrue(segmentPath.getPath().get(1) == p1);
		assertTrue(segmentPath.getPath().get(2) == p3);
		assertTrue(segmentPath.getPath().get(3) == p2);
		assertTrue(segmentPath.getPath().get(4) == ePoint);
		
		
	}
	
	
}
