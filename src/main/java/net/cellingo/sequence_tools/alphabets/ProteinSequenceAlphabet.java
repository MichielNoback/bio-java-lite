/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * The protein alphabet
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class ProteinSequenceAlphabet extends SequenceAlphabet {
	private static final char[] proteinCoreAlphabet = {'A','C','D','E','F','G','H','I','K','L','M','N','P','Q','R','S','T','V','W','Y'};
	private ArrayList<HashSet<Character>> conservedAminoAcidCharacters;
	/**
	 * default constructor
	 */
	public ProteinSequenceAlphabet() {
		super();
		super.setSequenceType(SequenceType.PROTEIN);
		super.setWildCardCharacter('X');
		initialise();
	}

	/**
	 * initialisation procedures for this alphabet
	 */
	private void initialise(){
		//create hashmap of nucleotide objects
		HashMap<Character, AlphabetCharacter> alphabetCharacters = super.getAlphabetCharacterObjects();
		conservedAminoAcidCharacters = new ArrayList<HashSet<Character>>();
		
		/*create core alphabet objects Set*/
		for(char aaCharacter: proteinCoreAlphabet){
			AminoAcid aminoAcid;
			try {
				aminoAcid = new AminoAcid(aaCharacter);
				alphabetCharacters.put(aaCharacter, aminoAcid);
			} catch (IllegalCharacterException e) {
				e.printStackTrace();
			}
		}
		
		/*create groups of conserved amino acids*/
		/*NOT REPRESENTED: pro*/
		HashSet<Character> conservedGroup1 = new HashSet<Character>();
			conservedGroup1.add('V');
			conservedGroup1.add('I');
			conservedGroup1.add('L');
			conservedGroup1.add('M');
		conservedAminoAcidCharacters.add(conservedGroup1);

		//TODO is this a good group?
		HashSet<Character> conservedGroup2 = new HashSet<Character>();
			conservedGroup2.add('S');
			conservedGroup2.add('C');
			conservedGroup2.add('T');
		conservedAminoAcidCharacters.add(conservedGroup2);
		
		HashSet<Character> conservedGroup3 = new HashSet<Character>();
			conservedGroup1.add('H');
			conservedGroup1.add('K');
			conservedGroup1.add('R');
		conservedAminoAcidCharacters.add(conservedGroup3);
		
		HashSet<Character> conservedGroup4 = new HashSet<Character>();
			conservedGroup1.add('F');
			conservedGroup1.add('Y');
			conservedGroup1.add('W');
		conservedAminoAcidCharacters.add(conservedGroup4);

		HashSet<Character> conservedGroup5 = new HashSet<Character>();
			conservedGroup1.add('D');
			conservedGroup1.add('E');
		conservedAminoAcidCharacters.add(conservedGroup5);
		
		HashSet<Character> conservedGroup6 = new HashSet<Character>();
			conservedGroup1.add('N');
			conservedGroup1.add('Q');
		conservedAminoAcidCharacters.add(conservedGroup6);
	
		//TODO is this a good group?
		HashSet<Character> conservedGroup7 = new HashSet<Character>();
			conservedGroup1.add('A');
			conservedGroup1.add('G');
		conservedAminoAcidCharacters.add(conservedGroup7);
	
		
		
		//create hashmap for ambiguity code matching
		//TODO finish
		HashMap<Character, HashSet<Character>> ambiguityCodes = getAmbiguityCodes();
			
		//populate the hashmap
		HashSet<Character> b_matches = new HashSet<Character>();
		b_matches.add('D');
		b_matches.add('N');
		ambiguityCodes.put('B',b_matches);

		HashSet<Character> z_matches = new HashSet<Character>();
		z_matches.add('E');
		z_matches.add('Q');
		ambiguityCodes.put('Z',z_matches);
		
		HashSet<Character> x_matches = new HashSet<Character>();
		x_matches.add('A');
		x_matches.add('C');
		x_matches.add('D');
		x_matches.add('E');
		x_matches.add('F');
		x_matches.add('G');
		x_matches.add('H');
		x_matches.add('I');
		x_matches.add('K');
		x_matches.add('L');
		x_matches.add('M');
		x_matches.add('N');
		x_matches.add('P');
		x_matches.add('Q');
		x_matches.add('R');
		x_matches.add('S');
		x_matches.add('T');
		x_matches.add('V');
		x_matches.add('W');
		x_matches.add('Y');
		ambiguityCodes.put('X',x_matches);
	}
	
	/**
	 * check whether two amino acid characters are in the same conservation group
	 * @param aminoAcid1
	 * @param aminoAcid2
	 * @return isConserved
	 */
	public boolean isConservedPair(char aminoAcid1, char aminoAcid2){
		/*loop the conserved groups*/
		for(HashSet<Character> conservedGroup: conservedAminoAcidCharacters){
			//test existence of character1
			if(conservedGroup.contains(aminoAcid1) && conservedGroup.contains(aminoAcid2)){
				return true;
			}
		}
		return false;
	}
	
}
