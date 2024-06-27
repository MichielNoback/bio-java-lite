/**
 *
 */
package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;

import java.io.Serializable;

/**
 * class represents a protein sequence
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class ProteinSequence extends Sequence implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5521580706458682880L;

    /**
     * @param sequenceString
     */
    public ProteinSequence(String sequenceString) {
        super(sequenceString);
        init();
    }

    private void init() {
        this.setSequenceType(SequenceType.PROTEIN);
        this.setAlphabet(SequenceAlphabet.ALPHABET_PROTEIN);
        this.setProgramSeqID(programMaxSeqID++);
    }

    @Override
    public double getMolecularWeight() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
