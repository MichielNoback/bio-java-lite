/**
 * 
 */
package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.SequenceObject;

/**
 * This interface specifies a single method that 
 * listeners to sequence fetcher objects should implement when 
 * streaming processing is desired, with large and / or many sequences:
 * sequenceFetched(). This method is called when a sequence is downloaded 
 * and processed.
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface SequenceFetcherListener {
	/**
	 * this method gets called on objects implementing this interface when a 
	 * sequence object has been downloaded and processed.
	 * @param sequenceObject
	 */
	public void sequenceFetched( SequenceObject sequenceObject );
	
}
