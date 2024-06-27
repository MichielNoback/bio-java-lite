package net.cellingo.sequence_tools.gene_analysis;

import net.cellingo.sequence_tools.annotation.OpenReadingFrame;
import net.cellingo.sequence_tools.annotation.SequenceCoordinates;
import net.cellingo.sequence_tools.sequences.*;

import java.util.ArrayList;

/**
 * objects of this class find ORFs according to specifications provided
 * in an OptionsObject. Found ORFs can be accessed through the
 * getOrfList() method.
 *
 * @author Michiel Noback (michiel@cellingo.net)
 * @version 1.0
 */
public class OrfFinder {
    private NucleicAcidSequence sequence;
    private GeneAnalysisOptions options;
    private int orfNumber;
    private ArrayList<OpenReadingFrame> orfList;
    private int currentOrfStart;
    private int currentOrfStop;
    private int currentPosition;
    private int lastStartPosition;
    private SequenceStrand strandToAnalyse;
    private int sequenceLength;
    private CodonTable codonTable;

    /**
     * constructor takes as arguments the sequence to be analysed and the options that define the way to do it
     *
     * @param sequence
     * @param options
     */
    public OrfFinder(NucleicAcidSequence sequence, GeneAnalysisOptions options) {
//		this.sequence = new NucleicAcidSequence(sequence.getSequenceString());
        //this.biolSequence = biolSequence;
        this.sequence = sequence;
        this.options = options;
        this.orfList = new ArrayList<OpenReadingFrame>();
        this.orfNumber = 0;
    }

    /**
     * get the found OpenReadingFrames
     *
     * @return orfList
     */
    public ArrayList<OpenReadingFrame> getOrfList() {
        return orfList;
    }

    /**
     * start the analysis
     */
    public void start() {
        SequenceStrand strandSelection = options.getStrandSelection();
        if (strandSelection == SequenceStrand.BOTH) {
            translate(this.sequence, SequenceStrand.FORWARD);
            resetFields();
            translate(sequence.reverseComplement(), SequenceStrand.COMPLEMENT);
        } else if (strandSelection == SequenceStrand.FORWARD) {
            translate(sequence, SequenceStrand.FORWARD);
        } else { //else if (strandSelection == SequenceStrand.COMPLEMENT)
            translate(sequence.reverseComplement(), SequenceStrand.COMPLEMENT);
        }
    }

    /**
     * resets fields for reverse starnd analysis
     */
    private void resetFields() {
        currentOrfStart = 0;
        currentOrfStop = 0;
        currentPosition = 0;
        lastStartPosition = 0;
    }

    /**
     * This method does the actual translation. The char strandToAnalyse='F'
     * (forward) or char strandToAnalyse='R' (reverse) argument is used for
     * posttranslation ORF processing
     */
    private void translate(NucleicAcidSequence sequence, SequenceStrand strand) {
        this.sequence = sequence;
        this.strandToAnalyse = strand;
        this.sequenceLength = sequence.getSequenceLength();
        boolean complement = false;
        //used for registering right frame with ORFs
        int frameOffset;
        this.codonTable = options.getCodonTable();
        int minimumOrfSize = options.getMinimumOrfSize();
        int startPosition = options.getStartAnalysisPosition();
        int stopPosition = options.getStopAnalysisPosition();

        /*if no stop has been set, use length of sequence as stop position*/
        if (stopPosition == 0) stopPosition = sequenceLength;

		/*correct for erroneous stops*/
        if (stopPosition > sequenceLength) stopPosition = sequenceLength;

        if (strand == SequenceStrand.FORWARD) {
            frameOffset = 1;
        } else {
            //reverse strandToAnalyse analysis
            frameOffset = 4;
            startPosition = (sequenceLength - stopPosition);
            stopPosition = sequenceLength - startPosition;
            complement = true;
        }

        for (int currentFrame = 0; currentFrame <= 2; currentFrame++) {
            StringBuilder currentProteinSequence = new StringBuilder();
            lastStartPosition = 0;
            //get the subsequent codons
            for (currentPosition = (startPosition + currentFrame); currentPosition < (stopPosition - 2); currentPosition += 3) {
                String codon = "";
                try {
                    codon = sequence.getSubString(currentPosition, currentPosition + 3);
                } catch (IllegalSequenceOperation e) {
                    e.printStackTrace();
                }
                String aminoAcid = codonTable.getCodonTranslation(codon);

                if (aminoAcid.equals("*")) {
                    //a stop codon has been found: end of ORF
                    if (currentProteinSequence.length() >= minimumOrfSize) {
                        processORF(currentFrame, currentProteinSequence, frameOffset, complement);
                    }
                    currentProteinSequence = new StringBuilder();
                } else {
                    if (currentProteinSequence.length() > 0) {
                        //we are already building an ORF
                        currentProteinSequence.append(aminoAcid);
                    } else if (isCorrectStart(codon)) {
                        //a new ORF has started
                        currentProteinSequence.append(aminoAcid);
                        lastStartPosition = currentPosition;
                    } //ignore codon otherwise
                }
            }
            /*process running ORFs that were not terminated by a stop codon*/
            if (currentProteinSequence.length() >= minimumOrfSize) {//minimum ORF size has been reached: process this one
                processORF(currentFrame, currentProteinSequence, frameOffset, complement);
            }
        }
    }

    private boolean isCorrectStart(String codon) {
        if ("ATG".equals(codon) && options.getOrfDefinition() == OrfDefinition.ATG_TO_STOP) {
            return true;
        } else if (options.getOrfDefinition() == OrfDefinition.STOP_TO_STOP) {
            return true;
        } else if (options.getOrfDefinition() == OrfDefinition.START_TO_STOP && codonTable.checkInitiationCodon(codon)) {
            return true;
        }
        return false;
    }

