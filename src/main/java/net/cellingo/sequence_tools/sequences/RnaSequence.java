/**
 *
 */
package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;

import java.io.Serializable;

/**
 * class represents an RNA sequence
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class RnaSequence extends NucleicAcidSequence implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5731987021613838159L;

    /**
     * @param sequenceString
     */
    public RnaSequence(String sequenceString) {
        super(sequenceString);
        init();
    }

    @Override
    protected NucleicAcidSequence getNewInstance(String sequenceString) {
        return new RnaSequence(sequenceString);
    }

    private void init() {
        this.setSequenceType(SequenceType.RNA);
        this.setAlphabet(SequenceAlphabet.ALPHABET_RNA);
        this.setProgramSeqID(programMaxSeqID++);
    }

    /**
     * convert to DNA and get a new DnaSequence object back
     *
     * @return DnaSequence
     */
    public DnaSequence toDna() {
        DnaSequence dnaSequence = new DnaSequence(getSequenceString().replace('U', 'T'));
        return dnaSequence;
    }

    @Override
    public double getMolecularWeight() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
