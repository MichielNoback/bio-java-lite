/**
 * 
 */
package net.cellingo.sequence_tools.seq_manipulation;

import net.cellingo.sequence_tools.sequences.Sequence;

/**
 * An interface that defines a single method, that should be implemented by 
 * classes implementing it. Can be used with SequenceSelectionFilter objects
 * to specify additional, custom sequence filtering steps.
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface SequenceFilter {
	/**
	 * this single method takes as argument a Sequence and returns
	 * whether this sequence passes the filter step
	 * @param sequence
	 * @return sequence passes filter
	 */
	public boolean filter( Sequence sequence );
}
