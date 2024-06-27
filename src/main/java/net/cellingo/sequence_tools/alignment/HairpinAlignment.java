/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

import net.cellingo.sequence_tools.annotation.SequenceElement;

/**
 * An object representing an intramolecular alignment
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class HairpinAlignment extends Alignment {
	private SequenceElement loop;
	private boolean onComplement;
	

	/**
	 * @return the loop
	 */
	public SequenceElement getHairpinLoop() {
		return loop;
	}

	/**
	 * @param loop the loop to set
	 */
	public void setHairpinLoop(SequenceElement loop) {
		this.loop = loop;
	}

	/**
	 * @return the onComplement
	 */
	public boolean isOnComplement() {
		return onComplement;
	}

	/**
	 * @param onComplement the onComplement to set
	 */
	public void setOnComplement(boolean onComplement) {
		this.onComplement = onComplement;
	}

	
	
}
