package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.*;
import net.cellingo.sequence_tools.sequences.DnaSequence;
import net.cellingo.sequence_tools.sequences.Sequence;
import net.cellingo.sequence_tools.sequences.SequenceCreationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple implementation for reading and parsing GFF version 3 sequence annotation format
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 * @see <a href="http://gmod.org/wiki/GFF3">GFF3 format</a>
 * Creation date: Jul 11, 2017
 */
class GffReader implements SequenceReaderDelegate {
    public static final int SEQID = 0;
    public static final int SOURCE = 1;
    public static final int FEATURE_TYPE = 2;
    public static final int START = 3;
    public static final int END = 4;
    public static final int SCORE = 5;
    public static final int STRAND = 6;
    public static final int FRAME = 7;
    public static final int ATTRIBUTES = 8;
    public static final int COMMENTS = 9;


    private final File[] inputFiles;
    private SequenceReaderListener listener;
    private String currentLine;
    private Map<String, AnnotatedSequence> gffSequenceEntries;
    private int currentLineNumber;
    private boolean fastaSequencesPresent;

    /**
     * multiple inputfiles accepted. If more than one, the first is assumed to be the GFF and
     * the others the Fasta raw sequences.
     *
     * @param inputFiles
     */
    public GffReader(File[] inputFiles) {
        this.inputFiles = inputFiles;
    }

    @Override
    public void setListener(SequenceReaderListener listener) {
        this.listener = listener;
    }

