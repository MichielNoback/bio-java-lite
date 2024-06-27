/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

import java.util.ArrayList;
import java.util.HashMap;

import net.cellingo.sequence_tools.alphabets.DnaSequenceAlphabet;
import net.cellingo.sequence_tools.alphabets.ProteinSequenceAlphabet;
import net.cellingo.sequence_tools.alphabets.RnaSequenceAlphabet;
import net.cellingo.sequence_tools.alphabets.StructureSequenceAlphabet;
import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * class that represents alignment characters and their retrieving process
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AlignmentCharacterMatrix {
	private SequenceType sequenceType;
//	private boolean alignmentAsStructure;
	private boolean matchingCharacterAsAlignmentCharacter;
	private HashMap<String, Character> alignmentCharactersMatrix;
	private char matchAlignmentCharacter;
	private char gcAlignmentCharacter;
	private char auAlignmentCharacter;
	private char guAlignmentCharacter;
	private char mismatchAlignmentCharacter;
	private char conservedAlignmentCharacter;
	private char gapAlignmentCharacter;
	private AlignmentMatrixType matrixType;

	/**
	 * Constructor takes in an enum SequenceType to build the matrix for, as well as
	 * a boolean for whether DNA alignment is 'regular' alignment or 'textAlignment' alignment 
	 * (if used for other SequenceType, set this to false) and a boolean for the character 
	 * used for matching alignmewnt characters (ie, if AA pair, then midline is 'A' or 
	 * something like '|').    
	 */
	public AlignmentCharacterMatrix(SequenceType sequenceType, AlignmentMatrixType matrixType, boolean matchingCharacterAsAlignmentCharacter) {
		this.sequenceType = sequenceType;
		this.matrixType = matrixType;		
//		this.alignmentAsStructure = dnaAlignmentAsStructure;
		this.matchingCharacterAsAlignmentCharacter = matchingCharacterAsAlignmentCharacter;
		matchingCharacterAsAlignmentCharacter = true;
		alignmentCharactersMatrix = new HashMap<String, Character>();
		matchAlignmentCharacter = ' ';
		gcAlignmentCharacter = '|';
		auAlignmentCharacter = ':';
		guAlignmentCharacter = '.';
		mismatchAlignmentCharacter = '!';
		gapAlignmentCharacter = '!';
		conservedAlignmentCharacter = '+';
		initialise();
	}
	
	/**
	 * object initialisation procedures
	 */
	public void initialise(){
		
		/*build alignment matrix dependent on sequence type and alignment type*/
//		if(sequenceType == SequenceType.DNA && alignmentAsStructure == false){
		if(sequenceType == SequenceType.DNA && (matrixType != AlignmentMatrixType.RNA_STRUCTURE_ALIGNMENT) ){

			DnaSequenceAlphabet sequenceAlphabet = new DnaSequenceAlphabet();
			ArrayList<Character> characterList= sequenceAlphabet.getCoreAlphabet();
			/*loop twice through the set of alphabet core chacaters*/
			for(char nucleotide1: characterList){
				for(char nucleotide2: characterList){
					char[] pair1 = {nucleotide1,nucleotide2};
					char[] pair2 = {nucleotide2,nucleotide1};
					//check for equality
					if(nucleotide1 == nucleotide2){
						if(matchingCharacterAsAlignmentCharacter){
							alignmentCharactersMatrix.put(new String(pair1),nucleotide1);
						}
						else{
							alignmentCharactersMatrix.put(new String(pair1),matchAlignmentCharacter);
						}
					}
					else{//not equal characters
						alignmentCharactersMatrix.put(new String(pair1),mismatchAlignmentCharacter);
						alignmentCharactersMatrix.put(new String(pair2),mismatchAlignmentCharacter);
					}
				}
			}
		}
		
//		else if(sequenceType == SequenceType.RNA && alignmentAsStructure == false){
		else if(sequenceType == SequenceType.RNA && (matrixType != AlignmentMatrixType.RNA_STRUCTURE_ALIGNMENT) ){			
			RnaSequenceAlphabet sequenceAlphabet = new RnaSequenceAlphabet();
			ArrayList<Character> characterList= sequenceAlphabet.getCoreAlphabet();
			/*loop twice through the set of alphabet core chacaters*/
			for(char nucleotide1: characterList){
				for(char nucleotide2: characterList){
					char[] pair1 = {nucleotide1,nucleotide2};
					char[] pair2 = {nucleotide2,nucleotide1};
					//check for equality
					if(nucleotide1 == nucleotide2){
						if(matchingCharacterAsAlignmentCharacter){
							alignmentCharactersMatrix.put(new String(pair1),nucleotide1);
						}
						else{
							alignmentCharactersMatrix.put(new String(pair1),matchAlignmentCharacter);
						}
					}
					else{//not equal characters
						alignmentCharactersMatrix.put(new String(pair1),mismatchAlignmentCharacter);
						alignmentCharactersMatrix.put(new String(pair2),mismatchAlignmentCharacter);
					}
				}
			}
		}
		
//		else if(sequenceType == SequenceType.DNA && alignmentAsStructure == true){
		else if(sequenceType == SequenceType.DNA && (matrixType == AlignmentMatrixType.RNA_STRUCTURE_ALIGNMENT) ){
			alignmentCharactersMatrix.put("AA",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("AC",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("AG",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("AT",auAlignmentCharacter);
			
			alignmentCharactersMatrix.put("CA",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("CC",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("CG",gcAlignmentCharacter);
			alignmentCharactersMatrix.put("CT",mismatchAlignmentCharacter);
			
			alignmentCharactersMatrix.put("GA",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("GC",gcAlignmentCharacter);
			alignmentCharactersMatrix.put("GG",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("GT",guAlignmentCharacter);

			alignmentCharactersMatrix.put("TA",auAlignmentCharacter);
			alignmentCharactersMatrix.put("TC",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("TG",guAlignmentCharacter);
			alignmentCharactersMatrix.put("TT",mismatchAlignmentCharacter);
		}
		
//		else if(sequenceType == SequenceType.RNA && alignmentAsStructure == true){
		else if(sequenceType == SequenceType.RNA && (matrixType == AlignmentMatrixType.RNA_STRUCTURE_ALIGNMENT) ){
			alignmentCharactersMatrix.put("AA",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("AC",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("AG",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("AU",auAlignmentCharacter);
			
			alignmentCharactersMatrix.put("CA",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("CC",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("CG",gcAlignmentCharacter);
			alignmentCharactersMatrix.put("CU",mismatchAlignmentCharacter);
			
			alignmentCharactersMatrix.put("GA",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("GC",gcAlignmentCharacter);
			alignmentCharactersMatrix.put("GG",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("GU",guAlignmentCharacter);
			
			alignmentCharactersMatrix.put("UA",auAlignmentCharacter);
			alignmentCharactersMatrix.put("UC",mismatchAlignmentCharacter);
			alignmentCharactersMatrix.put("UG",guAlignmentCharacter);
			alignmentCharactersMatrix.put("UU",mismatchAlignmentCharacter);
		}
		
		/*build the protein character alignment matrix*/
		else if(sequenceType == SequenceType.PROTEIN){
			ProteinSequenceAlphabet sequenceAlphabet = new ProteinSequenceAlphabet();
			ArrayList<Character> characterList= sequenceAlphabet.getCoreAlphabet();
			/*loop twice through the set of alphabet core chacaters*/
			for(char aminoAcid1: characterList){
				for(char aminoAcid2: characterList){
					char[] pair1 = {aminoAcid1,aminoAcid2};
					char[] pair2 = {aminoAcid2,aminoAcid1};

					//check for equality
					if(aminoAcid1 == aminoAcid2){
						if(matchingCharacterAsAlignmentCharacter){
							alignmentCharactersMatrix.put(new String(pair1),aminoAcid1);
						}
						else{
							alignmentCharactersMatrix.put(new String(pair1),matchAlignmentCharacter);
						}
					}
					//check for conservation
					else if(sequenceAlphabet.isConservedPair(aminoAcid1, aminoAcid2)){
						alignmentCharactersMatrix.put(new String(pair1),conservedAlignmentCharacter);
						alignmentCharactersMatrix.put(new String(pair2),conservedAlignmentCharacter);
					}
					//not equal, not conserved
					else{
						alignmentCharactersMatrix.put(new String(pair1),mismatchAlignmentCharacter);
						alignmentCharactersMatrix.put(new String(pair2),mismatchAlignmentCharacter);
					}
					
				}
			}
		}//end sequenceType == PROTEIN
		
		else if(sequenceType == SequenceType.STRUCTURE){
			//A (AU), C (CG), G (gap),M (mismatch, W (Wobble GU)
			StructureSequenceAlphabet sequenceAlphabet = new StructureSequenceAlphabet();
			ArrayList<Character> characterList= sequenceAlphabet.getCoreAlphabet();
			/*loop twice through the set of alphabet core chacaters*/
			for(char structureChar1: characterList){
				for(char structureChar2: characterList){
					char[] pair1 = {structureChar1,structureChar2};
					char[] pair2 = {structureChar2,structureChar1};

					//check for equality
					if(structureChar1 == structureChar2){
						if(matchingCharacterAsAlignmentCharacter){
							alignmentCharactersMatrix.put(new String(pair1),structureChar1);
						}
						else{
							alignmentCharactersMatrix.put(new String(pair1),matchAlignmentCharacter);
						}
					}
					//check for conservation
					else if(sequenceAlphabet.isConservedPair(structureChar1, structureChar2)){
						alignmentCharactersMatrix.put(new String(pair1),conservedAlignmentCharacter);
						alignmentCharactersMatrix.put(new String(pair2),conservedAlignmentCharacter);
					}
					//not equal, not conserved
					else{
						alignmentCharactersMatrix.put(new String(pair1),mismatchAlignmentCharacter);
						alignmentCharactersMatrix.put(new String(pair2),mismatchAlignmentCharacter);
					}
					
				}
			}
		}//end sequenceType == STRUCTURE
	}//end initialise()
	
	/**
	 * get the alignment character that belongs to the given pair of characters
	 */
	public char getAlignmentCharacter(char character1, char character2){
		char[] searchArray = {character1, character2};
		String searchString = new String(searchArray); 
		return alignmentCharactersMatrix.get(searchString);
	}
	/**
	 * set the G/C basepair alignment character
	 * @param gcAlignmentCharacter
	 */
	public void setGCalignmentCharacter(char gcAlignmentCharacter){
		this.gcAlignmentCharacter = gcAlignmentCharacter;
	}
	/**
	 * get the G/C basepair alignment character
	 * @return gcAlignCharacter
	 */
	public char getGCalignmentCharacter(){
		return gcAlignmentCharacter;
	}
	/**
	 * get the A/U basepair alignment character
	 * @return auAlignmentCharacterv
	 */
	public char getAUalignmentCharacter() {
		return auAlignmentCharacter;
	}
	/**
	 * set the A/U basepair alignment character
	 * @param auAlignmentCharacter
	 */
	public void setAUalignmentCharacter(char auAlignmentCharacter) {
		this.auAlignmentCharacter = auAlignmentCharacter;
	}
	/**
	 * get the conserved amino acid alignment character
	 * @return conservedAminoAcidAlignmentCharacter
	 */
	public char getConservedAminoAcidAlignmentCharacter() {
		return conservedAlignmentCharacter;
	}
	/**
	 * set the conserved amino acid alignment character
	 * @param conservedAminoAcidAlignmentCharacter
	 */
	public void setConservedAminoAcidAlignmentCharacter(
			char conservedAminoAcidAlignmentCharacter) {
		this.conservedAlignmentCharacter = conservedAminoAcidAlignmentCharacter;
	}
	/**
	 * get the gap alignment character
	 * @return gapAlignmentCharacter
	 */
	public char getGapAlignmentCharacter() {
		return gapAlignmentCharacter;
	}
	/**
	 * set the gap alignment character
	 * @param gapAlignmentCharacter
	 */
	public void setGapAlignmentCharacter(char gapAlignmentCharacter) {
		this.gapAlignmentCharacter = gapAlignmentCharacter;
	}
	/**
	 * get the G/U basepair alignment character
	 * @return guAlignmentCharacter
	 */
	public char getGUalignmentCharacter() {
		return guAlignmentCharacter;
	}
	/**
	 * set the G/U basepair alignment character
	 * @param guAlignmentCharacter
	 */
	public void setGUalignmentCharacter(char guAlignmentCharacter) {
		this.guAlignmentCharacter = guAlignmentCharacter;
	}
	/**
	 * get the mismatch alignment character
	 * @return mismatchAlignmentCharacter
	 */
	public char getMismatchAlignmentCharacter() {
		return mismatchAlignmentCharacter;
	}
	/**
	 * set the mismatch alignment character
	 * @param mismatchAlignmentCharacter
	 */
	public void setMismatchAlignmentCharacter(char mismatchAlignmentCharacter) {
		this.mismatchAlignmentCharacter = mismatchAlignmentCharacter;
	}
	/**
	 * get the character used for matching alighment characters
	 * @return matchAlignmentCharacter
	 */
	public char getMatchAlignmentCharacter() {
		return matchAlignmentCharacter;
	}
	/**
	 * set the character used for matching alighment characters
	 * @param matchAlignmentCharacter
	 */
	public void setMatchAlignmentCharacter(char matchAlignmentCharacter) {
		this.matchAlignmentCharacter = matchAlignmentCharacter;
	}
	/**
	 * check whether the nucleic acid alignment is done as textAlignment
	 * @return alignmentAsStructure
	 */
//	public boolean isAlignmentAsStructure() {
//		return alignmentAsStructure;
//	}
	/**
	 * set that the nucleic acid alignment is done as textAlignment alignment
	 * @param alignmentAsStructure
	 */
//	public void setAlignmentAsStructure(boolean alignmentAsStructure) {
//		this.alignmentAsStructure = alignmentAsStructure;
//	}
	
	/**
	 * check whether 
	 * @return matchingCharacterAsAlignmentCharacter
	 */
	public boolean getMatchingCharacterAsAlignmentCharacter() {
		return matchingCharacterAsAlignmentCharacter;
	}
	/**
	 * set whether tyhe alignment character should be the same as the matching characters
	 * @param useMatchingCharacterAsAlignment
	 */
	public void setMatchingCharacterAsAlignmentCharacter(
			boolean useMatchingCharacterAsAlignment) {
		this.matchingCharacterAsAlignmentCharacter = useMatchingCharacterAsAlignment;
	}
}
