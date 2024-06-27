/**
 * 
 */
package net.cellingo.sequence_tools.sequences;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceStrand {
	BOTH("forward and reverse complement strand"),
	FORWARD("forward strand"),
	COMPLEMENT("reverse complement strand"),
	UNKNOWN("unknown frame");
	
	private String strand;
	
	private SequenceStrand( String strand ){
		this.strand = strand;
	}

	public String toString(){
		return strand;
	}
}
