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
    
    /**
     * Whether or not a Drawable can be set as Synchronized per setSynchronized.  E.g., a Connection sets itself as 
     * synced after draw.  If it is connected to a shape being dragged, we don't want to allow it to sync itself.
     * 
     * @param allowSynchronized whether or not the Drawable should allow itself to be set as synchronized.
     */
    void allowSynchronized(boolean allowSynchronized);
    
}
