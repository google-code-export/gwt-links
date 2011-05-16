package com.orange.links.client;

import junit.framework.TestCase;

import com.orange.links.client.utils.Point;
import com.orange.links.client.utils.Segment;

public class SegmentTest extends TestCase{

	public void testAngle(){
		Point p0 = new Point(0,0);
		Point p1 = new Point(5,0);
		Point p2 = new Point(0,5);
		assertEquals(0.0, new Segment(p0, p1).getAngleWithTop());
		assertEquals(Math.PI/2, new Segment(p0, p2).getAngleWithTop());
	}
	
}
