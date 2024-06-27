/**
 * 
 */
package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.SequenceObject;

/**
 * Classes that want the SequenceReader to process sequences streaming should implement this interface. 
 * It defines a single method: sequenceRead( SequenceObject sequenceObject ) that will be called when a 
 * single sequence has been read from file. After the sequence has been passed to the listener, the 
 * reference to the sequence object will be lost. To be used with many or very large sequences. 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface SequenceReaderListener {
	/**
	 * method that will be invoked on the listener when a single sequence has been read and processed
	 * @param sequenceObject
	 */
	public void sequenceRead( SequenceObject sequenceObject );
	
	/**
	 * callback method to indicate that processing of sequences has finished
	 */
	public void sequenceReadingFinished();
	
}
