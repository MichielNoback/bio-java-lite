package net.cellingo.sequence_tools.graphics;

import net.cellingo.sequence_tools.annotation.SequenceCoordinates;

public interface SequenceMapListener {
	
	public static int SELECTION_TYPE_ALL = 0;
	public static int SELECTION_TYPE_REGION = 1;
	public static int SELECTION_TYPE_TRACK_AND_ELEMENTS = 2;
	
	/**
	 * a region of the sequence was selected
	 * @param sequence
	 * @param coordinates
	 */
	public void regionSelected( SequenceCoordinates coordinates );

	/**
	 * a sequence track was selected
	 * @param track
	 */
	public void trackSelected( SequenceTrack track );
	
	/**
	 * a sequence track element was selected
	 * @param trackElement
	 */
	public void trackElementSelected( SequenceTrackElement trackElement );
	
	/**
	 * implementers have to return in which type of events they are interested in. 
	 * The available types are defined within the interface SequenceMapListener 
	 * @return selection type
	 */
	public int getSelectionType(); 
	
}
