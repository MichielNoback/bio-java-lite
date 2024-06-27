package net.cellingo.sequence_tools.gene_analysis;

import java.io.Serializable;

/**
 * this class encapsulates the options used in gene analysis/ORF finding
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class GeneAnalysisOptions extends SequenceAnalysisOptions implements Serializable {
    protected static GeneAnalysisOptions DEFAULTS;

    static {
        GeneAnalysisOptions.DEFAULTS = new GeneAnalysisOptions();
        DEFAULTS.setCodonTableNumber(1);
        DEFAULTS.setMinimumOrfSize(100);
        DEFAULTS.setOrfDefinition(OrfDefinition.ATG_TO_STOP);
        DEFAULTS.setRbsSequence("TCTTTCCTCCACTAG");
    }
    /**
     *
     */
    private static final long serialVersionUID = -4915748796655883790L;
    //codon table standard constant
    public static final int STANDARD_CODON_TABLE = 1;
    //ORF definition: 0=ATG start to stop (default); 1=stop to stop; 2=all starts to stop
    private OrfDefinition orfDefinition;
    //one of the codon tables
    private int codonTableNumber;
    //minimum ORF size
    private int minimumOrfSize;
    //1- or 3-letter representation
    //5'UTR size
    private int utr5Size;
    //3'UTR size
    private int utr3Size;
    //codon table
    private CodonTable codonTable;
    //RBS sequence
    private String rbsSequence = "";

    public GeneAnalysisOptions() {
        super();
        copyDefaults();
        codonTable = new CodonTable(codonTableNumber);
    }

    private void copyDefaults() {
        if(DEFAULTS == null) return;
        this.setCodonTableNumber(DEFAULTS.getCodonTableNumber());
        this.setRbsSequence(DEFAULTS.getRbsSequence());
        this.setOrfDefinition(DEFAULTS.getOrfDefinition());
        this.setMinimumOrfSize(DEFAULTS.getMinimumOrfSize());
    }

    public static GeneAnalysisOptions getDefaults() {
        return DEFAULTS;
    }

    /**
     * specify which codon table should be used; codon table 1 is the default
     *
     * @param codonTableNumber
     */
    public void setCodonTableNumber(int codonTableNumber) {
        this.codonTableNumber = codonTableNumber;
        this.codonTable = new CodonTable(codonTableNumber);
    }

    /**
     * get the current codon table number
     *
     * @return codonTableNumber
     */
    public int getCodonTableNumber() {
        return codonTableNumber;
    }

    /**
     * get the codon table object
     *
     * @return codonTable
     */
    public CodonTable getCodonTable() {
        return codonTable;
    }

    /**
     * set the minimum ORF size; default is 100
     *
     * @param minimumOrfSize
     */
    public void setMinimumOrfSize(int minimumOrfSize) {
        this.minimumOrfSize = minimumOrfSize;
    }

    /**
     * get the minimum ORF size
     *
     * @return minimumOrfSize
     */
    public int getMinimumOrfSize() {
        return minimumOrfSize;
    }

    /**
     * get the used ORF definition
     *
     * @return orfDefinition
     */
    public OrfDefinition getOrfDefinition() {
        return orfDefinition;
    }

    /**
     * set the ORF definition
     *
     * @param definition
     */
    public void setOrfDefinition(OrfDefinition definition) {
        orfDefinition = definition;
    }

    /**
     * set the size of the 5'-UTR region to save. Default is 0
     *
     * @param utrSize
     */
    public void setUtr5Size(int utrSize) {
        utr5Size = utrSize;
    }

    /**
     * get the size of the 5'-UTR region to save. Default is 0.
     *
     * @return utr5size
     */
    public int getUtr5Size() {
        return utr5Size;
    }

    /**
     * set the size of the 3'-UTR region to save. Default is 0.
     *
     * @param utrSize
     */
    public void setUtr3Size(int utrSize) {
        utr3Size = utrSize;
    }

    /**
     * get the size of the 3'-UTR region to save. Default is 0.
     *
     * @return utr3size
     */
    public int getUtr3Size() {
        return utr3Size;
    }

    /**
     * get the RBS sequence used in the analysis
     *
     * @return rbsSequence
     */
    public String getRbsSequence() {
        return rbsSequence;
    }

    /**
     * set the RBS sequence used for the gene analysis
     *
     * @param rbsSequence
     */
    public void setRbsSequence(String rbsSequence) {
        this.rbsSequence = rbsSequence;
    }

    @Override
    public String toString() {
        return "GeneAnalysisOptions{" +
                "orfDefinition=" + orfDefinition +
                ", codonTableNumber=" + codonTableNumber +
                ", minimumOrfSize=" + minimumOrfSize +
                ", utr5Size=" + utr5Size +
                ", utr3Size=" + utr3Size +
                ", codonTable=" + codonTable +
                ", start=" + super.getStartAnalysisPosition() +
                ", stop=" + super.getStopAnalysisPosition() +
                ", strand selection=" + super.getStrandSelection() +
                ", rbsSequence='" + rbsSequence + '\'' +
                '}';
    }
}
