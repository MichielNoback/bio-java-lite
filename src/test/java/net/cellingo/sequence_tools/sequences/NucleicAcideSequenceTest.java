package net.cellingo.sequence_tools.sequences;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Creation date: 8-7-2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public class NucleicAcideSequenceTest {
    private NucleicAcidSequence dnaSeq;
    private NucleicAcidSequence rnaSeq;

    @Before
    public void setUp() {
        String testSeqDNA = "atggatctttcttaaa".toUpperCase();
        String testSeqRNA = "auggaucuuucuuaaaaa".toUpperCase();
        dnaSeq = new DnaSequence(testSeqDNA);
        rnaSeq = new RnaSequence(testSeqRNA);
    }

    @Test
    public void testComplementingDna() {
        NucleicAcidSequence compl = dnaSeq.complement();
        assertEquals("TACCTAGAAAGAATTT", compl.getSequenceString());

        //original sequence should be unchanged
        assertEquals("atggatctttcttaaa".toUpperCase(), dnaSeq.getSequenceString());

        DnaSequence ambiguousDna = new DnaSequence("ARWYGCTAN");
        DnaSequence ambCompl = (DnaSequence)ambiguousDna.complement();
        assertEquals("TYWRCGATN", ambCompl.getSequenceString());
    }

    @Test
    public void testReverseComplementingDna() {
        System.out.println("dnaSeq = " + dnaSeq);
        NucleicAcidSequence revCompl = dnaSeq.reverseComplement();
        System.out.println("revCompl = " + revCompl);
        assertEquals("TTTAAGAAAGATCCAT", revCompl.getSequenceString());

        DnaSequence ambiguousDna = new DnaSequence("ARWYGCTAN");
        System.out.println("ambiguousDna = " + ambiguousDna);
        DnaSequence ambRevCompl = (DnaSequence)ambiguousDna.reverseComplement();
        System.out.println("ambRevCompl = " + ambRevCompl);
        assertEquals("NTAGCRWYT", ambRevCompl.getSequenceString());
    }

}
