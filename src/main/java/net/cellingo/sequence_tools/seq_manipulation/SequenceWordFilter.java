/**
 * 
 */
package net.cellingo.sequence_tools.seq_manipulation;

import net.cellingo.sequence_tools.sequences.Sequence;

/**
 * A simple word filter that returns true if the word is contained within the given sequence
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceWordFilter implements SequenceFilter {

	/*the word to filter against*/
	private String word;

	public SequenceWordFilter( String word ){
		this.word = word.toUpperCase();
	}
	
	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.utils.SequenceFilter#filter(net.cellingo.sequence_tools.sequences.Sequence)
	 */
	public boolean filter(Sequence sequence) {
		if(sequence.getSequence().getSequenceString().contains( word ) ) return true;
		else return false;
	}

}
