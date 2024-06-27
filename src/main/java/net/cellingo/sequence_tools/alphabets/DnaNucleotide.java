package net.cellingo.sequence_tools.alphabets;

import java.io.Serializable;

/**
 * class represents the DNA nucleotides
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class DnaNucleotide extends AlphabetCharacter implements Serializable{
	private static final long serialVersionUID = -3208671259321451124L;

	/**
	 * construct with a single-letter representation
	 * @param nucleotide
	 * @throws IllegalCharacterException 
	 */
	public DnaNucleotide(char nucleotide) throws IllegalCharacterException{
		super(nucleotide);
		initialise();
	}
	
	private void initialise(){
		switch(super.getSingleCharacter()){
			case 'A':
				super.setFullName("deoxyadenosine");
				super.setMolecularWeight(313.2);
				super.setAlternativeCode("dA");
				break;
			case 'C':
				super.setFullName("deoxycytidine");
				super.setMolecularWeight(289.2);
				super.setAlternativeCode("dC");
				break;
			case 'G':
				super.setFullName("deoxyguanosine");
				super.setMolecularWeight(329.2);
				super.setAlternativeCode("dG");
				break;
			case 'T':
				super.setFullName("deoxythymidine");
				super.setMolecularWeight(304.2);
				super.setAlternativeCode("dT");
				break;
		}
	}
}
