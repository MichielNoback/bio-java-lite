package net.cellingo.sequence_tools.alphabets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * class represents the generic type SEquenceAlphabet
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceAlphabet{
	public static final SequenceAlphabet ALPHABET_DNA = new DnaSequenceAlphabet();
	public static final SequenceAlphabet ALPHABET_RNA = new RnaSequenceAlphabet();
	public static final SequenceAlphabet ALPHABET_PROTEIN = new ProteinSequenceAlphabet();
	public static final SequenceAlphabet ALPHABET_STRUCTURE = new StructureSequenceAlphabet();

	private SequenceType sequenceType;
	private char gapCharacter;
	private char wildCardCharacter;
	private HashMap<Character, AlphabetCharacter> alphabetCharacterObjects;
	private HashMap<Character, HashSet<Character>> ambiguousCodes;
	
	public SequenceAlphabet(){
		this.alphabetCharacterObjects = new HashMap<Character, AlphabetCharacter>();
		this.ambiguousCodes = new HashMap<Character, HashSet<Character>>();
		this.gapCharacter = '-';
		this.wildCardCharacter = '.';
	}
	
	/**
	 * set the type of sequence
	 */
	public void setSequenceType(SequenceType type){
		this.sequenceType = type;
	}
	/**
	 * get the sequence type
	 * @return sequenceType
	 */
	public SequenceType getSequenceType(){
		return sequenceType;
	}
	/**
	 * get the characters of this alphabet: not the ambiguous codes 
	 * @return coreAlphabet
	 */
	public ArrayList<Character> getCoreAlphabet() {
		return new ArrayList<Character>(this.alphabetCharacterObjects.keySet());
		//return this.alphabetCharacterObjects.keySet();
	}
	/**
	 * get the character object associated with a letter
	 * @param character
	 * @return characterObject
	 * @throws IllegalCharacterException
	 */
	public AlphabetCharacter getAlphabetCharacterObject(char character) throws IllegalCharacterException {
		if(alphabetCharacterObjects.containsKey(character)){
			return alphabetCharacterObjects.get(character);
		}
		else{
			throw new IllegalCharacterException("ERROR: character not present in this alphabet");
		}
	}
	
	/**
	 * get the ambiguity codes for this alphabet
	 */
	public HashMap<Character, HashSet<Character>> getAmbiguityCodes(){
		return ambiguousCodes;
	}
	/**
	 * check whether the given character matches the given ambiguity code
	 * @throws IllegalCharacterException 
	 */
	public boolean checkAmbiguousCodeMatch(char nucleotide, char ambiguityCode) throws IllegalCharacterException{
		if(ambiguousCodes.containsKey(nucleotide)){
			//System.out.println("character is present");
			return ambiguousCodes.get(ambiguityCode).contains(nucleotide);
		}
		else{
			throw new IllegalCharacterException("ERROR: character not present in this alphabet");
		}
	}
	
	/**
	 * get the gap character for gapped sequences
	 * @return gapCharacter
	 */
	public char getGapCharacter() {
		return gapCharacter;
	}
	/**
	 * set the gap character for gapped sequences; default is '-'
	 * @param gapCharacter
	 */
	public void setGapCharacter(char gapCharacter) {
		this.gapCharacter = gapCharacter;
	}
	/**
	 * get the wildcard character (representing ANY CHAR)
	 * @return wildCardCharacter
	 */
	public char getWildCardCharacter() {
		return wildCardCharacter;
	}
	/**
	 * set the wildcard character for ambiguous positions
	 * @param wildCardCharacter
	 */
	public void setWildCardCharacter(char wildCardCharacter) {
		this.wildCardCharacter = wildCardCharacter;
	}

	/**
	 * get the collection of alphabet character objects
	 */
	public HashMap<Character, AlphabetCharacter> getAlphabetCharacterObjects(){
		return alphabetCharacterObjects;
	}
	/**
	 * check if this character is part of the legal alphabet for this type
	 * @param character
	 * @return existence
	 */
	public boolean isValidAlphabetCharacter(char character){
		if(alphabetCharacterObjects.containsKey(character) || ambiguousCodes.containsKey(character)){
			return true;
		}
		return false;
	}
	
	/**
	 * check if this character is part of the legal alphabet for this type
	 * @param character
	 * @return existence
	 */
	public boolean isCoreCharacter(char character){
		if(alphabetCharacterObjects.containsKey(character)){
			return true;
		}
		return false;
	}
	/**
	 * get the set of matching sequence characters for this ambiguous
	 * (ie NOT core alphabet) character
	 * @param ambiguousCharacter
	 * @return matchingCoreAlphabetCharacters
	 */
	public HashSet<Character> getAmbiguousMatches(char ambiguousCharacter){
		return ambiguousCodes.get(ambiguousCharacter);
	}
	/**
	 * static method to get the correct sequence alphabet for the given type of sequence
	 * @param sequenceType
	 * @return sequenceAlphabet
	 */
	public static SequenceAlphabet getSequenceAlphabet(SequenceType sequenceType){
		if(sequenceType == SequenceType.DNA){
			return new DnaSequenceAlphabet();
		}
		else if(sequenceType == SequenceType.RNA){
			return new RnaSequenceAlphabet();
		}
		else if(sequenceType == SequenceType.PROTEIN){
			return new ProteinSequenceAlphabet();
		}
		else if(sequenceType == SequenceType.STRUCTURE){
			return new StructureSequenceAlphabet();
		}
		return null;
	}
	
}
