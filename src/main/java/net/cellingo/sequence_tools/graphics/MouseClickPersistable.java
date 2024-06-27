package net.cellingo.sequence_tools.graphics;

public interface MouseClickPersistable {
	/**
	 * returns the x start position of the mouse click preceding the mouse motion
	 * @return xMouseStart
	 */
	public int getXmouseStart();
	/**
	 * returns the y start position of the mouse click preceding the mouse motion
	 * @return yMouseStart
	 */
	public int getYmouseStart();
}
