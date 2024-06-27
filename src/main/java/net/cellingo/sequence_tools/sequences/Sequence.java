package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;
import net.cellingo.sequence_tools.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * class represents the base type for all sequences types. This class provides some factory methods to generate the
 * different types as well.
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.01
 */
public abstract class Sequence implements SequenceObject, Comparable<Sequence> {

    protected static int programMaxSeqID = 1;
    private int programSeqID;
    private SequenceType sequenceType;
    private SequenceAlphabet alphabet;
    private String sequence;
    private String sequenceName;
    private Attributes attributes;
    private String accessionNumber;

    public Sequence(String sequence) {
        this.sequence = sequence;
    }

    /**
     * returns the index at which this sequence contains the subsequence, or -1 if not found.
     *
     * @param subsequence the subsequence to find
     * @return index
     */
    public int indexOf(final String subsequence) {
        return this.sequence.indexOf(subsequence);
    }

    /**
     * finds all occurrences (as index) of the given subsequence (case insensitive).
     * Returns an empty list if no occurrences are found.
     *
     * @param subsequence the sequence to find
     * @return occurrences
     */
    public List<Integer> findOccurrences(final String subsequence) {
        List<Integer> occurrences = new ArrayList<Integer>();
        String ss = subsequence.toUpperCase();
        int index = this.sequence.indexOf(ss);
        while (index > -1) {
            occurrences.add(index);
            index = this.sequence.indexOf(ss, index + 1);
        }
        return occurrences;
    }

    /**
     * get the sequence alphabet.
     *
     * @return sequenceAlphabet
     */
    public SequenceAlphabet getAlphabet() {
        return alphabet;
    }

    /**
     * set the sequence alphabet.
     *
     * @param alphabet the alphabet
     */
    protected void setAlphabet(final SequenceAlphabet alphabet) {
        this.alphabet = alphabet;
    }

    /**
     * get the sequence as a String object.
     *
     * @return sequenceString
     */
    public String getSequenceString() {
        return sequence;
    }

    /**
     * get the sequence as a StringBuilder object.
     * TODO refactor this away
     *
     * @return sequence
     */
//    public StringBuilder getSequenceStringBuilder() {
//        return new StringBuilder(sequence);
//    }

    /**
     * set the sequence name.
     *
     * @param name the name
     */
    public void setSequenceName(final String name) {
        sequenceName = name;
    }

    /**
     * get the sequence name.
     *
     * @return sequenceName
     */
    public String getSequenceName() {
        if (sequenceName == null) {
            this.sequenceName = "UNNAMED SEQUENCE";
        }
        return sequenceName;
    }

    /**
     * get the sequencetype of this object.
     *
     * @return sequenceType the type
     */
    public SequenceType getSequenceType() {
        return sequenceType;
    }

    /**
     * set the sequence type.
     *
     * @param type the sequence type
     */
    protected void setSequenceType(final SequenceType type) {
        this.sequenceType = type;
    }

    /**
     * get sequence length.
     *
     * @return length the sequence length
     */
    public int getSequenceLength() {
        return sequence.length();
    }

