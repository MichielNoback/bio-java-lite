package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.alphabets.*;

/**
 * Factory class to create instances of subclasses of Sequence
 * Creation date: 8-7-2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public abstract class SequenceFactory {

    /**
     * factory method: create a new sequence object of given sequence type.
     *
     * @param sequence
     * @param type
     * @return seq
     * @throws SequenceCreationException ex
     */
    public static Sequence createSequence(
            String sequence, final SequenceType type) throws SequenceCreationException {
        sequence = sequence.toUpperCase();
        if (type == SequenceType.DNA) {
            return new DnaSequence(sequence);
        } else if (type == SequenceType.RNA) {
            return new RnaSequence(sequence);
        } else if (type == SequenceType.PROTEIN) {
            return new ProteinSequence(sequence);
        } else if (type == SequenceType.STRUCTURE) {
            return new StructureSequence(sequence);
        } else {
            throw new SequenceCreationException("ERROR: unknown sequence type");
        }
    }

    /**
     * factory method: create a new sequence object of given sequence type.
     *
     * @param sequence the sequence to create from
     * @param type     the type
     * @return seq
     * @throws SequenceCreationException ex
     */
    public static Sequence createSequence(
            final StringBuilder sequence, final SequenceType type) throws SequenceCreationException {
        return createSequence(sequence.toString(), type);
    }

    /**
     * factory method: find out what type of sequence is in the String and return the sequence object.
     *
     * @param sequence string
     * @return seq
     * @throws SequenceCreationException ex
     */
    public static Sequence createSequence(String sequence) throws SequenceCreationException {
        sequence = sequence.toUpperCase();
        boolean dna = true;
        boolean rna = true;
        boolean protein = true;
        boolean structure = true;
        char[] characterArray = sequence.toCharArray();
        //check dna alphabet
        for (char character : characterArray) {
            if (!SequenceAlphabet.ALPHABET_DNA.isValidAlphabetCharacter(character)) {
                dna = false;
                break;
            }
        }
        //check rna alphabet
        for (char character : characterArray) {
            if (!SequenceAlphabet.ALPHABET_RNA.isValidAlphabetCharacter(character)) {
                rna = false;
                break;
            }
        }
        //check protein alphabet
        for (char character : characterArray) {
            if (!SequenceAlphabet.ALPHABET_PROTEIN.isValidAlphabetCharacter(character)) {
                protein = false;
                break;
            }
        }
        //check textAlignment alphabet
        for (char character : characterArray) {
            if (!SequenceAlphabet.ALPHABET_STRUCTURE.isValidAlphabetCharacter(character)) {
                structure = false;
                break;
            }
        }
        //now evaluate and set the sequence type
        if (dna && protein) {
            return createSequence(sequence, SequenceType.DNA);
            //return new DnaSequence(sequence);
        } else if (protein) {
            return createSequence(sequence, SequenceType.PROTEIN);
            //return new ProteinSequence(sequence);
        } else if (rna) {
            return createSequence(sequence, SequenceType.RNA);
            //return new RnaSequence(sequence);
        } else if (structure) {
            return createSequence(sequence, SequenceType.STRUCTURE);
            //return new StructureSequence(sequence);
        } else {
            throw new SequenceCreationException(
                    "ERROR: provided String could not be converted to a valid sequence object");
        }
    }

    /**
     * factory method: find out what type of sequence is in the String and return the sequence object.
     *
     * @param sequence string
     * @return seq
     * @throws SequenceCreationException ex
     */
    public static Sequence createSequence(final StringBuilder sequence) throws SequenceCreationException {
        return createSequence(sequence.toString());
    }
}
