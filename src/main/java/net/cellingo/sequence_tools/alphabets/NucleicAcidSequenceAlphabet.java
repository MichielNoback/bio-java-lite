/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

import java.util.HashMap;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public abstract class NucleicAcidSequenceAlphabet extends SequenceAlphabet {
//	private HashMap<Character, Character> complementCodes;

	/**
	 * get the complement of a nucleotide
	 * @param nucleotide
	 * @return complementNucleotide
	 */
	public char getNucleotideComplement(char nucleotide){
		return getComplementCodes().get(nucleotide);
	}
	/**
	 * get the hashmap of complement codes
	 */
	public abstract HashMap<Character, Character> getComplementCodes();
	
}