    /**
     * @return the attributes
     */
    public Attributes getAttributes() {
        if (attributes == null) {
            attributes = new Attributes();
        }
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(final Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * method creates a subsequence from this one.
     * Starting at (and including) the given start position and ending at
     * (and including) the given end position
     *
     * @param startPosition the start
     * @param stopPosition  the stop
     * @return biologicalSequence
     * @throws net.cellingo.sequence_tools.sequences.IllegalSequenceOperation ex
     */
    public Sequence getSubSequence(int startPosition, int stopPosition) throws IllegalSequenceOperation {
        try {
            Sequence subSequence = SequenceFactory.createSequence(
                    new StringBuilder(sequence.subSequence(startPosition, stopPosition)), this.getSequenceType());
            subSequence.setSequenceName(this.getSequenceName() + " [FROM " + startPosition + " TO " + stopPosition + "]");
            return subSequence;
        } catch (Exception e) {
            throw new IllegalSequenceOperation(
                    "ERROR: requested subsequence not possible: " + startPosition + " to " + stopPosition);
        }
    }

    /**
     * method creates a subsequence from this one, starting at (and including) the given start position and ending at
     * (and including) the given end position provided in the coordinates object. If the coordinates object returns true
     * on the method call coordinates.isComplement(), the subsequence is still selected the same way
     *
     * @param coordinates the coordinates
     * @return biologicalSequence
     * @throws net.cellingo.sequence_tools.sequences.IllegalSequenceOperation ex
     */
    public Sequence getSubSequence(final SequenceCoordinates coordinates) throws IllegalSequenceOperation {
        try {
            return getSubSequence(coordinates.getStart(), coordinates.getStop());
        } catch (Exception e) {
            throw new IllegalSequenceOperation(
                    "ERROR: requested subsequence not possible: "
                            + coordinates.getStart() + " to " + coordinates.getStop());
        }
    }

    /**
     * get a substring of this sequence.
     *
     * @param startPosition the start (0-based)
     * @param stopPosition  the stop (0-based)
     * @return substring
     * @throws IllegalSequenceOperation ex
     */
    public String getSubString(final int startPosition, final int stopPosition) throws IllegalSequenceOperation {
        try {
            String subSequence = sequence.substring(startPosition, stopPosition);
            return subSequence;
        } catch (Exception e) {
            throw new IllegalSequenceOperation(
                    "ERROR: requested subsequence not possible: " + startPosition + " to " + stopPosition);
        }
    }

    /**
     * method creates a subsequence from this one, starting at (and including) the given start position and ending at
     * (and including) the given end position. However, several checks are done to prevent
     * ArrayIndexOutOfBoundsExceptions: if(startPosition<=0) -> startPosition = 1; if(startPosition>=stopPosition) ->
     * return null; if(stopPosition>getSequenceLength()) -> stopPosition = getSequenceLength();
     *
     * @param startPosition 0-based
     * @param stopPosition  0-based
     * @return biologicalSequence
     * @throws net.cellingo.sequence_tools.sequences.IllegalSequenceOperation ex
     */
    public String getSubStringArrayBoundsSafe(int startPosition, int stopPosition)
            throws IllegalSequenceOperation {
        if ((startPosition < 0 && stopPosition < 1)
                || (startPosition > getSequenceLength())
                || (startPosition >= stopPosition)) {
            throw new IllegalSequenceOperation(
                    "requested subsequence no possible: " + startPosition + " to " + stopPosition);
        }
        if (startPosition < 0) {
            startPosition = 0;
        }
        if (stopPosition > getSequenceLength()) {
            stopPosition = getSequenceLength();
        }
        return sequence.substring(startPosition, stopPosition);
    }

    /**
     * method creates a subsequence from this one, starting at (and including) the given start position and ending at
     * (and including) the given end position. However, several checks are done to prevent
     * ArrayIndexOutOfBoundsExceptions: if(startPosition<=0) -> startPosition = 1; if(startPosition>=stopPosition) ->
     * return null; if(stopPosition>getSequenceLength()) -> stopPosition = getSequenceLength();
     *
     * @param startPosition 0-based
     * @param stopPosition  0-based
     * @return biologicalSequence
     * @throws net.cellingo.sequence_tools.sequences.IllegalSequenceOperation ex
     */
    public Sequence getSubSequenceArrayBoundsSafe(
            int startPosition, int stopPosition) throws IllegalSequenceOperation {
        String substring = getSubStringArrayBoundsSafe(startPosition, stopPosition);
        try {
            return SequenceFactory.createSequence(substring, this.getSequenceType());
        } catch (SequenceCreationException e) {
            throw new IllegalSequenceOperation("requested subsequence no possible: " + startPosition + " to " + stopPosition);
        }
    }

    /**
     * method creates a subsequence from this one, starting at (and including) the given start position and ending at
     * (and including) the given end position provided by the coordinates object. If the coordinates object returns true
     * on the method call coordinates.isComplement(), the subsequence is still selected the same way
     * <p>
     * Several checks are done to prevent ArrayIndexOutOfBoundsExceptions: if(startPosition<=0) -> startPosition = 1;
     * if(startPosition>=stopPosition) -> return null; if(stopPosition>getSequenceLength()) -> stopPosition =
     * getSequenceLength();
     *
     * @param coordinates the coordinates
     * @return biologicalSequence
     * @throws net.cellingo.sequence_tools.sequences.IllegalSequenceOperation ex
     */
    public Sequence getSubSequenceArrayBoundsSafe(
            final SequenceCoordinates coordinates) throws IllegalSequenceOperation {

        int startPosition = coordinates.getStart();
        int stopPosition = coordinates.getStop();

        return getSubSequenceArrayBoundsSafe(startPosition, stopPosition);
    }

    /**
     * check the sequence for being entirely "core alphabet".
     *
     * @return isCoreAlphabet
     */
    public boolean sequenceIsCoreAlphabet() {
        //SequenceAlphabet alphabet = this.getAlphabet();
        for (char character : this.sequence.toCharArray()) {
            if (!this.getAlphabet().isCoreCharacter(character)) {
                return false;
            }
        }
        return true;
    }

    /**
     * get the program sequence ID.
     *
     * @return programSeqID
     */
    public int getProgramSequenceID() {
        return programSeqID;
    }

    /**
     * set the program SeqID.
     *
     * @param programSeqID the ID
     */
    public void setProgramSeqID(final int programSeqID) {
        this.programSeqID = programSeqID;
    }

    /**
     * @return the accessionNumber
     */
    public String getAccessionNumber() {
        if (this.attributes != null) {
            if (this.attributes.getFirstAttributeOfType("ID") != null) {
                return attributes.getFirstAttributeOfType("ID");
            } else if (this.attributes.getFirstAttributeOfType("db_xref") != null) {
                return attributes.getFirstAttributeOfType("db_xref");
            }
        } else if (this.accessionNumber != null) {
            return accessionNumber;
        }
        return "no ID";
    }

    /**
     * @param accessionNumber the accessionNumber to set
     */
    public void setAccessionNumber(final String accessionNumber) {
        if (this.attributes != null) {
            attributes.addAttribute("ID", accessionNumber);
        } else {
            this.accessionNumber = accessionNumber;
        }
    }

    /**
     * implementation of interface SequenceObject method.
     *
     * @return seqobject
     */
    public SequenceObjectType getSequenceObjectType() {
        return SequenceObjectType.SIMPLE_SEQUENCE;
    }

    /**
     * implementation of interface SequenceObject method, in this case the object itself is returned.
     *
     * @return seq object
     */
    public Sequence getSequence() {
        return this;
    }

    /**
     * returns the molecular weight of this molecule.
     *
     * @return MW in Daltons
     */
    public abstract double getMolecularWeight();

    /**
     * sort on id.
     *
     * @param otherSequence
     * @return compare
     */
    @Override
    public int compareTo(Sequence otherSequence) {
        if (this.getProgramSequenceID() < otherSequence.getProgramSequenceID()) {
            return -1;
        } else if (this.getProgramSequenceID() > otherSequence.getProgramSequenceID()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * overrides the toString method and prints the name and first 60 letters.
     */
    @Override
    public String toString() {
        String sequenceStringRepr = "";
        if (this.getSequenceLength() > 50) {
            try {
                sequenceStringRepr = this.getSubString(0, 15)
                        + "......" + this.getSubString(this.getSequenceLength() - 15, this.getSequenceLength());
                //System.out.println("LENGTH=" + sequenceStringRepr.length() + "::" + sequenceStringRepr);
            } catch (IllegalSequenceOperation e) {
                e.printStackTrace();
            }
        } else {
            sequenceStringRepr = this.getSequenceString();
        }
        String stringRepr = this.getSequenceName() + "[" + sequenceStringRepr + "]";
        return stringRepr;
    }

    /**
     * overrides the Object.clone() method. Returns a new sequence object with the same sequence, name and accession
     * number as the given sequence. The program sequence ID is NOT the same.
     *
     * @return seq
     */
    @Override
    public Sequence clone() {
        try {
            Sequence seq = SequenceFactory.createSequence(
                    this.getSequenceString(), this.getSequenceType());
            seq.setSequenceName(this.getSequenceName());
            if (this.attributes != null) {
                seq.setAttributes(this.getAttributes().clone());
            }
            return seq;
        } catch (SequenceCreationException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
