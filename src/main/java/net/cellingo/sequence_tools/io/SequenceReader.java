package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.SequenceObject;
import net.cellingo.sequence_tools.annotation.SequenceObjectType;
import net.cellingo.sequence_tools.sequences.SequenceCreationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class is the main point of entry for all sequence IO. It determines the sequence format based on the first line
 * in the file
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceReader implements SequenceReaderListener {
    private File[] inputFiles;
    private SequenceObjectType sequenceObjectType;
    private SequenceReaderDelegate sequenceReaderDelegate;
    private ArrayList<SequenceObject> sequenceList;
    private ArrayList<SequenceReaderListener> listeners;
    private boolean parseFastaHeader;

    /**
     * construct with input file containing sequence(s).
     *
     * @param inputFiles input files
     */
    public SequenceReader(final File... inputFiles) {
        this.inputFiles = inputFiles;
    }

    public SequenceReader (SequenceObjectType type, final File... inputFiles) {
        this.inputFiles = inputFiles;
        this.sequenceObjectType = type;
    }

    /**
     * read the file with this method if it contains an unknown file format.
     *
     * @throws UnknownSequenceFormatException ex
     * @throws SequenceCreationException      ex
     */
    public void read() throws UnknownSequenceFormatException, SequenceCreationException {
        if(this.inputFiles == null || this.inputFiles.length == 0) {
            throw new SequenceCreationException("No files provided");
        }
        sequenceList = new ArrayList<SequenceObject>();
        if (sequenceObjectType == null) {
            checkSequenceFormat();
        }
        readSequences();
    }

    /**
     * Have a peek for the possible sequence format
     *
     * @throws UnknownSequenceFormatException ex
     */
    private void checkSequenceFormat() throws UnknownSequenceFormatException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFiles[0]));
            String firstLine = reader.readLine();
            if (firstLine.startsWith(">")) {
                sequenceObjectType = SequenceObjectType.SIMPLE_SEQUENCE;
            } else if (firstLine.startsWith("LOCUS")) {
                sequenceObjectType = SequenceObjectType.GENBANK_SEQUENCE;
            } else if (firstLine.contains("##gff-version 3")) {
                sequenceObjectType = SequenceObjectType.GFF3_SEQUENCE;
            }
            else {
                throw new UnknownSequenceFormatException("error! unknown sequence format");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a listener to this reader. If this object has any listener, it will parse sequences in a streaming fashion
     * and not batch-wise.
     *
     * @param listener the listener
     */
    public void addSequenceReaderListener(final SequenceReaderListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<SequenceReaderListener>();
        }
        listeners.add(listener);
    }

    public void setParseFastaHeader(boolean parseFastaHeader) {
        this.parseFastaHeader = parseFastaHeader;
    }

    /**
     * read the sequences.
     *
     * @throws SequenceCreationException ex
     */
    private void readSequences() throws SequenceCreationException {
        if (sequenceObjectType == SequenceObjectType.SIMPLE_SEQUENCE) {
            FastaReader fReader = new FastaReader(inputFiles[0]);
            fReader.setParseFastaHeader(this.parseFastaHeader);
            this.sequenceReaderDelegate = fReader;
        } else if (sequenceObjectType == SequenceObjectType.GENBANK_SEQUENCE) {
            this.sequenceReaderDelegate = new GenbankReader(inputFiles[0]);
        } else if (sequenceObjectType == SequenceObjectType.GFF3_SEQUENCE) {
            this.sequenceReaderDelegate = new GffReader(inputFiles);
        }
        else {
            throw new UnsupportedOperationException("this sequence format is not yet supported");
        }
        sequenceReaderDelegate.setListener(this);
        sequenceReaderDelegate.readSequences();

    }

    /**
     * When reading of other formats besides Fasta, this process gets delegated to another reader. When a sequence is
     * read entirely, it calls this method when a sequence.
     *
     * @param sequenceObject the seq object
     */
    public void sequenceRead(final SequenceObject sequenceObject) {
        if (listeners != null) {
            for (SequenceReaderListener listener : listeners) {
                listener.sequenceRead(sequenceObject);
            }
        } else {
            sequenceList.add(sequenceObject);
        }
    }

    /**
     * closing off.
     */
    public void sequenceReadingFinished() {
        if (listeners != null) {
            for (SequenceReaderListener listener : listeners) {
                listener.sequenceReadingFinished();
            }
        }
    }

    /**
     * get the sequences read from file as ArrayList.
     *
     * @return list of sequences
     */
    public ArrayList<SequenceObject> getSequenceList() {
        return sequenceList;
    }

    /**
     * Returns the object type of the sequences that have just been read
     * @return the sequenceObjectType
     */
    public SequenceObjectType getSequenceObjectType() {
        return sequenceObjectType;
    }

}
