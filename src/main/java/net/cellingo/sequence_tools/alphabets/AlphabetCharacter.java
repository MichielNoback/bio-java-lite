package net.cellingo.sequence_tools.alphabets;
/**
 * AlphabetCharacter represents the super type for any character 
 * that is a member of a biological sequence alphabet
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AlphabetCharacter {
	private char singleCharacter;
	private String fullName;
	private double molecularWeight;
	private String alternativeCode;

	/**
	 * constructor takes as argument the single-letter character representation
	 * @param character
	 */
	public AlphabetCharacter(char character) throws IllegalCharacterException{
		this.singleCharacter = character;
	}

	/**
	 * get the single-letter character representation
	 * @return character
	 */
	public char getSingleCharacter() {
		return singleCharacter;
	}
	/**
	 * get the full name of this character
	 * @return fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * set the full name of this character
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * get the molecular weight of this character
	 * @return molecular weight
	 */
	public double getMolecularWeight() {
		return molecularWeight;
	}
	/**
	 * set the molecular weight of this character
	 * @param molecularWeight
	 */
	public void setMolecularWeight(double molecularWeight) {
		this.molecularWeight = molecularWeight;
	}
	/**
	 * get the alternative code for this character
	 * @return alternative code
	 */
	public String getAlternativeCode() {
		return alternativeCode;
	}
	/**
	 * set the alternative code for this character
	 * @param alternativeCode
	 */
	public void setAlternativeCode(String alternativeCode) {
		this.alternativeCode = alternativeCode;
	}

	
	
}
