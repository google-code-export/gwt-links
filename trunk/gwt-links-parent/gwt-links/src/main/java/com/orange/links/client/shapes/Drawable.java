package com.orange.links.client.shapes;


/**
 * Common interface for all shapes and connections.
 */
public interface Drawable {

    /**
     * @param sync whether or not this drawable is synchronized
     */
    void setSynchronized(boolean sync);
    
    /**
     * @return whether or not this drawable is synchronized
     */
    boolean isSynchronized();
    
    /**
     * Draws this object.
     */
    void draw();
    
    /**
     * Draws this object in a "highlighted" manner.
     */
    void drawHighlight();
    
}
