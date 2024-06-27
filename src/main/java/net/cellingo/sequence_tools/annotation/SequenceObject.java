package net.cellingo.sequence_tools.annotation;

import net.cellingo.sequence_tools.sequences.Sequence;

/**
 * All sequence objects, be it Sequence )sub'types or AnnotatedSequence types,
 * should implement these methods. 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface SequenceObject {
	/**
	 * returns the type of sequence object
	 * @return sequenceObjectType
	 */
	public SequenceObjectType getSequenceObjectType();
	/**
	 * returns the sequence name
	 * @return sequenceName
	 */
	public String getSequenceName();
	/**
	 * returns the accession number
	 * @return accessionNumber
	 */
	public String getAccessionNumber();
	/**
	 * returns the sequence length
	 * @return sequenceLength
	 */
	public int getSequenceLength();
	/**
	 * returns the wrapped sequence
	 * @return sequence
	 */
	public Sequence getSequence();
	/**
	 * returns the programe sequence ID
	 * @return programSequenceID
	 */
	public int getProgramSequenceID();
	/**
	 * returns the annotation object associated with this sequence 
	 * @return annotations
	 */
	public Attributes getAttributes();
}
