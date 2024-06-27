package net.cellingo.sequence_tools.annotation;

/**
 * This class encapsulates the coordinate properties of elements in sequences.
 * The value returned by getStart() should ALWAYS be lower than that returned by getStop(),
 * whatever the value returned by isComplement().
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceCoordinates implements Comparable<SequenceCoordinates> {
    boolean complement;
    boolean complete;
    int start;
    int stop;
    private boolean fivePrimeIncomplete;
    private boolean threePrimeIncomplete;
    private boolean midIncomplete;

    /**
     * constructor sets field complement to false and complete to true
     *
     * @param start
     * @param stop
     */
    public SequenceCoordinates(int start, int stop) {
        assert start <= stop : "a start coordinate greater than a stop coordinate was passed";
        this.start = start;
        this.stop = stop;
        this.complement = false;
        this.complete = true;
    }

    /**
     * constructor sets field complement to false and complete to true
     *
     * @param start
     * @param stop
     */
    public SequenceCoordinates(int start, int stop, boolean complement) {
        assert start <= stop : "a start coordinate greater than a stop coordinate was passed";
        this.start = start;
        this.stop = stop;
        this.complement = complement;
        this.complete = true;
    }

    /**
     * construct with all fields
     *
     * @param start
     * @param stop
     * @param complement
     * @param complete
     */
    public SequenceCoordinates(int start, int stop, boolean complement, boolean complete) {
        assert start <= stop : "a start coordinate greater than a stop coordinate was passed";
        this.start = start;
        this.stop = stop;
        this.complement = complement;
        this.complete = complete;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }


    /**
     * @param complement the complement to set
     */
    public void setComplement(boolean complement) {
        this.complement = complement;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(int stop) {
        this.stop = stop;
    }

    /**
     * @return the stop
     */
    public int getStop() {
        return stop;
    }

    /**
     * @return the strand
     */
    public boolean isComplement() {
        return complement;
    }

    /**
     * check whether element is complete
     *
     * @return is complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * sets whether this coordinates is 5'incomplete
     *
     * @param complete_5p
     */
    public void set5primeIncomplete(boolean complete_5p) {
        this.fivePrimeIncomplete = complete_5p;
    }

    /**
     * returns whether this coordinates is 5'incomplete
     *
     * @return is 5'-incomplete
     */
    public boolean is5primeIncomplete() {
        return fivePrimeIncomplete;
    }

    /**
     * sets whether this coordinates is 3'incomplete
     *
     * @param complete_3p
     */
    public void set3primeIncomplete(boolean complete_3p) {
        this.threePrimeIncomplete = complete_3p;
    }

    /**
     * returns whether this coordinates is 3'incomplete
     *
     * @return is 3'-incomplete
     */
    public boolean is3primeIncomplete() {
        return threePrimeIncomplete;
    }

    /**
     * sets whether this coordinates is mid incomplete
     *
     * @param complete_mid
     */
    public void setMidIncomplete(boolean complete_mid) {
        this.midIncomplete = complete_mid;
    }

    /**
     * returns whether this coordinates is mid incomplete
     *
     * @return is mid-incomplete
     */
    public boolean isMidIncomplete() {
        return midIncomplete;
    }

    /**
     * returns whether this coordinates object overlaps with another coordinates object
     *
     * @param otherCoordinates
     * @return coordinates are overlapping
     */
    public boolean overlaps(SequenceCoordinates otherCoordinates) {
        if ((this.getStop() <= otherCoordinates.getStop()) && (this.getStop() >= otherCoordinates.getStart()))
            return true;
        else if ((this.getStart() <= otherCoordinates.getStop()) && (this.getStart() >= otherCoordinates.getStart()))
            return true;

        else if ((otherCoordinates.getStop() <= this.getStop()) && (otherCoordinates.getStop() >= this.getStart()))
            return true;
        else if ((otherCoordinates.getStart() <= this.getStop()) && (otherCoordinates.getStart() >= this.getStart()))
            return true;

        else return false;
    }

    /**
     * returns whether this element is included completely within the given coordinates
     *
     * @param otherCoordinates
     * @return coordinates includes element
     */
    public boolean isIncludedWithin(SequenceCoordinates otherCoordinates) {
        return ((this.getStart() >= otherCoordinates.getStart()) && (this.getStop() <= otherCoordinates.getStop()));
    }

    public String toString() {
        return (this.getClass().getSimpleName() + "[start=" + start
                + " stop=" + stop
                + " complement=" + complement
                + " complete=" + complete + "]");
    }

    public int compareTo(SequenceCoordinates otherCoordinates) {
        /*compare based on start position*/
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (this.getStart() == otherCoordinates.getStart()) {
            return EQUAL;
        } else if (this.getStart() < otherCoordinates.getStart()) {
            return BEFORE;
        } else {
            return AFTER;
        }
    }
}
