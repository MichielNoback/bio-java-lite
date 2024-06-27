/**
 * 
 */
package net.cellingo.sequence_tools.seq_manipulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.cellingo.sequence_tools.sequences.Sequence;
import net.cellingo.sequence_tools.sequences.NucleicAcidSequence;
import net.cellingo.sequence_tools.sequences.ProteinSequence;

/**
 * A simple low-complexity filter implementation that returns true if the argument sequence is NOT low complexity.
 * This implementations simply looks at the frequency of letters. Sequences should be longer than 15 characters long, 
 * otherwise true is returned. This minimum length can be adjusted using setMinimumLength()
 * For nucleic acid sequences, it returns false if the sequence is   
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 0.1
 */
public class LowComplexityFilter implements SequenceFilter {
	/*minimum length for proper filtering*/
	private int minimumLength = 15;
	private Sequence sequence;
	
	/**
	 * default constructor
	 */
	public LowComplexityFilter(){ }
	
	/**
	 * sets the minimum sequence length for filtering; filter() returns true with shorter sequences
	 * @param minimumLength
	 */
	public void setMinimumLength( int minimumLength ){
		this.minimumLength = minimumLength;
	}
	
	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.seq_manipulation.SequenceFilter#filter(net.cellingo.sequence_tools.sequences.Sequence)
	 */
	public boolean filter(Sequence sequence) {
		this.sequence = sequence;
		
		/*short sequences simply return true*/
		if( sequence.getSequenceLength() < minimumLength) return true;
		
		if( sequence instanceof NucleicAcidSequence ) return !isNucleicAcidLowComplexity();
		
		if( sequence instanceof ProteinSequence ) return !isProteinLowComplexity();
		
		/*no other checks implemented; simply return true*/
		System.out.println("WARNING LowComplexityFilter.filter() returns true without checking sequence; a filter for this sequence type has not yet been implemented");
		return true;
	}
	
	/*checks for nucleotide low complexity*/
	private boolean isNucleicAcidLowComplexity(){
		int length = sequence.getSequenceLength();
		StringBuilder sb = new StringBuilder(sequence.getSequenceString());
		HashMap<Character,Counter> freqs = new HashMap<Character,Counter>();

		/*iterate and get frequency of all characters*/
		for( int i=0; i<length; i++  ){
			char ch = sb.charAt(i);
			if( freqs.containsKey(ch)) freqs.get(ch).increment();
			else{
				Counter c = new Counter(ch);
				c.setVal(1);
				freqs.put(ch, c);
			}
		}
		ArrayList<Counter> list = new ArrayList<Counter>();
		list.addAll( freqs.values() );
		Collections.sort( list );
		
		double incrementalPercentage = 0;
		for( int i=0; i<list.size(); i++ ){
			incrementalPercentage += ( (double)list.get(i).getVal() / (double)length );
			
			if( i==0 && incrementalPercentage > 0.8 ) return true;
			if( i==1 && incrementalPercentage > 0.9 ) return true;
			if( i==2 && incrementalPercentage > 0.98 ) return true;
			
			//System.out.println( "sequence has length: " + length + "; character " + c.getKey() + " has frequency " + c.getVal() + " (" + (((double)c.getVal()/(double)length)*100) + "%)");
		}
		return false;
	}

	private boolean isProteinLowComplexity(){
		System.out.println("WARNING LowComplexityFilter.filter() returns true without checking sequence; a filter for this sequence type has not yet been implemented");
		return true;
	}
	
	public class Counter implements Comparable<Counter>{
		char key;
		int val;
		
		public Counter( char ch ){
			this.key = ch;
		}
		
		public void increment(){
			this.val ++;
		}
		
		public void setVal(int newVal){
			this.val = newVal;
		}
		
		public char getKey(){
			return key;
		}
		
		public int getVal(){
			return val;
		}

		public int compareTo(Counter c) {
			if( this.getVal() < c.getVal() ) return 1;
			if( this.getVal() > c.getVal() ) return -1;
			return 0;
		}
		
		
		
	}
}
