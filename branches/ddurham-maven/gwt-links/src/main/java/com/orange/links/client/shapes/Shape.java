package com.orange.links.client.shapes;


/**
 * Interface to represent a point, widget or mouse as a rectangle
 * @author Pierre Renaudin (pierre.renaudin.fr@gmail.com)
 *
 */
public interface Shape {

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
	
}
