package com.orange.links.client.shapes;

import com.orange.links.client.connection.Connection;

/**
 * Interface to represent a point, widget or mouse as a rectangle
 * 
 * @author Pierre Renaudin (pierre.renaudin.fr@gmail.com)
 * 
 */
public interface Shape extends Drawable {

    /**
     * 
     * @return left margin
     */
    public int getLeft();

    /**
     * 
     * @return top margin
     */
    public int getTop();

    /**
     * 
     * @return width of the shape
     */
    public int getWidth();

    /**
     * 
     * @return height of the shape
     */
    public int getHeight();

    /**
     * Add a connection to this shape.
     * 
     * @return whether or not a connection was added
     */
    public boolean addConnection(Connection connection);

    /**
     * Remove a connection to this shape.
     * 
     * @return whether or not a connection was removed
     */
    public boolean removeConnection(Connection connection);

}
