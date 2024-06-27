package net.cellingo.sequence_tools.annotation;

import net.cellingo.sequence_tools.gene_analysis.OrfDefinition;
import net.cellingo.sequence_tools.sequences.ProteinSequence;
import net.cellingo.sequence_tools.sequences.SequenceFrame;

import java.io.Serializable;

/**
 * class represents an Open Reading Frame as extension on class SequenceElement
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class OpenReadingFrame extends NucleicAcidSequenceElement implements Serializable{
	private ProteinSequence proteinSequence;
	private SequenceFrame readFrame;
	private OrfDefinition orfDefinition;
	private boolean probablyReal;
	
	/**
	 * default constructor
	 */
	public OpenReadingFrame(){
		super(SequenceElement.getNextAutogeneratedId());
		initialise();
	}
	/**
	 * constructor takes protein sequence as argument
	 * @param proteinSequence
	 */
	public OpenReadingFrame(ProteinSequence proteinSequence){
		super(SequenceElement.getNextAutogeneratedId());
		this.proteinSequence = proteinSequence;
		initialise();
	}
	
	/**
	 * initialization code
	 */
	private void initialise(){
		setSequenceElementType(SequenceElementType.CDS);
		readFrame = SequenceFrame.UNKNOWN;
		orfDefinition = OrfDefinition.UNKNOWN;
		probablyReal = false;
	}

	/**
	 * there is evidence that this ORF represents a real gene
	 * @return probablyReal
	 */
	public boolean isProbablyReal() {
		return probablyReal;
	}
	
	/**
	 * set if there is evidence that this ORF represents a real gene
	 * @param probablyReal
	 */
	public void setProbablyReal(boolean probablyReal) {
		this.probablyReal = probablyReal;
	}
	
	/**
	 * get the definition of this ORF
	 * @return orfDefinition
	 */
	public OrfDefinition getOrfDefinition() {
		return orfDefinition;
	}
	
	/**
	 * set the definition of this ORF
	 * @param orfDefinition
	 */
	public void setOrfDefinition(OrfDefinition orfDefinition) {
		this.orfDefinition = orfDefinition;
	}
	
	/**
	 * get the protein sequence translation of this ORF
	 * @return protein sequence
	 */
	public ProteinSequence getProteinSequence() {
		return proteinSequence;
	}
	
	/**
	 * set the protein sequence translation of this ORF
	 * @param proteinSequence
	 */
	public void setProteinSequence(ProteinSequence proteinSequence) {
		this.proteinSequence = proteinSequence;
	}
	
	/**
	 * get the reading frame of this ORF
	 * @return reading frame
	 */
	public SequenceFrame getReadFrame() {
		return readFrame;
	}
	
	/**
	 * set the reading frame of this ORF
	 * @param readFrame
	 */
	public void setReadFrame(SequenceFrame readFrame) {
		this.readFrame = readFrame;
	}

	@Override
	public String toString() {
		return "OpenReadingFrame{" +
				"proteinSequence=" + proteinSequence +
				", coordinates=" + getSequenceCoordinates() +
				", readFrame=" + readFrame +
				'}';
	}
}
