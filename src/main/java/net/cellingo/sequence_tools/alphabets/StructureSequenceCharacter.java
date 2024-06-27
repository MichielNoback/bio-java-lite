/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

/**
 * class represents the structure sequence characters
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class StructureSequenceCharacter extends AlphabetCharacter {

	/**
	 * @param character
	 * @throws IllegalCharacterException 
	 */
	public StructureSequenceCharacter(char character) throws IllegalCharacterException {
		super(character);
		initialise();
	}

	
	private void initialise(){
		switch(super.getSingleCharacter()){
		//A (AU), C (CG), G (gap),M (mismatch, W (Wobble GU),			//{'A','C','G','T','M','R','W','S','Y','K','V','H','D','B','N'}
			case 'A':
				super.setFullName("A-U basepair");
				super.setAlternativeCode("2H");
				break;
			case 'C':
				super.setFullName("C-G basepair");
				super.setAlternativeCode("3H");
				break;
			case 'G':
				super.setFullName("gap");
				super.setAlternativeCode("-");
				break;
			case 'M':
				super.setFullName("mismatch");
				super.setAlternativeCode("!");
				break;
			case 'W':
				super.setFullName("G-U Wobble basepair");
				super.setAlternativeCode("1H");
				break;
		}
	}

}
