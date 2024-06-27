/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.cellingo.sequence_tools.sequences.Sequence;

/**
 * base Aligner class has to be extended into specific aligner types 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public abstract class Aligner {
	private ArrayList<Alignment> alignmentList;
	private List<Sequence> sequenceList;
	private AlignmentStrategy strategy;
	private AlignmentAlgorithm algorithm;
	private AlignmentOptions options;
	private AlignmentScoringMatrix scoringMatrix;
	private AlignmentCharacterMatrix alignmentCharacterMatrix;

	/**
	 * construct with a strategy type
	 * @param strategy
	 */
	public Aligner( AlignmentStrategy strategy, AlignmentAlgorithm algorithm, AlignmentOptions options ) {
		this.strategy = strategy;
		this.algorithm = algorithm;
		this.options = options;
		this.alignmentList = new ArrayList<Alignment>();
		this.scoringMatrix = options.getAlignmentScoringMatrix();
		this.alignmentCharacterMatrix = options.getAlignmentCharacterMatrix();
	}

	/**
	 * get the list of alignments as textual (String-) objects
	 * @return alignments
	 */
	public ArrayList<Alignment> getAlignmentList(){
		return alignmentList;
	}
	
	/**
	 * get an iterator of the found alignments
	 * @return alignment iterator
	 */
	public Iterator<Alignment> getAlignmentIterator(){
		return alignmentList.iterator();
	}
	
	/**
	 * set the sequences to be analyzed
	 * @param sequenceList
	 */
	public void setSequences( List<Sequence> sequenceList ){
		this.sequenceList = sequenceList;
	}
	
	/**
	 * this method should be overriden
	 */
	public void doAlignment(){
		
	}

	/**
	 * @return the sequenceList
	 */
	public List<Sequence> getSequenceList() {
		return sequenceList;
	}

	/**
	 * @param sequenceList the sequenceList to set
	 */
	public void setSequenceList(ArrayList<Sequence> sequenceList) {
		this.sequenceList = sequenceList;
	}

	/**
	 * @return the strategy
	 */
	public AlignmentStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy the strategy to set
	 */
	public void setStrategy(AlignmentStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * @return the algorithm
	 */
	public AlignmentAlgorithm getAlgorithm() {
		return algorithm;
	}

	/**
	 * @param algorithm the algorithm to set
	 */
	public void setAlgorithm(AlignmentAlgorithm algorithm) {
		this.algorithm = algorithm;
	}

	/**
	 * @return the options
	 */
	public AlignmentOptions getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(AlignmentOptions options) {
		this.options = options;
	}

	/**
	 * @return the scoringMatrix
	 */
	public AlignmentScoringMatrix getScoringMatrix() {
		return scoringMatrix;
	}

	/**
	 * @param scoringMatrix the scoringMatrix to set
	 */
	public void setScoringMatrix(AlignmentScoringMatrix scoringMatrix) {
		this.scoringMatrix = scoringMatrix;
	}

	/**
	 * @return the alignmentCharacterMatrix
	 */
	public AlignmentCharacterMatrix getAlignmentCharacterMatrix() {
		return alignmentCharacterMatrix;
	}

	/**
	 * @param alignmentCharacterMatrix the alignmentCharacterMatrix to set
	 */
	public void setAlignmentCharacterMatrix(
			AlignmentCharacterMatrix alignmentCharacterMatrix) {
		this.alignmentCharacterMatrix = alignmentCharacterMatrix;
	}

}
