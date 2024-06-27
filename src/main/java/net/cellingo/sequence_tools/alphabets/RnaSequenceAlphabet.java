/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

import java.util.HashMap;
import java.util.HashSet;

import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * class represents the DNA sequence alphabet
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class RnaSequenceAlphabet extends NucleicAcidSequenceAlphabet {
	private static final char[] RNA_CORE_ALPHABET = {'A','C','G','U'};
	private HashMap<Character, Character> rnaComplementCodes = new HashMap<>();
	/**
	 * default construtor
	 */
	public RnaSequenceAlphabet() {
		super();
		super.setSequenceType(SequenceType.RNA);
		super.setWildCardCharacter('N');
		initialise();
	}
	
	/**
	 * initialisation procedures for this alphabet
	 */
	private void initialise(){
		//create hashmap of nucleotide objects
		HashMap<Character, AlphabetCharacter> alphabetCharacters = super.getAlphabetCharacterObjects();
		for(char nucleotideCharacter: RNA_CORE_ALPHABET){
			try {
				RnaNucleotide nucleotide = new RnaNucleotide(nucleotideCharacter);
				alphabetCharacters.put(nucleotideCharacter, nucleotide);
			} catch (IllegalCharacterException e) {
				e.printStackTrace();
			}
		}
		//create hashmap of complement codes
		rnaComplementCodes.put('A','U');
		rnaComplementCodes.put('C','G');
		rnaComplementCodes.put('G','C');
		rnaComplementCodes.put('U','A');
		rnaComplementCodes.put('M','K');
		rnaComplementCodes.put('R','Y');
		rnaComplementCodes.put('W','W');
		rnaComplementCodes.put('S','S');
		rnaComplementCodes.put('Y','R');
		rnaComplementCodes.put('K','M');
		rnaComplementCodes.put('V','B');
		rnaComplementCodes.put('H','D');
		rnaComplementCodes.put('D','H');
		rnaComplementCodes.put('B','V');
		rnaComplementCodes.put('N','N');

		//create hashmap for ambiguity code matching
		HashMap<Character, HashSet<Character>> rnaAmbiguityCodes = getAmbiguityCodes();

		//populate the hashmap
		//populate the hashmap
		HashSet<Character> a_matches = new HashSet<Character>();
		a_matches.add('A');
		rnaAmbiguityCodes.put('A',a_matches);

		HashSet<Character> c_matches = new HashSet<Character>();
		c_matches.add('C');
		rnaAmbiguityCodes.put('C',c_matches);

		HashSet<Character> g_matches = new HashSet<Character>();
		g_matches.add('G');
		rnaAmbiguityCodes.put('G',g_matches);

		HashSet<Character> u_matches = new HashSet<Character>();
		u_matches.add('U');
		rnaAmbiguityCodes.put('U',u_matches);

		HashSet<Character> m_matches = new HashSet<Character>();
		m_matches.add('A');
		m_matches.add('C');
		rnaAmbiguityCodes.put('M',m_matches);

		HashSet<Character> r_matches = new HashSet<Character>();
		r_matches.add('A');
		r_matches.add('G');
		rnaAmbiguityCodes.put('R',r_matches);

		HashSet<Character> w_matches = new HashSet<Character>();
		w_matches.add('A');
		w_matches.add('U');
		rnaAmbiguityCodes.put('W',w_matches);

		HashSet<Character> s_matches = new HashSet<Character>();
		s_matches.add('C');
		s_matches.add('G');
		rnaAmbiguityCodes.put('S',s_matches);

		HashSet<Character> y_matches = new HashSet<Character>();
		y_matches.add('C');
		y_matches.add('U');
		rnaAmbiguityCodes.put('Y',y_matches);

		HashSet<Character> k_matches = new HashSet<Character>();
		k_matches.add('G');
		k_matches.add('U');
		rnaAmbiguityCodes.put('K',k_matches);

		HashSet<Character> v_matches = new HashSet<Character>();
		v_matches.add('A');
		v_matches.add('C');
		v_matches.add('G');
		rnaAmbiguityCodes.put('V',v_matches);

		HashSet<Character> h_matches = new HashSet<Character>();
		h_matches.add('A');
		h_matches.add('C');
		h_matches.add('U');
		rnaAmbiguityCodes.put('H',h_matches);
		
		HashSet<Character> d_matches = new HashSet<Character>();
		d_matches.add('A');
		d_matches.add('G');
		d_matches.add('U');
		rnaAmbiguityCodes.put('D',d_matches);
		
		HashSet<Character> b_matches = new HashSet<Character>();
		b_matches.add('C');
		b_matches.add('G');
		b_matches.add('U');
		rnaAmbiguityCodes.put('B',b_matches);

		HashSet<Character> n_matches = new HashSet<Character>();
		n_matches.add('A');
		n_matches.add('C');
		n_matches.add('G');
		n_matches.add('U');
		rnaAmbiguityCodes.put('N',n_matches);
	}

	@Override
	public HashMap<Character, Character> getComplementCodes() {
		return this.rnaComplementCodes;
	}
}
