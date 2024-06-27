/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * class encapsulates the options used in finding and processing alignments
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AlignmentOptions {
	private AlignmentCharacterMatrix alignmentCharacterMatrix;
	private AlignmentScoringMatrix alignmentScoringMatrix;
	private SequenceType sequenceType;
	
	private AlignmentProperties alignmentPropertiesMinimumValues;
	private AlignmentProperties alignmentPropertiesMaximumValues;
	
//	private boolean dnaAlignmentAsStructure;
	private boolean matchingCharacterIsAlignmentCharacter;
	private boolean alsoComplementStrand;
	private int startPosition;
	private int stopPosition;
	private int windowLength;
	private int windowStep;
//	private boolean onlyHairpins;
//	private boolean returnTextualResults;
	private int gapOpenPenalty;
	private int gapExtensionPenalty;
	private AlignmentMatrixType matrixType;

	/**
	 * This default constructor sets all the options for alignment analyses to default
	 * values. Any changes to the options should be made using getters and setters.
	 * Actual alignment score cannot be found nor set here; they are implemented in the 
	 * ScoringMatrix classes
	 */
	public AlignmentOptions(SequenceType sequenceType, AlignmentMatrixType matrixType) {
		this.sequenceType = sequenceType;
		this.matrixType = matrixType;
		this.matchingCharacterIsAlignmentCharacter = false;
//		this.stopPosition = sequenceLength;
		this.alignmentScoringMatrix = new AlignmentScoringMatrix(this.sequenceType, matrixType);
		alignmentCharacterMatrix = new AlignmentCharacterMatrix(this.sequenceType, matrixType, matchingCharacterIsAlignmentCharacter);
		windowLength = 250;
		windowStep = 50;
		alsoComplementStrand = true;
		gapOpenPenalty = -6;
		gapExtensionPenalty = -3;
		
		alignmentPropertiesMinimumValues = new AlignmentProperties();
		alignmentPropertiesMinimumValues.getProperties().put(AlignmentProperty.SW_SCORE, 30.0);//"swScore",30.0);
		alignmentPropertiesMinimumValues.getProperties().put(AlignmentProperty.RELATIVE_SCORE, 0.250);//"relativeScore",0.250);
		alignmentPropertiesMinimumValues.getProperties().put(AlignmentProperty.ALIGNMENT_LENGTH, 15.0);//"alignmentLength",15.0);
		
		alignmentPropertiesMaximumValues = new AlignmentProperties();
		alignmentPropertiesMaximumValues.getProperties().put(AlignmentProperty.HAIRPIN_LENGTH, (double)windowLength);//"hairpinLength", (double)windowLength);
		//startAnalysisPosition = 1;
		//stopAnalysisPosition = 0;
	}
	/**
	 * get the window length for sliding window analyses
	 * @return windowLength
	 */
	public int getWindowLength(){
		return windowLength;
	}
	/**
	 * set the window length for sliding window analyses
	 * @param windowLength
	 */
	public void setWindowLength(int windowLength){
		this.windowLength = windowLength;
	}
	/**
	 * get the step size for sliding window analyses
	 * @return windowStep
	 */
	public int getWindowStep(){
		return windowStep;
	}
	/**
	 * set the step size for sliding window analyses
	 * @param windowStep
	 */
	public void setWindowStep(int windowStep){
		this.windowStep = windowStep;
	}
	/**
	 * get the lower cutoff values for the AlignmentProprties
	 * @return alignmentPropertiesMinimumValues
	 */
	public AlignmentProperties getAlignmentPropertiesMinimumValues(){
		return alignmentPropertiesMinimumValues;
	}
	/**
	 * get the higher cutoff values for the AlignmentProprties
	 * @return alignmentPropertiesMaximumValues
	 */
	public AlignmentProperties getAlignmentPropertiesMaximumValues(){
		return alignmentPropertiesMaximumValues;
	}
	
	/**
	 * get the gap opening penalty (first gap in alignment)
	 * @return gapOpenPenalty
	 */
	public int getGapOpenPenalty(){
		return gapOpenPenalty;
	}
	/**
	 * set the gap opening penalty (first consecutive gap in alignment)
	 * @param gapOpenPenalty
	 */
	public void setGapOpenPenalty(int gapOpenPenalty){
		this.gapOpenPenalty = gapOpenPenalty;
	}
	/**
	 * get the gap extension penalty (second and later consecutive gaps in alignment)
	 * @return gapOpenPenalty
	 */
	public int getGapExtensionPenalty(){
		return gapExtensionPenalty;
	}
	/**
	 * set the gap extension penalty (second and later consecutive gaps in alignment)
	 * @param gapExtensionPenalty
	 */
	public void setGapExtensionPenalty(int gapExtensionPenalty){
		this.gapExtensionPenalty = gapExtensionPenalty;
	}
	/**
	 * get the start of analysis
	 * @return startPosition
	 */
	public int getStartPosition() {
		return startPosition;
	}
	/**
	 * set the start of analysis
	 * @param startPosition
	 */
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	/**
	 * get the stop of analysis
	 * @return stopPosition
	 */
	public int getStopPosition() {
		return stopPosition;
	}
	/**
	 * set the stop of analysis
	 * @param stopPosition
	 */
	public void setStopPosition(int stopPosition) {
		this.stopPosition = stopPosition;
	}
	/**
	 * get whether the alignment character used for equal (matching) sequence characters
	 * is a standard character or the same as the matching characters
	 * @return matchingCharacterAsAlignmentCharacter
	 */
	public boolean isMatchingCharacterIsAlignmentCharacter() {
		return matchingCharacterIsAlignmentCharacter;
	}
	/**
	 * set whether the alignment character used for equal (matching) sequence characters
	 * is a standard character or the same as the matching characters
	 * @param matchingCharacterIsAlignmentCharacter
	 */
	public void setMatchingCharacterIsAlignmentCharacter(
			boolean matchingCharacterIsAlignmentCharacter) {
		this.matchingCharacterIsAlignmentCharacter = matchingCharacterIsAlignmentCharacter;
		alignmentCharacterMatrix = new AlignmentCharacterMatrix(this.sequenceType, matrixType, matchingCharacterIsAlignmentCharacter);
	}
	/**
	 * getter for the alignment charatcter matrix used in the current analysis
	 * @return alignmentCharacterMatrix
	 */
	public AlignmentCharacterMatrix getAlignmentCharacterMatrix() {
		return alignmentCharacterMatrix;
	}
	/**
	 * get the alignment scoring matrix
	 * @return alignmentScoringMatrix
	 */
	public AlignmentScoringMatrix getAlignmentScoringMatrix() {
		return alignmentScoringMatrix;
	}
	/**
	 * check whether the complement strand should be analysed as well
	 * @return alsoComplementStrand
	 */
	public boolean alsoComplementStrand(){
		return alsoComplementStrand;
	}
	/**
	 * set the option to also complement strand analysis
	 * @param alsoComplement
	 */
	public void setAlsoComplement(boolean alsoComplement){
		this.alsoComplementStrand = alsoComplement;
	}
	/**
	 * @return the matrixType
	 */
	public AlignmentMatrixType getMatrixType() {
		return matrixType;
	}
	/**
	 * @param matrixType the matrixType to set
	 */
	public void setMatrixType(AlignmentMatrixType matrixType) {
		this.matrixType = matrixType;
	}
}
