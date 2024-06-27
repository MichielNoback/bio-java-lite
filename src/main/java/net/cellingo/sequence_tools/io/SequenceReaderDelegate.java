/**
 * 
 */
package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.sequences.SequenceCreationException;

/**
 * Delegate sequenceReader objects should implement this interface;
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface SequenceReaderDelegate {
	/**
	 * starts processing the sequences and will report finished sequences back to the listener
	 */
	public void readSequences() throws SequenceCreationException;
	/**
	 * Sets the listener to which to report to when a sequence has been read
	 * @param listener
	 */
	public void setListener( SequenceReaderListener listener );
}
