package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.*;
import net.cellingo.sequence_tools.sequences.DnaSequence;
import net.cellingo.sequence_tools.sequences.ProteinSequence;
import net.cellingo.sequence_tools.sequences.Sequence;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creation date: Jul 10, 2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public class SequenceReaderTest {

    @Test
    public void readMultiFastaProtein() throws Exception {
        File fastaFile = new File("sample_data/fhit_sample.faa");
        assertTrue(fastaFile.exists() && fastaFile.canRead());
        SequenceReader reader = new SequenceReader(fastaFile);
        reader.read();
        final ArrayList<SequenceObject> sequenceObjects = reader.getSequenceList();
//        for (SequenceObject seq : sequenceObjects) {
//            //since Fasta has been read, a direct cast to sequence is possible
//            System.out.println((Sequence)seq);
//        }
        assertEquals(5, sequenceObjects.size());
        assertEquals("data should be simple sequence", ProteinSequence.class, sequenceObjects.get(0).getClass());
        assertEquals("MSFRFGQHLIKPSVVFLKTELSFALVNRKPVVPGH", ((Sequence)sequenceObjects.get(0)).getSequenceString());
    }

    @Test
    public void readMultiFastaProteinAndParseFasteHeader() throws Exception {
        File fastaFile = new File("sample_data/fhit_sample.faa");
        assertTrue(fastaFile.exists() && fastaFile.canRead());
        SequenceReader reader = new SequenceReader(fastaFile);
        reader.setParseFastaHeader(true);
        reader.read();
        final ArrayList<SequenceObject> sequenceObjects = reader.getSequenceList();
        assertEquals(5, sequenceObjects.size());
        assertEquals("data should be simple sequence", ProteinSequence.class, sequenceObjects.get(0).getClass());
        assertEquals("FHIT protein(fragment)", sequenceObjects.get(0).getSequenceName());
        assertEquals("gi|21595364", sequenceObjects.get(0).getAccessionNumber());
        assertEquals("Homo sapiens", sequenceObjects.get(0).getAttributes().getFirstAttributeOfType("Organism"));
        assertTrue(sequenceObjects.get(0).getAttributes().getAttributesOfType("ID").contains("gb|AAH32336.1"));
        assertTrue(sequenceObjects.get(0).getAttributes().getAttributesOfType("ID").contains("gi|21595364"));
        assertEquals("MSFRFGQHLIKPSVVFLKTELSFALVNRKPVVPGH", ((Sequence)sequenceObjects.get(0)).getSequenceString());
    }


    @Test
    public void readMultiFastaDna() throws Exception {
        File fastaFile = new File("sample_data/dna_sequences_4.fa");
        assertTrue(fastaFile.exists() && fastaFile.canRead());
        SequenceReader reader = new SequenceReader(fastaFile);
        //final ArrayList<SequenceObject> sequenceObjects = reader.readMultiFasta();
        //or, alternatively, use dynamic type determination
        reader.read();
        final ArrayList<SequenceObject> sequenceObjects = reader.getSequenceList();
        assertEquals(4, sequenceObjects.size());
        assertEquals("data should be simple sequence", DnaSequence.class, sequenceObjects.get(0).getClass());
        assertEquals("CATCATGAAATCGCTTGTCGCACTACTGCTGCTTTTAGTCGCTACTTCTGCCTTTGCTGACCAGTATGTAAATGGCTACA" +
                "CTAGAAAAGACGGAACTTATGTCAACGGCTATAC", ((Sequence)sequenceObjects.get(0)).getSequenceString());

    }

    @Test
    @Ignore
    public void readGenBankSimple() throws Exception {
        File genbankFile = new File("sample_data/example_genbank_file.gb");
        assertTrue(genbankFile.exists() && genbankFile.canRead());
        SequenceReader reader = new SequenceReader(genbankFile);
        reader.read();
        //I know there is only one GenBank entry in the file
        GenbankEntry gbEntry = (GenbankEntry)(reader.getSequenceList().get(0));
        System.out.println("GB Entry Sequence = " + gbEntry.getSequence());
        //Iterate the features
        for (SequenceElementType type : gbEntry.getSequenceElementTypesList()) {
            for (SequenceElement element : gbEntry.getElementList(type)) {
                System.out.println("TYPE=" + type + "; CLASS=" + element.getClass().getSimpleName() + " ELEMENT=" + element);
            }
        }
    }

    @Test
    public void readGenBankBig() throws Exception {
        File genbankFile = new File("sample_data/Haloarcula_marismortui_genome.gb");
        assertTrue(genbankFile.exists() && genbankFile.canRead());
        SequenceReader reader = new SequenceReader(SequenceObjectType.GENBANK_SEQUENCE, genbankFile);
        reader.read();
        //I know there is only one GenBank entry in the file
        GenbankEntry gbEntry = (GenbankEntry)(reader.getSequenceList().get(0));
        System.out.println("GB Sequence entry:\n" + gbEntry);
        System.out.println("\nnumber of genes = " + gbEntry.getElementList(SequenceElementType.GENE).size());
        System.out.println("\nnumber of CDSs = " + gbEntry.getElementList(SequenceElementType.CDS).size());
        System.out.println("\nnumber of rRNAs = " + gbEntry.getElementList(SequenceElementType.RRNA).size());
        assertEquals(285, gbEntry.getElementList(SequenceElementType.GENE).size());
        assertEquals(281, gbEntry.getElementList(SequenceElementType.CDS).size());
        assertEquals(3, gbEntry.getElementList(SequenceElementType.RRNA).size());

    }

    @Test
    @Ignore
    public void readMultiFastaDnaStreaming() throws Exception {
        File fastaFile = new File("sample_data/dna_sequences_4.fa");
        assertTrue(fastaFile.exists() && fastaFile.canRead());
        SequenceReader reader = new SequenceReader(fastaFile);
        reader.addSequenceReaderListener(new SequenceReaderListener() {
            @Override
            public void sequenceRead(SequenceObject sequenceObject) {
                System.out.println("sequence = " + (Sequence)sequenceObject);
            }
            @Override
            public void sequenceReadingFinished() {
                System.out.println("finished");
            }
        });
        reader.read();
    }

    @Test
    @Ignore
    public void getSequenceObjectType() throws Exception {
        File fastaFile = new File("sample_data/dna_sequences_4.fa");
        SequenceReader reader = new SequenceReader(fastaFile);
        reader.read();
        System.out.println(reader.getSequenceObjectType());
    }

    @Test
    public void readGff3SmallWithFasta() throws Exception {
        File gffFile = new File("sample_data/acetobacter_fragment_with_fasta.gff");
        SequenceReader reader = new SequenceReader(SequenceObjectType.GFF3_SEQUENCE, gffFile);
        reader.addSequenceReaderListener(new SequenceReaderListener() {
            @Override
            public void sequenceRead(SequenceObject sequenceObject) {
                System.out.println("TESTER: sequence = " + sequenceObject);
                AnnotatedSequence seq = ((AnnotatedSequence)sequenceObject);
                for (SequenceElementType type : seq.getSequenceElementTypesList()) {
                    for(SequenceElement element : seq.getElementList(type)) {
                        System.out.println("element = " + element);
                    }
                }

            }

            @Override
            public void sequenceReadingFinished() {
                System.out.println("finished");
            }
        });
        reader.read();
    }

    @Test
    public void readGff3SmallWithoutFasta() throws Exception {
        File gffFile = new File("sample_data/acetobacter_fragment_without_fasta.gff");
        SequenceReader reader = new SequenceReader(SequenceObjectType.GFF3_SEQUENCE, gffFile);
        reader.addSequenceReaderListener(new SequenceReaderListener() {
            @Override
            public void sequenceRead(SequenceObject sequenceObject) {
                System.out.println("TESTER: sequence = " + sequenceObject);
                AnnotatedSequence seq = ((AnnotatedSequence)sequenceObject);
                for (SequenceElementType type : seq.getSequenceElementTypesList()) {
                    for(SequenceElement element : seq.getElementList(type)) {
                        System.out.println("element = " + element);
                    }
                }
            }

            @Override
            public void sequenceReadingFinished() {
                System.out.println("TESTER finished");
            }
        });
        reader.read();

    }

    @Test
    public void readGff3SmallWithExternalFasta() throws Exception {
        File gffFile = new File("sample_data/acetobacter_fragment_without_fasta.gff");
        File fastaFile = new File("sample_data/acetobacter_fragment_separate_fasta.fna");
        SequenceReader reader = new SequenceReader(SequenceObjectType.GFF3_SEQUENCE, gffFile, fastaFile);
        reader.addSequenceReaderListener(new SequenceReaderListener() {
            @Override
            public void sequenceRead(SequenceObject sequenceObject) {
                System.out.println("TESTER: sequence = " + sequenceObject);
            }

            @Override
            public void sequenceReadingFinished() {
                System.out.println("TESTER finished");
            }
        });
        reader.read();

    }

}