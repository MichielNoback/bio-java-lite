package net.cellingo.sequence_tools.alignment;

import java.util.ArrayList;
import java.util.HashMap;

import net.cellingo.sequence_tools.alphabets.DnaSequenceAlphabet;
import net.cellingo.sequence_tools.alphabets.RnaSequenceAlphabet;
import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;
import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * class that encapsulates the scoring of tow aligned characters
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AlignmentScoringMatrix {
	private AlignmentMatrixType matrixType;
	private SequenceAlphabet sequenceAlphabet;
	private HashMap<String, Integer> scoringMatrix;
	// = new HashMap<String, Integer>();
	private int simpleMatch;
	private int mismatchPenalty;
	private int gcBasepairScore;
	private int atBasepairScore;
	private int guBasepairScore;
	
	/**
	 * constructor takes in an enum specifying the matrix type
	 */
	public AlignmentScoringMatrix(SequenceType sequenceType, AlignmentMatrixType matrixType){
		this.matrixType = matrixType;
		this.sequenceAlphabet = SequenceAlphabet.getSequenceAlphabet(sequenceType);
		gcBasepairScore = 3;
		atBasepairScore = 2;
		guBasepairScore = 1;
		simpleMatch = 3;
		mismatchPenalty = -3;
		createScoringMatrix();
	}
	
	/**
	 * methods for creating the scoringsmatrix depending on matrix and sequence type
	 */
	public void createScoringMatrix(){
		
		scoringMatrix = new HashMap<String, Integer>();
		
		/*simple alignment*/
		if(matrixType == AlignmentMatrixType.SIMPLE_MATCH_ALIGNMENT){
			ArrayList<Character> characterList= sequenceAlphabet.getCoreAlphabet();
			/*loop twice through the set of alphabet core chacaters*/
			for(char nucleotide1: characterList){
				for(char nucleotide2: characterList){
					char[] pair1 = {nucleotide1,nucleotide2};
					char[] pair2 = {nucleotide2,nucleotide1};
					//check for equality
					if(nucleotide1 == nucleotide2){
						scoringMatrix.put(new String(pair1),simpleMatch);
					}
					else{//not equal characters
						scoringMatrix.put(new String(pair2),mismatchPenalty);
					}
				}
			}
		}
		
		/*textAlignment alignment*/
		else if(matrixType == AlignmentMatrixType.RNA_STRUCTURE_ALIGNMENT){
			if(sequenceAlphabet instanceof DnaSequenceAlphabet){
				scoringMatrix.put("AA",mismatchPenalty);
				scoringMatrix.put("AC",mismatchPenalty);
				scoringMatrix.put("AG",mismatchPenalty);
				scoringMatrix.put("AT",atBasepairScore);
				
				scoringMatrix.put("CA",mismatchPenalty);
				scoringMatrix.put("CC",mismatchPenalty);
				scoringMatrix.put("CG",gcBasepairScore);
				scoringMatrix.put("CT",mismatchPenalty);
				
				scoringMatrix.put("GA",mismatchPenalty);
				scoringMatrix.put("GC",gcBasepairScore);
				scoringMatrix.put("GG",mismatchPenalty);
				scoringMatrix.put("GT",guBasepairScore);

				scoringMatrix.put("TA",atBasepairScore);
				scoringMatrix.put("TC",mismatchPenalty);
				scoringMatrix.put("TG",guBasepairScore);
				scoringMatrix.put("TT",mismatchPenalty);
			}
			else if(sequenceAlphabet instanceof RnaSequenceAlphabet){
				scoringMatrix.put("AA",mismatchPenalty);
				scoringMatrix.put("AC",mismatchPenalty);
				scoringMatrix.put("AG",mismatchPenalty);
				scoringMatrix.put("AU",atBasepairScore);
				
				scoringMatrix.put("CA",mismatchPenalty);
				scoringMatrix.put("CC",mismatchPenalty);
				scoringMatrix.put("CG",gcBasepairScore);
				scoringMatrix.put("CU",mismatchPenalty);
				
				scoringMatrix.put("GA",mismatchPenalty);
				scoringMatrix.put("GC",gcBasepairScore);
				scoringMatrix.put("GG",mismatchPenalty);
				scoringMatrix.put("GU",guBasepairScore);
				
				scoringMatrix.put("UA",atBasepairScore);
				scoringMatrix.put("UC",mismatchPenalty);
				scoringMatrix.put("UG",guBasepairScore);
				scoringMatrix.put("UU",mismatchPenalty);
			}
		}//end if(matrixType == AlignmentMatrixType.STRUCTURE_ALIGNMENT)
		
		else if(matrixType == AlignmentMatrixType.DNA_STRUCTURE_ALIGNMENT){
			if(sequenceAlphabet instanceof DnaSequenceAlphabet){
				scoringMatrix.put("AA",mismatchPenalty);
				scoringMatrix.put("AC",mismatchPenalty);
				scoringMatrix.put("AG",mismatchPenalty);
				scoringMatrix.put("AT",atBasepairScore);
				
				scoringMatrix.put("CA",mismatchPenalty);
				scoringMatrix.put("CC",mismatchPenalty);
				scoringMatrix.put("CG",gcBasepairScore);
				scoringMatrix.put("CT",mismatchPenalty);
				
				scoringMatrix.put("GA",mismatchPenalty);
				scoringMatrix.put("GC",gcBasepairScore);
				scoringMatrix.put("GG",mismatchPenalty);
				scoringMatrix.put("GT",mismatchPenalty);

				scoringMatrix.put("TA",atBasepairScore);
				scoringMatrix.put("TC",mismatchPenalty);
				scoringMatrix.put("TG",mismatchPenalty);
				scoringMatrix.put("TT",mismatchPenalty);
			}
			else if(sequenceAlphabet instanceof RnaSequenceAlphabet){
				scoringMatrix.put("AA",mismatchPenalty);
				scoringMatrix.put("AC",mismatchPenalty);
				scoringMatrix.put("AG",mismatchPenalty);
				scoringMatrix.put("AU",atBasepairScore);
				
				scoringMatrix.put("CA",mismatchPenalty);
				scoringMatrix.put("CC",mismatchPenalty);
				scoringMatrix.put("CG",gcBasepairScore);
				scoringMatrix.put("CU",mismatchPenalty);
				
				scoringMatrix.put("GA",mismatchPenalty);
				scoringMatrix.put("GC",gcBasepairScore);
				scoringMatrix.put("GG",mismatchPenalty);
				scoringMatrix.put("GU",mismatchPenalty);
				
				scoringMatrix.put("UA",atBasepairScore);
				scoringMatrix.put("UC",mismatchPenalty);
				scoringMatrix.put("UG",mismatchPenalty);
				scoringMatrix.put("UU",mismatchPenalty);
			}
		}//end if(matrixType == AlignmentMatrixType.STRUCTURE_ALIGNMENT)

		
/*		else if(matrixType == AlignmentMatrixType.PROTEIN_BLOSUM62){
			
		}//end if matrixType == AlignmentMatrixType.PROTEIN_BLOSUM62
*/		
		//TODO protein alignment matrices not implemented yet
		
	}//end createScoringMatrix
	
	
	/**
	 * returns the slignment score for two aligning characters
	 * @return score
	 */
	public int getAlignmentScore(char character1, char character2){
		char[] characterArray = {character1,character2};
		String alignment = new String(characterArray);
		return scoringMatrix.get(alignment);
	}
	/**
	 * get the alignment score between two sequence characters provided as String
	 * @param twoCharacterString
	 * @return score
	 */
	public int getAlignmentScore(String twoCharacterString){
		return scoringMatrix.get(twoCharacterString);
	}
	/**
	 * get the matrix type
	 * @return matrixType
	 */
	public AlignmentMatrixType getMatrixType() {
		return matrixType;
	}
	/**
	 * set the matrix type
	 * @param matrixType
	 */
	public void setMatrixType(AlignmentMatrixType matrixType) {
		this.matrixType = matrixType;
		createScoringMatrix();
	}

	public int getMismatchPenalty() {
		return mismatchPenalty;
	}

	public void setMismatch(int mismatch) {
		this.mismatchPenalty = mismatch;
		createScoringMatrix();
	}

	public int getSimpleMatch() {
		return simpleMatch;
	}

	public void setSimpleMatch(int simpleMatch) {
		this.simpleMatch = simpleMatch;
		createScoringMatrix();
	}

	public int getAtBasepairScore() {
		return atBasepairScore;
	}

	public void setAtBasepairScore(int atBasepairScore) {
		this.atBasepairScore = atBasepairScore;
		createScoringMatrix();
	}

	public int getGcBasepairScore() {
		return gcBasepairScore;
	}

	public void setGcBasepairScore(int gcBasepairScore) {
		this.gcBasepairScore = gcBasepairScore;
		createScoringMatrix();
	}

	public int getGuBasepairScore() {
		return guBasepairScore;
	}

	public void setGuBasepairScore(int guBasepairScore) {
		this.guBasepairScore = guBasepairScore;
		createScoringMatrix();
	}
}
