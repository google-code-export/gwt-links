package com.orange.links.client.utils;


public class Direction {

    public static final Direction N = new Direction("N", Math.PI/2);
    public static final Direction NE = new Direction("NE", Math.PI/2);
    public static final Direction NW = new Direction("NE", Math.PI/2);
    public static final Direction S = new Direction("S", 3*Math.PI/2);
    public static final Direction SW = new Direction("SW", 3*Math.PI/2);
    public static final Direction SE = new Direction("SE", 3*Math.PI/2);
    public static final Direction E = new Direction("E", Math.PI);
    public static final Direction W = new Direction("W", 0);
    
    private final String id;
    private final double angle;
    
    private Direction(String id, double angle){
        this.id = id;
        this.angle = angle;
    }

    /**
     * @return all defined directions
     */
    public static Direction[] getAll(){
    	return new Direction[]{S, E, W, N,SE,SW,NE,NW};
    }
    
    /**
     * @return true if it is horizontal direction
     */
    public boolean isHorizontal(){
    	return this == W || this == E;
    }
    
    /**
     * @return true if it is vertical direction
     */
    public boolean isVertical(){
    	return this == N || this == S;
    }

    /**
     * @return representing angle value
     */
    public double getAngle(){
    	return angle;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
    	return id;
    }
    
}

