package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.Attributes;
import net.cellingo.sequence_tools.sequences.Sequence;
import net.cellingo.sequence_tools.sequences.SequenceCreationException;
import net.cellingo.sequence_tools.sequences.SequenceFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Creation date: Jul 11, 2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
class FastaReader implements SequenceReaderDelegate {

    private final File inputFile;
    private final int startReadingAtLine;
    private SequenceReaderListener listener;
    private boolean parseFastaHeader;

    public FastaReader(File inputFile) {
        this.inputFile = inputFile;
        startReadingAtLine = 0;
    }

    public FastaReader(File inputFile, int startReadingAtLine) {
        this.inputFile = inputFile;
        this.startReadingAtLine = startReadingAtLine;
    }

    @Override
    public void readSequences() throws SequenceCreationException {
        try {
            readMultiFasta();
        } catch (UnknownSequenceFormatException e) {
            throw new SequenceCreationException(e);
        }
    }

    @Override
    public void setListener(SequenceReaderListener listener) {
        this.listener = listener;
    }

    /**
     * Read a file of known multifasta content and return a list of plain Sequence objects.
     * If there are listeners added to this reader, the list of sequences returned will be empty!
     *
     * @return sequenceList
     * @throws net.cellingo.sequence_tools.io.UnknownSequenceFormatException   ex
     * @throws net.cellingo.sequence_tools.sequences.SequenceCreationException ex
     */
    private void readMultiFasta() throws UnknownSequenceFormatException, SequenceCreationException {
        int lineNumber = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            /*Fasta type sequences*/
            StringBuilder sequence = new StringBuilder();
            String line = null;
            String description = "";

            OUTER:
            while ((line = reader.readLine()) != null) {
                lineNumber++;

                INNER:
                while ((lineNumber - 1) < this.startReadingAtLine) {
                    String subs = line.length() > 10 ? line.substring(0, 10) : line;
                    continue OUTER;
                }
                if (line.startsWith(">")) {
                    //new FASTA sequence starts
                    //process the sequence of lastFasta
                    if (lineNumber > 1 && sequence.length() > 0) {
                        processNewSequence(sequence, description);
                    }
                    description = line;
                    sequence = new StringBuilder();
                } else {
                    sequence.append(line.toUpperCase());
                }
            }
            //process last sequence in file
            processNewSequence(sequence, description);
        } catch (IOException e) {
            throw new SequenceCreationException("ERROR! sequence file could not be read", e);
        }
    }

    private void processNewSequence(StringBuilder sequence, String description) throws SequenceCreationException {
        Sequence seq = SequenceFactory.createSequence(sequence);
        if (this.parseFastaHeader) {
            Attributes a = Attributes.fromFastaDescriptionLine(description);
            seq.setSequenceName(a.getFirstAttributeOfType("Name"));
            seq.setAttributes(a);
            seq.setAccessionNumber(a.getFirstAttributeOfType("ID"));
        } else {
            seq.setSequenceName(description.substring(1));
        }
        this.listener.sequenceRead(seq);
    }

    public void setParseFastaHeader(boolean parseFastaHeader) {
        this.parseFastaHeader = parseFastaHeader;
    }
}