    @Override
    public void readSequences() throws SequenceCreationException {
        gffSequenceEntries = new HashMap<>();
        try {
            if (this.inputFiles.length == 1) {
                readSingleGff(inputFiles[0]);
            } else if (this.inputFiles.length == 2) {
                fastaSequencesPresent = true;
                readFastaSequences(inputFiles[1], 0);
                readSingleGff(inputFiles[0]);
            } else {
                throw new UnsupportedOperationException("not implemented yet");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new SequenceCreationException("Error parsing GFF file at line " + currentLineNumber + ": " + currentLine);
        }
    }

    /**
     * parses a given gff formatted file and returns a HasMap of sequence
     * elements
     *
     * @return sequenceElements
     * @throws NumberFormatException
     * @throws IOException
     */
    private void readSingleGff(File gffFile) throws NumberFormatException, IOException {
        /**
         * <seqid> <source; "." if absent> <feature/type; required> <start (one-based)> <end (one-based)> <score; "." if absent> <strand: +/-/.>  <phase (frame); 0/1/2/.> [attributes]
         * Possible attributes, KEY=VALUE;KEY=VALUE, where multiple values are comma-separated
         * ID, Name, Alias. Parent, Target, Gap (CIGAR format), Derives_from, Note, Dbxref, Ontology_term
         *
         * PGSC0003DMB000000001	GLEAN	mRNA	1886254	1888720	.	+	.	ID=PGSC0003DMT400019244;Parent=PGSC0003DMG400007442;Source_id=PGSC0003DMG000009445;Class=2;
         *
         * AT THE END POSSIBLE FASTA SEQUENCE:
         * ##FASTA
         * >ctg123
         * cttctgggcgtacccgattctcggagaacttgccgcaccattccgccttg
         *
         */
        if (!fastaSequencesPresent) {
            findAndProcessFastaSequences(gffFile);
        }

        BufferedReader br = new BufferedReader(new FileReader(gffFile));
        while ((currentLine = br.readLine()) != null) {
            currentLineNumber++;
            if (currentLine.startsWith("##FASTA") || currentLine.startsWith("##Fasta") || currentLine.startsWith("##fasta")) {
                //end of features section
                break;
            }
            /*ignore all comment lines for now*/
            if (currentLine.startsWith("##") || currentLine.startsWith("#!")) {
                continue;
            }

            String[] elmnts = currentLine.split("\t");

            String sourceSequenceID = elmnts[SEQID];

            //A new sequence is present, without an actual fasta sequence to represent it
            if (!gffSequenceEntries.containsKey(sourceSequenceID) && !this.fastaSequencesPresent) {
                AnnotatedSequence gffSequenceEntry = new AnnotatedSequence(SequenceObjectType.GFF3_SEQUENCE);
                Sequence dummy = new DnaSequence("");
                gffSequenceEntry.setSequence(dummy);
                gffSequenceEntry.addAnnotation("ID", sourceSequenceID);
                this.gffSequenceEntries.put(sourceSequenceID, gffSequenceEntry);
            }
            processFeature(elmnts);
        }

        notifyObservers();
    }

    private void notifyObservers() {
        for (AnnotatedSequence seq : this.gffSequenceEntries.values()) {
            listener.sequenceRead(seq);
        }
        listener.sequenceReadingFinished();
    }

    private void findAndProcessFastaSequences(File gffFile) throws IOException {
        String line;
        int lineNumber = 0;
        BufferedReader br = new BufferedReader(new FileReader(gffFile));
        while ((line = br.readLine()) != null) {
            lineNumber++;
            if (line.startsWith("##FASTA") || line.startsWith("##Fasta") || line.startsWith("##fasta")) {
                this.fastaSequencesPresent = true;
                readFastaSequences(gffFile, lineNumber);
            }
        }
        this.fastaSequencesPresent = false;
    }

    private void readFastaSequences(File gffFile, int startReadingAtLine) {
        FastaReader fReader = new FastaReader(gffFile, startReadingAtLine);
        fReader.setListener(new SequenceReaderListener() {
            @Override
            public void sequenceRead(SequenceObject sequenceObject) {
                addFastaSequence(sequenceObject);
            }

            @Override
            public void sequenceReadingFinished() {
                //pass
            }
        });
        try {
            fReader.readSequences();
        } catch (SequenceCreationException e) {
            java.util.logging.Logger.getLogger("GffReader").log(
                    Level.SEVERE,
                    "SequenceCreation error in fastaReader with file " + gffFile + "; message=" + e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    private void addFastaSequence(SequenceObject sequenceObject) {
        //System.out.println("sequenceObject = " + sequenceObject);
        String name = sequenceObject.getSequenceName();
        String seqId = name.substring(0, name.indexOf(' '));

        if (!gffSequenceEntries.containsKey(seqId)) {
            AnnotatedSequence gffSequenceEntry = new AnnotatedSequence(SequenceObjectType.GFF3_SEQUENCE);
            gffSequenceEntry.setSequence((Sequence) sequenceObject);
            gffSequenceEntry.addAnnotation("ID", seqId);
            this.gffSequenceEntries.put(seqId, gffSequenceEntry);
        } else {
            throw new IllegalStateException("Duplicate sequence ID in GFF collection: " + seqId);
        }
    }

    private void processFeature(String[] elmnts) {
        String featureSource = elmnts[SOURCE];
        String feature = elmnts[FEATURE_TYPE];
        SequenceElementType type = SequenceElementType.getType(feature);

        double score = (elmnts[SCORE].equals(".") ? 0.0 : Double.parseDouble(elmnts[SCORE]));

        SequenceCoordinates coordinates = extractCoordinates(elmnts);

        short frame = (elmnts[FRAME].equals(".") ? -1 : Short.parseShort(elmnts[FRAME]));

        String attributes = elmnts[ATTRIBUTES];
        List<AttributeEntry> splitAttributes = splitAttributes(attributes, currentLineNumber);

        String id = extractId(splitAttributes);

        NucleicAcidSequenceElement sequenceElement = new NucleicAcidSequenceElement(id);
        sequenceElement.addAttribute("featureSource", featureSource);
        sequenceElement.addCoordinates(coordinates);
        sequenceElement.setSequenceElementType(type);
        sequenceElement.setScore(score);

        processGffAttributes(sequenceElement, splitAttributes);

        if (sequenceElement.getParentSequenceElementIds() == null ||
                sequenceElement.getParentSequenceElementIds().size() == 0) {
            sequenceElement.addParentSequenceElementId(elmnts[SEQID]);
        }

        if (elmnts.length == 10) {
            //supports non-gff3 format comments
            sequenceElement.addAttribute("comment", elmnts[COMMENTS]);
        }

        this.gffSequenceEntries.get(elmnts[SEQID]).addElement(sequenceElement);
    }

    private String extractId(List<AttributeEntry> splitAttributes) {
        //assume first entry in list is ID, otherwise search it in entire list
        String id = null;
        AttributeEntry firstEntry = splitAttributes.get(0);
        if (firstEntry.getKey().equals("ID")) {
            id = firstEntry.getValue();
        } else {
            for (AttributeEntry entry : splitAttributes) {
                if (entry.getKey().equals("ID")) {
                    id = entry.getValue();
                }
            }
            if (id == null) {
                Logger.getLogger("GffReader").log(Level.WARNING, "line " + currentLineNumber + " seems to have a feature without ID");
                id = SequenceElement.getNextAutogeneratedId();
            }
        }
        return id;
    }

    private SequenceCoordinates extractCoordinates(String[] elmnts) {
        int start = Integer.parseInt(elmnts[START]);
        int end = Integer.parseInt(elmnts[END]);
        boolean forwardStrand = true;
        //defaults to true for other characters such as "."
        if (elmnts[STRAND].equals("-")) forwardStrand = false;
        return new SequenceCoordinates(start, end, forwardStrand);
    }

    private void processGffAttributes(NucleicAcidSequenceElement sequenceElement, List<AttributeEntry> splitAttributes) {
        /* Possible attributes, KEY=VALUE;KEY=VALUE, where multiple values are comma-separated
         * ID, Name, Alias. Parent, Target, Gap (CIGAR format), Derives_from, Note, Dbxref, Ontology_term
         * ID=cds2;Parent=gene2;Dbxref=Genbank:WP_058986868.1;Name=WP_058986868.1;gbkey=CDS;inference=COORDINATES: similar to AA sequence:RefSeq:WP_007397838.1;product=transposase;protein_id=WP_058986868.1;transl_table=11
         */

        for (AttributeEntry entry : splitAttributes) {
            //System.out.print("entry = " + entry);
            String value = entry.getValue();
            if (entry.getKey().equals("ID")) {
                //already dealt with
                continue;
            }
            String[] values = value.split(",");

            //special action on Parent IDs,
            if (entry.getKey().equals("Parent")) {
                for (String v : values) {
                    sequenceElement.addParentSequenceElementId(value);
                }
            }
            //but register the Parent as attribute anyway
            for (String v : values) {
                sequenceElement.addAttribute(entry.getKey(), value);
            }
        }
    }

    private List<AttributeEntry> splitAttributes(String attributes, int lineNumber) {
        List<AttributeEntry> attributesList = new ArrayList<>();
        String[] elmnts = attributes.split(";");
        for (String elmnt : elmnts) {
            String[] keyVal = elmnt.split("=");
            if (keyVal.length != 2) {
                System.err.println("Ignoring invalid formatted attributes at line " + lineNumber);
                System.err.println("attributes=" + attributes);
            }
            attributesList.add(new AttributeEntry(keyVal[0], keyVal[1]));
        }
        return attributesList;
    }

    private static class AttributeEntry {
        private final String key;
        private final String value;

        public AttributeEntry(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "AttributeEntry{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
