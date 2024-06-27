package net.cellingo.sequence_tools.alphabets;

import java.io.Serializable;

/**
 * class represents the RNA nucleotides
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class RnaNucleotide extends AlphabetCharacter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6245465397817694703L;

	/**
	 * construct with a single-letter representation
	 * @param nucleotide
	 * @throws IllegalCharacterException 
	 */
	public RnaNucleotide(char nucleotide) throws IllegalCharacterException{
		super(nucleotide);
		initialise();
	}

	private void initialise(){
		switch(super.getSingleCharacter()){
			case 'A':
				super.setFullName("adenosine");
				super.setMolecularWeight(329.2);
				super.setAlternativeCode("A");
				break;
			case 'C':
				super.setFullName("cytidine");
				super.setMolecularWeight(305.2);
				super.setAlternativeCode("C");
				break;
			case 'G':
				super.setFullName("guanosine");
				super.setMolecularWeight(345.2);
				super.setAlternativeCode("G");
				break;
			case 'U':
				super.setFullName("uridine");
				super.setMolecularWeight(306.2);
				super.setAlternativeCode("U");
				break;
		}
	}
}
