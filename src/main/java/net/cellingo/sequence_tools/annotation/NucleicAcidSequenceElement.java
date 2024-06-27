/**
 * 
 */
package net.cellingo.sequence_tools.annotation;

import net.cellingo.sequence_tools.sequences.NucleicAcidSequence;

/**
 * Base class that extends SequenceElement and adds a NucleicAcid specific feature:
 * whether the element is on the forward strand
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class NucleicAcidSequenceElement extends SequenceElement{

	public NucleicAcidSequenceElement(String id){
		super(id);
	}
	
	/**
	 * get the coding region of this ORF. Returns null if no parent sequence has been defined.
	 * @return coding region
	 */
	public NucleicAcidSequence getParentSequenceRegion(){
		if( getParentSequence() != null ){
			try{
				return (NucleicAcidSequence)this.getParentSequence().getSubSequence( this.getSequenceCoordinates().getStart(), this.getSequenceCoordinates().getStop() );
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else{
			return null;
		}
	}
}
