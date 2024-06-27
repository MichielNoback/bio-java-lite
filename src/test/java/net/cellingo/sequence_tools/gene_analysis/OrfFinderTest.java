package net.cellingo.sequence_tools.gene_analysis;

import net.cellingo.sequence_tools.annotation.OpenReadingFrame;
import net.cellingo.sequence_tools.sequences.DnaSequence;
import net.cellingo.sequence_tools.sequences.SequenceStrand;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Creation date: 8-7-2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public class OrfFinderTest {
    private DnaSequence dnaOne;
    private DnaSequence dnaTwo;

    @Before
    public void setUp() throws Exception {
        String testSeqDNAone = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttatttaaaaagat";
        String testSeqDNAtwo = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgat"
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
                + "taccagtaaatttactggaga";

        dnaOne = new DnaSequence(testSeqDNAone.toUpperCase());
        dnaTwo = new DnaSequence(testSeqDNAtwo.toUpperCase());
    }

    @Test
    public void doOrfFindingForward() throws Exception {
        //will use codon table 1, ORFs from ATG to stop codon of mimimal length 100
        GeneAnalysisOptions geneAnalysisOptions = new GeneAnalysisOptions();
        geneAnalysisOptions.setMinimumOrfSize(5);
        geneAnalysisOptions.setStrandSelection(SequenceStrand.FORWARD);
        geneAnalysisOptions.setOrfDefinition(OrfDefinition.STOP_TO_STOP);
        OrfFinder orfFinder = new OrfFinder(dnaOne, geneAnalysisOptions);
        orfFinder.start();
        List<OpenReadingFrame> orfs = orfFinder.getOrfList();
        assertEquals("MDLSFTLSVVSAILAITAVIAVFI", orfs.get(0).getProteinSequence().getSequenceString());
        assertEquals("WIFLSLFRSCRPSSPSLL", orfs.get(1).getProteinSequence().getSequenceString());
        assertEquals("LLYLFKK", orfs.get(2).getProteinSequence().getSequenceString());
        assertEquals("GSFFHSFGRVGHPRHHCCDCCIYLKR", orfs.get(3).getProteinSequence().getSequenceString());
    }

    @Test
    public void doOrfFindingBoth() throws Exception {
        //will use codon table 1, ORFs from ATG to stop codon of mimimal length 100
        GeneAnalysisOptions geneAnalysisOptions = new GeneAnalysisOptions();
        geneAnalysisOptions.setMinimumOrfSize(5);
        geneAnalysisOptions.setStrandSelection(SequenceStrand.BOTH);
        geneAnalysisOptions.setOrfDefinition(OrfDefinition.STOP_TO_STOP);
        OrfFinder orfFinder = new OrfFinder(dnaOne, geneAnalysisOptions);
        orfFinder.start();
        List<OpenReadingFrame> orfs = orfFinder.getOrfList();
//        for (OpenReadingFrame orf : orfs) {
//            System.out.println("orf = " + orf);
//        }

        //forward
        assertEquals("MDLSFTLSVVSAILAITAVIAVFI", orfs.get(0).getProteinSequence().getSequenceString());
        assertEquals("WIFLSLFRSCRPSSPSLL", orfs.get(1).getProteinSequence().getSequenceString());
        assertEquals("LLYLFKK", orfs.get(2).getProteinSequence().getSequenceString());
        assertEquals("GSFFHSFGRVGHPRHHCCDCCIYLKR", orfs.get(3).getProteinSequence().getSequenceString());
        //reverse
        assertEquals("IFLNKYSNHSSDGEDGRHDRKSERKIH", orfs.get(4).getProteinSequence().getSequenceString());
        assertEquals("INTAITAVMARMADTTERVKERS", orfs.get(5).getProteinSequence().getSequenceString());
        assertEquals("IQQSQQ", orfs.get(6).getProteinSequence().getSequenceString());
        assertEquals("WRGWPTRPKE", orfs.get(7).getProteinSequence().getSequenceString());
    }


    @Test
    public void doSixFrameTranslation() throws Exception {
        String[] translation = OrfFinder.doSixFrameTranslation(dnaOne);

//        for (String line : translation) {
//            System.out.println(line);
//        }
        assertEquals("M  D  L  S  F  T  L  S  V  V  S  A  I  L  A  I  T  A  V  I  A  V  F  I  *  K  D  ", translation[0]);
        assertEquals(" W  I  F  L  S  L  F  R  S  C  R  P  S  S  P  S  L  L  *  L  L  Y  L  F  K  K    ", translation[1]);
        assertEquals("  G  S  F  F  H  S  F  G  R  V  G  H  P  R  H  H  C  C  D  C  C  I  Y  L  K  R   ", translation[2]);
        assertEquals("ATGGATCTTTCTTTCACTCTTTCGGTCGTGTCGGCCATCCTCGCCATCACTGCTGTGATTGCTGTATTTATTTAAAAAGAT", translation[3]);
        assertEquals("TACCTAGAAAGAAAGTGAGAAAGCCAGCACAGCCGGTAGGAGCGGTAGTGACGACACTAACGACATAAATAAATTTTTCTA", translation[4]);
        assertEquals("  H  I  K  R  E  S  K  R  D  H  R  G  D  E  G  D  S  S  H  N  S  Y  K  N  L  F  I", translation[5]);
        assertEquals("    S  R  E  K  V  R  E  T  T  D  A  M  R  A  M  V  A  T  I  A  T  N  I  *  F  S ", translation[6]);
        assertEquals("   P  D  K  K  *  E  K  P  R  T  P  W  G  R  W  *  Q  Q  S  Q  Q  I  *  K  F  L  ", translation[7]);
    }


    @Test
    public void doTranslation() throws Exception {
        StringBuilder translation = OrfFinder.doTranslation(1, dnaOne, 8, 56, "");
        System.out.println("translation = " + translation);
    }

}