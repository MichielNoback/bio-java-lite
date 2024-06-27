/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

import java.util.Comparator;

import net.cellingo.sequence_tools.annotation.SequenceElement;
import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class Alignment {
	public static final int ALIGNMENT_TYPE_INTRAMOLECULAR = 0;
	public static final int ALIGNMENT_TYPE_INTERMOLECULAR = 1;
	private static final int BEFORE = -1;
	private static final int AFTER = 1;
	private static final int EQUAL = 0;

	private static int alignmentNumber = 0;
	private int alignmentID;
	private int aligmentType;
	private SequenceType sequenceType;
	private AlignmentProperties alignmentProperties;
	private SequenceElement topParent;
	private SequenceElement bottomParent;
	private StringBuilder middleLine;
	private StringBuilder topStrand;
	private StringBuilder bottomStrand;
	private boolean propertiesCalculated;
	private char gapCharacter;

	/**
	 * 
	 */
	public Alignment() {
		alignmentID = alignmentNumber++;
		initialize();
	}

	private void initialize(){
		alignmentProperties = new AlignmentProperties();
		
		if(topParent != null){
			
			gapCharacter = topParent.getParentSequence().getAlphabet().getGapCharacter();
		}
		else{
			gapCharacter = '-';
		}

	}
	
	/**
	 * @return the alignmentID
	 */
	public int getAlignmentID() {
		return alignmentID;
	}

	/**
	 * @param alignmentID the alignmentID to set
	 */
//	public void setAlignmentID(int alignmentID) {
//		this.alignmentID = alignmentID;
//	}

	/**
	 * set a property with its value for this alignment
	 * @param propertyName
	 * @param propertyValue
	 */
	public void setPropertyValue(AlignmentProperty property, double propertyValue){
		alignmentProperties.setProperty(property, propertyValue);
	}
	/**
	 * set the alignment (SW) score 
	 * @param score
	 */
	public void setAlignmentScore(int score){
		alignmentProperties.setProperty(AlignmentProperty.SW_SCORE, (double)score);
	}
	/**
	 * get the value of a alignment property
	 * @param propertyName
	 * @return property value
	 */
	public double getPropertyValue(AlignmentProperty property){
		if(propertiesCalculated || property == AlignmentProperty.SW_SCORE){
			return alignmentProperties.getPropertyValue(property);
		}
		else{
			calculateProperties();
			return alignmentProperties.getPropertyValue(property);
		}
	}

	
	
	/**
	 * @return the aligmentType
	 */
	public int getAligmentType() {
		return aligmentType;
	}

	/**
	 * @param aligmentType the aligmentType to set
	 */
	public void setAligmentType(int aligmentType) {
		this.aligmentType = aligmentType;
	}

	/**
	 * @return the alignmentProperties
	 */
	public AlignmentProperties getAlignmentProperties() {
		return alignmentProperties;
	}

	/**
	 * @param alignmentProperties the alignmentProperties to set
	 */
//	public void setAlignmentProperties(AlignmentProperties alignmentProperties) {
//		this.alignmentProperties = alignmentProperties;
//	}

	/**
	 * @return the topParent
	 */
	public SequenceElement getTopParent() {
		return topParent;
	}

	/**
	 * @param topParent the topParent to set
	 */
	public void setTopParent(SequenceElement topParent) {
		this.topParent = topParent;
	}

	/**
	 * @return the bottomParent
	 */
	public SequenceElement getBottomParent() {
		return bottomParent;
	}

	/**
	 * @param bottomParent the bottomParent to set
	 */
	public void setBottomParent(SequenceElement bottomParent) {
		this.bottomParent = bottomParent;
	}

	/**
	 * @return the middleLine
	 */
	public StringBuilder getMiddleLine() {
		return middleLine;
	}

	/**
	 * @param middleLine the middleLine to set
	 */
	public void setMiddleLine(StringBuilder middleLine) {
		this.middleLine = middleLine;
	}

	/**
	 * @return the topStrand
	 */
	public StringBuilder getTopStrand() {
		return topStrand;
	}

	/**
	 * @param topStrand the topStrand to set
	 */
	public void setTopStrand(StringBuilder topStrand) {
		this.topStrand = topStrand;
	}

	/**
	 * @return the bottomStrand
	 */
	public StringBuilder getBottomStrand() {
		return bottomStrand;
	}

	/**
	 * @param bottomStrand the bottomStrand to set
	 */
	public void setBottomStrand(StringBuilder bottomStrand) {
		this.bottomStrand = bottomStrand;
	}

	/**
	 * properties:
	 * - swScore - primary data: already present
	 * - relativeScore - done here
	 * - hairpinLength - done here
	 * - loopLength - done here
	 * - alignmentLength - done here
	 * - number of G/C basepairs - done here
	 * - number of A/U basepairs - done here
	 * - number of G/U basepairs - done here
	 * - gap number - done here
	 * - mismatch number - not yet implemented
	 */	
	private void calculateProperties(){
		sequenceType = topParent.getParentSequence().getSequenceType();
		
		/*alignment length*/
		alignmentProperties.setProperty(AlignmentProperty.ALIGNMENT_LENGTH, (double)topStrand.length());
		/*relative score*/
		double relativeScore = alignmentProperties.getPropertyValue(AlignmentProperty.SW_SCORE)/alignmentProperties.getPropertyValue(AlignmentProperty.ALIGNMENT_LENGTH);
		alignmentProperties.setProperty(AlignmentProperty.RELATIVE_SCORE, relativeScore);
		/*g/c basepairs*/
		alignmentProperties.setProperty(AlignmentProperty.GC_PAIR_NUMBER, getGCnumber());
		/*a/u basepairs*/
		alignmentProperties.setProperty(AlignmentProperty.AU_PAIR_NUMBER, getAUnumber());
		/*g/u basepairs*/
		alignmentProperties.setProperty(AlignmentProperty.GU_PAIR_NUMBER, getGUnumber());
		/*gap number*/
		alignmentProperties.setProperty(AlignmentProperty.GAP_NUMBER, getGapNumber());

		if( this instanceof HairpinAlignment) {
			HairpinAlignment hairpin = (HairpinAlignment) this;
			
			/*hairpin length*/
			int start = (getTopParent().getSequenceCoordinates().getStart() + 1);
			int stop = getBottomParent().getSequenceCoordinates().getStart() + 1;
			
			if(hairpin.isOnComplement()){
				alignmentProperties.setProperty(AlignmentProperty.HAIRPIN_LENGTH, (start - stop) );
			}
			else{
				alignmentProperties.setProperty(AlignmentProperty.HAIRPIN_LENGTH, (stop - start) );
			}
			/*loopLength*/
			int loopStart = hairpin.getHairpinLoop().getSequenceCoordinates().getStart();
			int loopStop = hairpin.getHairpinLoop().getSequenceCoordinates().getStart();
			alignmentProperties.setProperty(AlignmentProperty.LOOP_LENGTH, (loopStop-loopStart) );
		}
		
	
		propertiesCalculated = true;
	}

	/**
	 * get the number of gaps in this alignment
	 */
	private int getGapNumber(){
		int gapNumber=0;
		for(int i=0; i<topStrand.length(); i++){
			//char character = topStrand.charAt(i);
			if(topStrand.charAt(i) == gapCharacter){
				gapNumber++;
			}
		}
		return gapNumber;
	}
	/**
	 * get the number of G/C basepairs in this alignment
	 */
	private int getGCnumber(){
		int gcNumber=0;
		for(int i=0; i<topStrand.length(); i++){
			char character = topStrand.charAt(i);
			char revCharacter = bottomStrand.charAt(i);
			if(character == 'G' && revCharacter == 'C'){
				gcNumber++;
			}
			else if(character == 'C' && revCharacter == 'G'){
				gcNumber++;
			}
		}
		return gcNumber;
	}

	/**
	 * get the number of A/U basepairs in this alignment
	 */
	private int getAUnumber(){
		int auNumber=0;
		if(sequenceType == SequenceType.DNA){
			for(int i=0; i<topStrand.length(); i++){
				char character = topStrand.charAt(i);
				char revCharacter = bottomStrand.charAt(i);
				if(character == 'A' && revCharacter == 'T'){
					auNumber++;
				}
				else if(character == 'T' && revCharacter == 'A'){
					auNumber++;
				}
			}
		}
		else if(sequenceType == SequenceType.RNA){
			for(int i=0; i<topStrand.length(); i++){
				char character = topStrand.charAt(i);
				char revCharacter = bottomStrand.charAt(i);
				if(character == 'A' && revCharacter == 'U'){
					auNumber++;
				}
				else if(character == 'U' && revCharacter == 'A'){
					auNumber++;
				}
			}
		}
		return auNumber;
	}
	/**
	 * get the number of G/U basepairs in this alignment
	 */
	private int getGUnumber(){
		int guNumber=0;
		if(sequenceType == SequenceType.DNA){
			for(int i=0; i<topStrand.length(); i++){
				char character = topStrand.charAt(i);
				char revCharacter = bottomStrand.charAt(i);
				if(character == 'G' && revCharacter == 'T'){
					guNumber++;
				}
				else if(character == 'T' && revCharacter == 'G'){
					guNumber++;
				}
			}
		}
		else if(sequenceType == SequenceType.RNA){
			for(int i=0; i<topStrand.length(); i++){
				char character = topStrand.charAt(i);
				char revCharacter = bottomStrand.charAt(i);
				if(character == 'G' && revCharacter == 'U'){
					guNumber++;
				}
				else if(character == 'U' && revCharacter == 'G'){
					guNumber++;
				}
			}
		}
		return guNumber;
	}
	
	
	
	/**
	 * Comparator for ordering according to S/W alignment score
	 */
	public static Comparator<Alignment> ScoreComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.SW_SCORE);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.SW_SCORE);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	/**
	 * Comparator for ordering according to the relative score:
	 * S/W alignment score corrected for stem length (score/stem length) 
	 */
	public static Comparator<Alignment> RelativeScoreComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			double score = alignment.getPropertyValue(AlignmentProperty.RELATIVE_SCORE);
			double otherScore = otherAlignment.getPropertyValue(AlignmentProperty.RELATIVE_SCORE);

			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	/**
	 * Comparator for ordering according to the stem length:
	 * length of alignment forward strand including gaps 
	 */
	public static Comparator<Alignment> StemLengthComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.ALIGNMENT_LENGTH);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.ALIGNMENT_LENGTH);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	/**
	 * Comparator for ordering according to the loop length:
	 * length of alignment forward strand including gaps 
	 */
	public static Comparator<Alignment> LoopLengthComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.LOOP_LENGTH);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.LOOP_LENGTH);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	
	public static Comparator<Alignment> TotalLengthComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.HAIRPIN_LENGTH);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.HAIRPIN_LENGTH);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	
	/**
	 * Comparator for ordering according to the loop length:
	 * length of alignment forward strand including gaps 
	 */
	public static Comparator<Alignment> GapNumberComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.GAP_NUMBER);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.GAP_NUMBER);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	/**
	 * Comparator for ordering according to the G/C number in
	 * alignment 
	 */
	public static Comparator<Alignment> GCnumberComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.GC_PAIR_NUMBER);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.GC_PAIR_NUMBER);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};
	/**
	 * Comparator for ordering according to the A/U number in
	 * alignment 
	 */
	public static Comparator<Alignment> AUnumberComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.AU_PAIR_NUMBER);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.AU_PAIR_NUMBER);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};

	/**
	 * Comparator for ordering according to the G/U number in
	 * alignment 
	 */
	public static Comparator<Alignment> GUnumberComparator = new Comparator<Alignment>() {
		public int compare(Alignment alignment, Alignment otherAlignment) {
			int score = (int)alignment.getPropertyValue(AlignmentProperty.GU_PAIR_NUMBER);
			int otherScore = (int)otherAlignment.getPropertyValue(AlignmentProperty.GU_PAIR_NUMBER);
			if(score < otherScore){
				return BEFORE;
			}
			else if(score > otherScore){
				return AFTER;
			}
			else{
				return EQUAL;
			}
		}
	};

}
