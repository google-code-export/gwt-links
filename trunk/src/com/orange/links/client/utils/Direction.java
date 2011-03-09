package com.orange.links.client.utils;


public class Direction {

    public static final Direction UP = new Direction("UP", 0.0f);
    public static final Direction DOWN = new Direction("DOWN", 180.0f);
    public static final Direction LEFT = new Direction("LEFT", 270.0f);
    public static final Direction RIGHT = new Direction("RIGHT", 90.0f);
    
    private final String id;
    private final float angle;
    
    private Direction(String id, float angle){
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
    public float getAngle(){
    	return angle;
    }
    
    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
    	return id;
    }
    
}

