package net.cellingo.sequence_tools.sequences;

import java.util.HashMap;

import net.cellingo.sequence_tools.alphabets.NucleicAcidSequenceAlphabet;

/**
 * An abstract class in the chain of inheritance to define
 * Nucleic-acid-specific methods such as complement and
 * reverse complement
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public abstract class NucleicAcidSequence extends Sequence {
	private static HashMap<Character, Character> complements = new HashMap<Character, Character>();

	static{
		complements.put('A', 'T');
		complements.put('C', 'G');
		complements.put('G', 'C');
		complements.put('T', 'A');
		complements.put('a', 't');
		complements.put('c', 'g');
		complements.put('g', 'c');
		complements.put('t', 'a');
		complements.put('-', '-');

	}

	public NucleicAcidSequence(String sequence) {
		super(sequence);
	}

	/**
	 * Complementing this sequence and returns a complemented copy
	 */
    public NucleicAcidSequence complement(){
		NucleicAcidSequenceAlphabet alphabet = (NucleicAcidSequenceAlphabet)getAlphabet();
		String complString = complementString(getSequenceString(), alphabet);
		return getNewInstance(complString);
	}

    /**
     * Template method element
     * @param sequenceString
     * @return
     */
	protected abstract NucleicAcidSequence getNewInstance(String sequenceString);

    /**
     * creates a complement sequence for the given alphabet
     * @param stringToComplement
     * @param alphabet
     * @return
     */
	public static String complementString(String stringToComplement, NucleicAcidSequenceAlphabet alphabet) {
        int seqLength = stringToComplement.length();
        char nucleotide;
        char complementNucleotide;
        char[] complements = new char[seqLength];
        for (int index = 0; index < seqLength; index++){
            nucleotide = stringToComplement.charAt(index);
            complementNucleotide =  alphabet.getNucleotideComplement(nucleotide);
            complements[index] = complementNucleotide;
        }
        return new String(complements);
    }

    /**
     * creates a reverse complement of the given string, with the given alphabet
     * @param stringToComplement
     * @return
     */
    public static String reverseString(String stringToComplement) {
        StringBuilder sb = new StringBuilder(stringToComplement);
        return sb.reverse().toString();
    }

	/**
	 * reverse complementing the sequence and returns a modified copy
	 */
	public NucleicAcidSequence reverseComplement(){
	    String rev = reverseString(getSequenceString());
        NucleicAcidSequenceAlphabet alphabet = (NucleicAcidSequenceAlphabet)getAlphabet();
        String complString = complementString(rev, alphabet);
        return getNewInstance(complString);
	}


	/**
	 * convenience method to do simple complementing of a stringbuilder
	 * Non-core nucleic acid alphabet will become N
	 * @param seq
	 * @return complement
	 */
//	public static StringBuilder complement( StringBuilder seq ){
//		StringBuilder compl = new StringBuilder();
//		for( int i=0; i<seq.length(); i++){
//			char n = seq.charAt(i);
//			if( complements.containsKey(n)) compl.append(complements.get(n));
//			else compl.append('N');
//		}
//		return compl;
//	}

	/**
	 * convenience method to do simple reverse complementing of a stringbuilder
	 * Non-core nucleic acid alphabet will become N
	 * @param seq
	 * @return reverseComplement
	 */
//	public static StringBuilder reverseComplement( StringBuilder seq ){
//		StringBuilder compl = new StringBuilder();
//		for( int i=seq.length()-1; i>=0; i--){
//			char n = seq.charAt(i);
//			if( complements.containsKey(n)) compl.append(complements.get(n));
//			else compl.append('N');
//		}
//		return compl;
//	}

	/**
	 * convenience method to do simple complementing of a string
	 * Non-core nucleic acid alphabet will become N
	 * @param seq
	 * @return complement
	 */
//	public static String complement( String seq ){
//		StringBuilder compl = new StringBuilder();
//		for( int i=0; i<seq.length(); i++){
//			char n = seq.charAt(i);
//			if( complements.containsKey(n)) compl.append(complements.get(n));
//			else compl.append('N');
//		}
//		return compl.toString();
//	}

	/**
	 * convenience method to do simple reverse complementing of a string
	 * Non-core nucleic acid alphabet will become N
	 * @param seq
	 * @return reverseComplement
	 */
//	public static String reverseComplement( String seq ){
//		StringBuilder compl = new StringBuilder();
//		for( int i=seq.length()-1; i>=0; i--){
//			char n = seq.charAt(i);
//			if( complements.containsKey(n)) compl.append(complements.get(n));
//			else compl.append('N');
//		}
//		return compl.toString();
//	}

}
