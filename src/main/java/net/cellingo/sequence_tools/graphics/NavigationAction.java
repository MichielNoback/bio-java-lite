package net.cellingo.sequence_tools.graphics;

public enum NavigationAction {
	ZOOM_OUT_10(10),
	ZOOM_OUT_5(5),
	ZOOM_OUT_2(2),
	ZOOM_IN_2(2),
	ZOOM_IN_5(5),
	ZOOM_IN_10(10),
	MOVE_LEFT,
	MOVE_RIGHT,
	MOVE_TO_START,
	MOVE_TO_END,
	GOTO_POSITION;
	
	private int amount;

	private NavigationAction( ){ }
	
	private NavigationAction( int amount ){
		this.amount = amount;
	}
	
	/**
	 * returns navigation amount, if applicable 
	 * @return amount
	 */
	public int getAmount(){
		return amount;
	}
	
}
