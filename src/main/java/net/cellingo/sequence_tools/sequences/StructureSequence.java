/**
 *
 */
package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class StructureSequence extends Sequence {

    /**
     * @param sequenceString
     */
    public StructureSequence(String sequenceString) {
        super(sequenceString);
        init();
    }


    private void init() {
        this.setSequenceType(SequenceType.STRUCTURE);
        this.setAlphabet(SequenceAlphabet.ALPHABET_STRUCTURE);
        this.setProgramSeqID(programMaxSeqID++);
    }

    @Override
    public double getMolecularWeight() {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
