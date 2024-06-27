/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class StructureSequenceAlphabet extends SequenceAlphabet {
	private static final char[] alignmentCoreAlphabet = {'A','C','G','M','W'};
	private ArrayList<HashSet<Character>> conservedStructureCharacters;
	//A (AU), C (CG), G (gap),M (mismatch, W (Wobble GU)

	/**
	 * 
	 */
	public StructureSequenceAlphabet() {
		super();
		super.setSequenceType(SequenceType.STRUCTURE);
		super.setWildCardCharacter('.');
		initialise();
	}

	
	/**
	 * initialisation procedures for this alphabet
	 */
	private void initialise(){
		//create hashmap of nucleotide objects
		HashMap<Character, AlphabetCharacter> alphabetCharacters = super.getAlphabetCharacterObjects();
		for(char alignmentCharacter: alignmentCoreAlphabet){
			try {
				StructureSequenceCharacter alignmentObject = new StructureSequenceCharacter(alignmentCharacter);
				alphabetCharacters.put(alignmentCharacter, alignmentObject);
			} catch (IllegalCharacterException e) {
				e.printStackTrace();
			}
		}

		//create hashmap of nucleotide objects
		conservedStructureCharacters = new ArrayList<HashSet<Character>>();
		
		/*create groups of conserved textAlignment characters: A (AU), C (CG), G (gap),M (mismatch, W (Wobble GU)*/
		HashSet<Character> conservedGroup1 = new HashSet<Character>();
			conservedGroup1.add('A');
			conservedGroup1.add('C');
		conservedStructureCharacters.add(conservedGroup1);

		HashSet<Character> conservedGroup2 = new HashSet<Character>();
			conservedGroup2.add('G');
			conservedGroup2.add('M');
		conservedStructureCharacters.add(conservedGroup2);
		
		
		
		//create hashmap for ambiguity code matching
		HashMap<Character, HashSet<Character>> alignmentAmbiguityCodes = getAmbiguityCodes();
		//A (AU), C (CG), G (gap), M (mismatch, W (Wobble GU),
		
		//populate the hashmap
		
		//all basepairs
		HashSet<Character> b_matches = new HashSet<Character>();
		b_matches.add('A');
		b_matches.add('C');
		b_matches.add('W');
		alignmentAmbiguityCodes.put('B',b_matches);
		
		//regular basepairs
		HashSet<Character> r_matches = new HashSet<Character>();
		r_matches.add('A');
		r_matches.add('C');
		alignmentAmbiguityCodes.put('R',r_matches);
		
		//gap or mismatch
		HashSet<Character> n_matches = new HashSet<Character>();
		n_matches.add('G');
		n_matches.add('M');
		alignmentAmbiguityCodes.put('N',n_matches);
		
		//mismatch or wobble
		HashSet<Character> a_matches = new HashSet<Character>();
		a_matches.add('M');
		a_matches.add('W');
		alignmentAmbiguityCodes.put('A',a_matches);

	}
	
	/**
	 * check whether two amino acid characters are in the same conservation group
	 * @param structureCharacter1
	 * @param structureCharacter2
	 * @return isConserved
	 */
	public boolean isConservedPair(char structureCharacter1, char structureCharacter2){
		/*loop the conserved groups*/
		for(HashSet<Character> conservedGroup: conservedStructureCharacters){
			//test existence of character1
			if(conservedGroup.contains(structureCharacter1) && conservedGroup.contains(structureCharacter2)){
				return true;
			}
		}
		return false;
	}

	
}
