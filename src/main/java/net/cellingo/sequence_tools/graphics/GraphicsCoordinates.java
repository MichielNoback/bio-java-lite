package net.cellingo.sequence_tools.graphics;

/**
 * Specifies GUI-related positioning properties. I
 * @author Cellingo
 *
 */
public class GraphicsCoordinates {
	private int top;
	private int left;
	private int width;
	private int height;
	
	/**
	 * construct with all properties
	 * @param top
	 * @param left
	 * @param width
	 * @param height
	 */
	public GraphicsCoordinates(int top, int left, int width, int height) {
		super();
		this.top = top;
		this.left = left;
		this.width = width;
		this.height = height;
	}

	/**
	 * construct with only width and height. Leaves top and left 0
	 * @param width
	 * @param height
	 */
	public GraphicsCoordinates(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	/**
	 * tests whether the given point is located within this GraphicsCoordinates object
	 * @param x
	 * @param y
	 * @return overlaps
	 */
	public boolean overlaps( int x, int y ){
		if( top > y ) return false;
		if( left > x ) return false;
		if( (left + width ) < x ) return false;
		if( (top + height ) < y ) return false;
		return true;
	}
	
	/**
	 * @return the top
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @return the left
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String toString(){
		return this.getClass().getSimpleName() + "[ top=" + top + " left=" + left + " width=" + width + " height=" + height + "]";
	}
}
