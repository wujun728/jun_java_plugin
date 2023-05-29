package net.jueb.util4j.beta.astr;

import java.io.Serializable;


public class Point implements Serializable{
 
	/*
     * JDK 1.1 serialVersionUID 
     */
    private static final long serialVersionUID = -5276940640259749850L;
	
	   /**
     * The X coordinate of this <code>Point</code>.
     * If no X coordinate is set it will default to 0.
     *
     * @serial
     * @see #getLocation()
     * @see #move(int, int)
     * @since 1.0
     */
    public int x;

    /**
     * The Y coordinate of this <code>Point</code>. 
     * If no Y coordinate is set it will default to 0.
     *
     * @serial
     * @see #getLocation()
     * @see #move(int, int)
     * @since 1.0
     */
    public int y;


    /**
     * Constructs and initializes a point at the origin 
     * (0,&nbsp;0) of the coordinate space. 
     * @since       1.1
     */
    public Point() {
    	this(0, 0);
    }

    /**
     * Constructs and initializes a point with the same location as
     * the specified <code>Point</code> object.
     * @param       p a point
     * @since       1.1
     */
    public Point(Point p) {
    	this(p.x, p.y);
    }

    /**
     * Constructs and initializes a point at the specified 
     * {@code (x,y)} location in the coordinate space. 
     * @param x the X coordinate of the newly constructed <code>Point</code>
     * @param y the Y coordinate of the newly constructed <code>Point</code>
     * @since 1.0
     */
    public Point(int x, int y) {
		this.x = x;
		this.y = y;
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public int getX() {
    	return x;
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public int getY() {
    	return y;
    }

    /**
     * Returns the location of this point.
     * This method is included for completeness, to parallel the
     * <code>getLocation</code> method of <code>Component</code>.
     * @return      a copy of this point, at the same location
     * @see         java.awt.Component#getLocation
     * @see         java.awt.Point#setLocation(java.awt.Point)
     * @see         java.awt.Point#setLocation(int, int)
     * @since       1.1
     */
    public Point getLocation() {
    	return new Point(x, y);
    }	

    /**
     * Sets the location of the point to the specified location.
     * This method is included for completeness, to parallel the
     * <code>setLocation</code> method of <code>Component</code>.
     * @param       p  a point, the new location for this point
     * @see         java.awt.Component#setLocation(java.awt.Point)
     * @see         java.awt.Point#getLocation
     * @since       1.1
     */
    public void setLocation(Point p) {
    	setLocation(p.x, p.y);
    }	

    /**
     * Changes the point to have the specified location.
     * <p>
     * This method is included for completeness, to parallel the
     * <code>setLocation</code> method of <code>Component</code>.
     * Its behavior is identical with <code>move(int,&nbsp;int)</code>.
     * @param       x the X coordinate of the new location
     * @param       y the Y coordinate of the new location
     * @see         java.awt.Component#setLocation(int, int)
     * @see         java.awt.Point#getLocation
     * @see         java.awt.Point#move(int, int)
     * @since       1.1
     */
    public void setLocation(int x, int y) {
    	move(x, y);
    }	

    /**
     * Sets the location of this point to the specified double coordinates.
     * The double values will be rounded to integer values.
     * Any number smaller than <code>Integer.MIN_VALUE</code>
     * will be reset to <code>MIN_VALUE</code>, and any number
     * larger than <code>Integer.MAX_VALUE</code> will be
     * reset to <code>MAX_VALUE</code>.
     *
     * @param x the X coordinate of the new location
     * @param y the Y coordinate of the new location
     * @see #getLocation
     */
    public void setLocation(double x, double y) {
		this.x = (int) Math.floor(x+0.5);
		this.y = (int) Math.floor(y+0.5);
    }

    /**
     * Moves this point to the specified location in the 
     * {@code (x,y)} coordinate plane. This method
     * is identical with <code>setLocation(int,&nbsp;int)</code>.
     * @param       x the X coordinate of the new location
     * @param       y the Y coordinate of the new location
     * @see         java.awt.Component#setLocation(int, int)
     */
    public void move(int x, int y) {
		this.x = x;
		this.y = y;
    }	

    /**
     * Translates this point, at location {@code (x,y)}, 
     * by {@code dx} along the {@code x} axis and {@code dy} 
     * along the {@code y} axis so that it now represents the point 
     * {@code (x+dx,y+dy)}.
     *
     * @param       dx   the distance to move this point 
     *                            along the X axis
     * @param       dy    the distance to move this point 
     *                            along the Y axis
     */
    public void translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
    }	

    /**
     * Determines whether or not two points are equal. Two instances of
     * <code>Point2D</code> are equal if the values of their 
     * <code>x</code> and <code>y</code> member fields, representing
     * their position in the coordinate space, are the same.
     * @param obj an object to be compared with this <code>Point2D</code>
     * @return <code>true</code> if the object to be compared is
     *         an instance of <code>Point2D</code> and has
     *         the same values; <code>false</code> otherwise.
     */

    /**
     * Returns a string representation of this point and its location 
     * in the {@code (x,y)} coordinate space. This method is 
     * intended to be used only for debugging purposes, and the content 
     * and format of the returned string may vary between implementations. 
     * The returned string may be empty but may not be <code>null</code>.
     * 
     * @return  a string representation of this point
     */
    public String toString() {
    	return "[x=" + x + ",y=" + y + "]";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Point other = (Point) obj;
		if (x != other.x){
			return false;
		}
		if (y != other.y){
			return false;
		}
		return true;
	}
	
}
