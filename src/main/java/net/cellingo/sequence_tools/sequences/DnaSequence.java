package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;

import java.io.Serializable;

/**
 * class represents a DNA sequence
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class DnaSequence extends NucleicAcidSequence implements Serializable {

    private static final long serialVersionUID = -1474898689720249681L;

    /**
     * default constructor for this class
     */
    public DnaSequence(String sequenceString) {
        super(sequenceString);
        init();
    }

    @Override
    protected NucleicAcidSequence getNewInstance(String sequenceString) {
        return new DnaSequence(sequenceString);
    }

    private void init() {
        this.setSequenceType(SequenceType.DNA);
        this.setAlphabet(SequenceAlphabet.ALPHABET_DNA);
        this.setProgramSeqID(programMaxSeqID++);
    }

    /**
     * convert to RNA and get a new RnaSequence object back
     *
     * @return RnaSequence
     */
    public RnaSequence toRna() {
        RnaSequence rnaSequence = new RnaSequence(getSequenceString().replace('T', 'U'));
        return rnaSequence;
    }

    @Override
    public double getMolecularWeight() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