    private void processORF(int currentFrame, StringBuilder currentProteinSequence, int frameOffset, boolean complement) {
        orfNumber++;
        if (strandToAnalyse == SequenceStrand.FORWARD) {    //process forward strandToAnalyse ORF
            currentOrfStart = lastStartPosition;
            currentOrfStop = currentPosition + 2;
        } else {    //process reverse strandToAnalyse ORF
            currentOrfStop = sequenceLength - lastStartPosition;
            currentOrfStart = sequenceLength - (currentPosition + 2);
        }
        //System.out.println("processing ORF");
        ProteinSequence proteinSequence = new ProteinSequence(currentProteinSequence.toString());
        OpenReadingFrame orf = new OpenReadingFrame();
        orf.setProteinSequence(proteinSequence);
        orf.setOrfDefinition(options.getOrfDefinition());
        orf.setParentSequence(sequence);

        SequenceCoordinates oc = new SequenceCoordinates(currentOrfStart, currentOrfStop, complement, true);
        orf.addCoordinates(oc);

        orf.setReadFrame(SequenceFrame.getSequenceFrame(currentFrame + frameOffset));
        orfList.add(orf);
    }


    /**
     * This method does a six-frame translation on a given string of dna and
     * returns a String[8] array with the forward frames in index{0][1][2] then
     * forward and reverse strands dna at [3][4] and reverse frames at [5][6][7]
     */
    public static String[] doSixFrameTranslation(NucleicAcidSequence seq) {
        String[] sixFrameTranslationList = new String[8];
        //String[] threeFrames = new String[3];
//		String complementDna = "";
//		String reverseComplement = "";
        String aminoAcidSeparator = "  ";
        String tempString = "";

        StringBuilder[] threeFrames = doThreeFrameTranslation(seq, aminoAcidSeparator);
        sixFrameTranslationList[0] = threeFrames[0].toString();
        sixFrameTranslationList[1] = threeFrames[1].toString();
        sixFrameTranslationList[2] = threeFrames[2].toString();
        sixFrameTranslationList[3] = seq.getSequenceString();

        //first complement for printing reverse strandToAnalyse
        NucleicAcidSequence complementSeq = seq.complement();

        sixFrameTranslationList[4] = complementSeq.getSequenceString();

        //now also reverse for analysis
        complementSeq = seq.reverseComplement();
        //complementSeq.reverse();

        threeFrames = doThreeFrameTranslation(complementSeq, aminoAcidSeparator);

        threeFrames[0].reverse();
        tempString = threeFrames[0].toString();
        tempString = tempString.trim();
        tempString = "  " + tempString + "";
        sixFrameTranslationList[5] = tempString;
        threeFrames[1].reverse();
        tempString = threeFrames[1].toString();
        tempString = tempString.trim();
        tempString = "    " + tempString + " ";
        sixFrameTranslationList[6] = tempString;
        threeFrames[2].reverse();
        tempString = threeFrames[2].toString();
        tempString = tempString.trim();
        tempString = "   " + tempString + "  ";
        sixFrameTranslationList[7] = tempString;

        return sixFrameTranslationList;
    }

    /**
     * Do a three-frame translation on a given sequence string "seq"
     * An String[] array with the three frames is returned. Amino acids are separated
     * by the String provided in the second argument "aminoAcidSeparator"
     */
    public static StringBuilder[] doThreeFrameTranslation(NucleicAcidSequence seq, String aminoAcidSeparator) {
        StringBuilder[] threeFrames = new StringBuilder[3];

        for (int currentFrame = 0; currentFrame < 3; currentFrame++) {
            threeFrames[currentFrame] = doTranslation(currentFrame, seq, 0, seq.getSequenceLength(), aminoAcidSeparator);
        }
        return threeFrames;
    }

    /**
     * performs a single frame translation
     *
     * @param frame              1 2 or 3
     * @param seq
     * @param aminoAcidSeparator
     * @return translation
     */
    public static StringBuilder doTranslation(int frame, NucleicAcidSequence seq, int start, int stop, String aminoAcidSeparator) {
        int seqLength = seq.getSequenceLength();
        if (stop > seqLength) stop = seqLength;
        CodonTable codons = new CodonTable();

        String codon = "";
        String aminoAcid = "";
        //String spacerString = aminoAcidSeparator;
        String firstSpacerString = "";
        String aminoAcidString = "";

        //get the subsequent codons
        switch (frame) {
            case 0:
                firstSpacerString = "";
                break;
            case 1:
                firstSpacerString = " ";
                break;
            case 2:
                firstSpacerString = "  ";
                break;
        }
        StringBuilder currentProteinSequence = new StringBuilder(); //string to hold current protein sequence
        currentProteinSequence.append(firstSpacerString);
        for (int position = (start + frame); position <= (stop - 3); position += 3) {
            try {
                codon = seq.getSubString(position, position + 3);
            } catch (IllegalSequenceOperation e) {
                e.printStackTrace();
            }
            aminoAcid = codons.getCodonTranslation(codon);
            if (aminoAcid != null) {    //a complete and existing codon is present
                aminoAcidString = aminoAcid + aminoAcidSeparator;
                currentProteinSequence.append(aminoAcidString);
            } else {
                currentProteinSequence.append(aminoAcidSeparator);
            }

        }//end looping the codons
        switch (frame) {
            case 0:
                currentProteinSequence.append("");
                break;
            case 1:
                currentProteinSequence.append("  ");
                break;
            case 2:
                currentProteinSequence.append(" ");
                break;
        }

        return currentProteinSequence;
    }


}
