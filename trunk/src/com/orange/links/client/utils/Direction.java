package com.orange.links.client.utils;


public class Direction {

    public static final Direction UP = new Direction("UP", Math.PI/2);
    public static final Direction DOWN = new Direction("DOWN", 3*Math.PI/2);
    public static final Direction LEFT = new Direction("LEFT", Math.PI);
    public static final Direction RIGHT = new Direction("RIGHT", 0);
    
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
    	return new Direction[]{UP, DOWN, LEFT, RIGHT};
    }
    
    /**
     * @return true if it is horizontal direction
     */
    public boolean isHorizontal(){
    	return this == LEFT || this == RIGHT;
    }
    
    /**
     * @return true if it is vertical direction
     */
    public boolean isVertical(){
    	return this == UP || this == DOWN;
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

