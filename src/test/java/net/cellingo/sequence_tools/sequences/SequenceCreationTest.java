package net.cellingo.sequence_tools.sequences;

import net.cellingo.sequence_tools.annotation.SequenceCoordinates;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Creation date: 8-7-2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public class SequenceCreationTest {
    private String testSeqDNAone;
    private String testSeqDNAtwo;

    @Before
    public void setUp() {
        testSeqDNAone = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgat"
                + "ttttaggtatcacaacactgtgaccaagaccatcgaaacccacacagacaatatcgagacaaacatggatgaaaacc"
                + "tccgcattcctgtgactgctgaggttggatcaggctacttcaagatgactgatgtgtcctttgacagcgacaccttg"
                + "ggcaaaatcaagatccgcaatggaaagtctgatgcacagatgaaggaagaagatgcggatcttgtcatcactcccgt"
                + "ggagggccgagcactcgaagtgactgtggggcagaatctcacctttgagggaacattcaaggtgtggaacaacacat"
                + "caagaaagatcaacatcactggtatgcagatggtgccaaagattaacccatcaaaggcctttgtcggtagctccaac"
                + "acctcctccttcacccccgtctctattgatgaggatgaagttggcacctttgtgtgtggtaccacctttggcgcacc"
                + "aattgcagctaccgccggtggaaatcttttcgacatgtacgtgcacgtcacctactctggcactgagaccgagtaaa"
                + "taaatcgtgcttttttatatagatagggaattttaatattacaacaataagaaaataaaacaattgaggaaatttat"
                + "accatattttattgacctacttaaccttcttgctatacaatgaatgtttaggtgactggaaaagtttagcaatatta"
                + "tccttgaacgggaaacatgcaccaattacaggcgcaatttcatacgctctcggcctattggtcttttcctggtcata"
                + "cattttagatacaatagacaaaaatggaatgtttgtatagatagaattggcagacaaatctgcagttctcttaatca"
                + "aaatggacaacatgtctattaacaaataagccaacccaaaagtcatggcagtttctgaacacaactcactgttaata"
                + "aattcaggagctgtatgaggatggttactaaagaacctctcatcagttccccaacatttaaaattgtagtacttttt"
                + "acatggtacaattaaaccaaaatcaatcatcttaggttgaccagttattccatcaattactatattgtcacttttta"
                + "tgtccggattcactaatccttgttgagacaaccgagtgacaatatttacaatttctaccaaaacaaagggcaattgg"
                + "tttaacattctctccctcatttttccaacgatagctatggctgaaattgaatccgtaatgggtttcttgcatttaga"
                + "ttgtagaccttcaggcaggcggccagtagcttgaagcatcctaacaccgtacagagtatccctcatcctactaggtt"
                + "gctgtacaacattctcgTCTCGGATCAGATCGAGCCATTGCTGGTTTCTTCCACAGTGGTACTTTCCATTAGAACTA"
                + "TCACCGGGTGGAAACTAGCAGTGGCTCGATCTTTTCCtccattttacatttcaggtctccatttaatacatcaacgc"
                + "taccagaaatttactggaga";
        testSeqDNAtwo = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgat";
    }

    @Test
    public void createSequenceDna1() throws Exception {
        Sequence sequence = SequenceFactory.createSequence(testSeqDNAone);
        System.out.println("created sequence = " + sequence);
        assertEquals(SequenceType.DNA, sequence.getSequenceType());
    }
    @Test
    public void createSequenceDna2() throws Exception {
        Sequence sequence = SequenceFactory.createSequence(testSeqDNAtwo);
        sequence.setSequenceName("New Sequence");
        System.out.println(sequence);
        assertEquals(SequenceType.DNA, sequence.getSequenceType());
    }

    @Test
    public void createSequenceDna3() throws Exception {
        Sequence sequence = SequenceFactory.createSequence(testSeqDNAone, SequenceType.DNA);
        sequence.setSequenceName("New Sequence");
        System.out.println(sequence);
        assertEquals(SequenceType.DNA, sequence.getSequenceType());
    }

    @Test
    public void createSubSequence1() throws Exception {
        Sequence sequence = SequenceFactory.createSequence(testSeqDNAone);
        sequence.setSequenceName("Mighty big sequence");
        Sequence subSeq = sequence.getSubSequence(0, 10);
        System.out.println(subSeq);
        String expected = "atggatcttt".toUpperCase();
        assertEquals(expected, subSeq.getSequenceString());

        Sequence subSeq2 = sequence.getSubSequence(new SequenceCoordinates(0, 10));
        System.out.println(subSeq2);

    }

    @Test
    public void createSubSequenceArrayBoundsSafe() throws Exception {
        Sequence sequence = SequenceFactory.createSequence(testSeqDNAone);
        Sequence subSeq = sequence.getSubSequenceArrayBoundsSafe(-1, 10);
        System.out.println("created subsequence = " + subSeq);
        String expected = "atggatcttt".toUpperCase();
        assertEquals(expected, subSeq.getSequenceString());
    }

}