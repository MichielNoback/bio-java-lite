/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * class represents the DNA sequence alphabet
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class DnaSequenceAlphabet extends NucleicAcidSequenceAlphabet {
	private static final char[] DNA_CORE_ALPHABET = {'A','C','G','T'};
//	private static final char[] DNA_AMBIGUOUS_ALPHABET = {'A','B','C','D','H','G','K','M','N','R','S','T','V','W','Y'};
	private static final HashMap<Character, Character[]> iupacAmbiguities = new HashMap<>();
	private HashMap<Character, Character> dnaComplementCodes = new HashMap<>();

        static{
            iupacAmbiguities.put('A', new Character[]{'A'});
            iupacAmbiguities.put('C', new Character[]{'C'});
            iupacAmbiguities.put('G', new Character[]{'G'});
            iupacAmbiguities.put('T', new Character[]{'T'});
            iupacAmbiguities.put('Y', new Character[]{'C', 'T'});
            iupacAmbiguities.put('R', new Character[]{'A', 'G'});
            iupacAmbiguities.put('W', new Character[]{'A', 'T'});
            iupacAmbiguities.put('S', new Character[]{'G', 'C'});
            iupacAmbiguities.put('K', new Character[]{'T', 'G'});
            iupacAmbiguities.put('M', new Character[]{'C', 'A'});
            
            iupacAmbiguities.put('D', new Character[]{'A', 'G', 'T'});
            iupacAmbiguities.put('V', new Character[]{'A', 'C', 'G'});
            iupacAmbiguities.put('H', new Character[]{'A', 'C', 'T'});
            iupacAmbiguities.put('B', new Character[]{'C', 'G', 'T'});
            iupacAmbiguities.put('N', new Character[]{'A', 'C', 'G', 'T'});
            
        }
        
        /**
         * returns list of core DNA bases macthing the ambiguous base
         * @param iupacDNAbase ambiguous base (also core dna is valid - will return itself)
         * @return list of matching core bases
         */
        public static Character[] getAmbiguousBases(Character iupacDNAbase){
            if(iupacAmbiguities.containsKey(iupacDNAbase)) return iupacAmbiguities.get(iupacDNAbase);
            else throw new IllegalArgumentException("Base " + iupacDNAbase + " is not a legal DNA code");
        }
        
	/**
	 * default constructor 
	 */
	public DnaSequenceAlphabet(){
		super();
		super.setSequenceType(SequenceType.DNA);
		super.setWildCardCharacter('N');
		initialise();
	}

	@Override
	public HashMap<Character, Character> getComplementCodes() {
		return this.dnaComplementCodes;
	}


	/**
	 * initialization procedures for this alphabet
	 */
	private void initialise(){
		//create hashmap of nucleotide objects
		HashMap<Character, AlphabetCharacter> alphabetCharacters = super.getAlphabetCharacterObjects();
		for(char nucleotideCharacter: DNA_CORE_ALPHABET){
			try {
				DnaNucleotide nucleotide = new DnaNucleotide(nucleotideCharacter);
				alphabetCharacters.put(nucleotideCharacter, nucleotide);
			} catch (IllegalCharacterException e) {
				e.printStackTrace();
			}
		}
		
		//create hashmap of complement codes
		//HashMap<Character, Character> dnaComplementCodes = super.getComplementCodes();
		dnaComplementCodes.put('A','T');
		dnaComplementCodes.put('C','G');
		dnaComplementCodes.put('G','C');
		dnaComplementCodes.put('T','A');
		dnaComplementCodes.put('M','K');
		dnaComplementCodes.put('R','Y');
		dnaComplementCodes.put('W','W');
		dnaComplementCodes.put('S','S');
		dnaComplementCodes.put('Y','R');
		dnaComplementCodes.put('K','M');
		dnaComplementCodes.put('V','B');
		dnaComplementCodes.put('H','D');
		dnaComplementCodes.put('D','H');
		dnaComplementCodes.put('B','V');
		dnaComplementCodes.put('N','N');

		//create hashmap for ambiguity code matching
		HashMap<Character, HashSet<Character>> dnaAmbiguityCodes = super.getAmbiguityCodes();

		//populate the hashmap
		HashSet<Character> a_matches = new HashSet<Character>();
		a_matches.add('A');
		dnaAmbiguityCodes.put('A',a_matches);

		HashSet<Character> c_matches = new HashSet<Character>();
		c_matches.add('C');
		dnaAmbiguityCodes.put('C',c_matches);

		HashSet<Character> g_matches = new HashSet<Character>();
		g_matches.add('G');
		dnaAmbiguityCodes.put('G',g_matches);

		HashSet<Character> t_matches = new HashSet<Character>();
		t_matches.add('T');
		dnaAmbiguityCodes.put('T',t_matches);

		HashSet<Character> m_matches = new HashSet<Character>();
		m_matches.add('A');
		m_matches.add('C');
		dnaAmbiguityCodes.put('M',m_matches);

		HashSet<Character> r_matches = new HashSet<Character>();
		r_matches.add('A');
		r_matches.add('G');
		dnaAmbiguityCodes.put('R',r_matches);

		HashSet<Character> w_matches = new HashSet<Character>();
		w_matches.add('A');
		w_matches.add('T');
		dnaAmbiguityCodes.put('W',w_matches);

		HashSet<Character> s_matches = new HashSet<Character>();
		s_matches.add('C');
		s_matches.add('G');
		dnaAmbiguityCodes.put('S',s_matches);

		HashSet<Character> y_matches = new HashSet<Character>();
		y_matches.add('C');
		y_matches.add('T');
		dnaAmbiguityCodes.put('Y',y_matches);

		HashSet<Character> k_matches = new HashSet<Character>();
		k_matches.add('G');
		k_matches.add('T');
		dnaAmbiguityCodes.put('K',k_matches);

		HashSet<Character> v_matches = new HashSet<Character>();
		v_matches.add('A');
		v_matches.add('C');
		v_matches.add('G');
		dnaAmbiguityCodes.put('V',v_matches);

		HashSet<Character> h_matches = new HashSet<Character>();
		h_matches.add('A');
		h_matches.add('C');
		h_matches.add('T');
		dnaAmbiguityCodes.put('H',h_matches);
		
		HashSet<Character> d_matches = new HashSet<Character>();
		d_matches.add('A');
		d_matches.add('G');
		d_matches.add('T');
		dnaAmbiguityCodes.put('D',d_matches);
		
		HashSet<Character> b_matches = new HashSet<Character>();
		b_matches.add('C');
		b_matches.add('G');
		b_matches.add('T');
		dnaAmbiguityCodes.put('B',b_matches);

		HashSet<Character> n_matches = new HashSet<Character>();
		n_matches.add('A');
		n_matches.add('C');
		n_matches.add('G');
		n_matches.add('T');
		dnaAmbiguityCodes.put('N',n_matches);
	}
}
