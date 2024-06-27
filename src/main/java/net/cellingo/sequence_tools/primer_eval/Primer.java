package net.cellingo.sequence_tools.primer_eval;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.cellingo.sequence_tools.alignment.Alignment;
import net.cellingo.sequence_tools.alignment.AlignmentAlgorithm;
import net.cellingo.sequence_tools.alignment.AlignmentController;
import net.cellingo.sequence_tools.alignment.AlignmentMatrixType;
import net.cellingo.sequence_tools.alignment.AlignmentOptions;
import net.cellingo.sequence_tools.alignment.AlignmentProperty;
import net.cellingo.sequence_tools.alignment.AlignmentStrategy;
import net.cellingo.sequence_tools.sequences.Sequence;
import net.cellingo.sequence_tools.sequences.SequenceCreationException;
import net.cellingo.sequence_tools.sequences.SequenceFactory;
import net.cellingo.sequence_tools.sequences.SequenceType;

/**
 * this class models a PCR primer, for evaluation purposes
 *
 * @author Michiel Noback (m.a.noback@pl.hanze.nl)
 * @version 0.1
 */
public class Primer {

    private static String TESTPASS = "Pass";
    private static String TESTFAIL = "Warning";
    private static NumberFormat percentageNumberFormatter;
    private static NumberFormat numberFormatter;
    private static int maximumBaseRunLength = 5;
    private static int minimumSuitableTm = 50;
    private static int maximumSuitableTm = 60;
    private static int desiredThreePrimeEndSize = 5;
    private static final HashMap<Character, Double> terminalCorrectionsDsHash;
    private static final HashMap<Character, Double> terminalCorrectionsDhHash;
    private static final HashMap<String, Double> dsHash;
    private static final HashMap<String, Double> dhHash;
    private static double defaultSaltMolarity = 0.05;
    private String sequence;
    private String name;
    private HashMap<Character, Integer> baseCounts;

    /**
     * initialization of staic properties
     */
    static {
        //SantaLucia, J. (1998) Proc. Nat. Acad. Sci. USA 95, 1460.
        terminalCorrectionsDsHash = new HashMap<Character, Double>();
        terminalCorrectionsDsHash.put('G', -2.8);
        terminalCorrectionsDsHash.put('A', 4.1);
        terminalCorrectionsDsHash.put('T', 4.1);
        terminalCorrectionsDsHash.put('C', -2.8);

        terminalCorrectionsDhHash = new HashMap<Character, Double>();
        terminalCorrectionsDhHash.put('G', 0.1);
        terminalCorrectionsDhHash.put('A', 2.3);
        terminalCorrectionsDhHash.put('T', 2.3);
        terminalCorrectionsDhHash.put('C', 0.1);

        //SantaLucia, J. (1998) Proc. Nat. Acad. Sci. USA 95, 1460.
        dsHash = new HashMap<String, Double>();
        dsHash.put("GG", -19.9);
        dsHash.put("GA", -22.2);
        dsHash.put("GT", -22.4);
        dsHash.put("GC", -27.2);

        dsHash.put("AG", -21.0);
        dsHash.put("AA", -22.2);
        dsHash.put("AT", -20.4);
        dsHash.put("AC", -22.4);

        dsHash.put("TG", -22.7);
        dsHash.put("TA", -21.3);
        dsHash.put("TT", -22.2);
        dsHash.put("TC", -22.2);

        dsHash.put("CG", -27.2);
        dsHash.put("CA", -22.7);
        dsHash.put("CT", -21.0);
        dsHash.put("CC", -19.9);

        //SantaLucia, J. (1998) Proc. Nat. Acad. Sci. USA 95, 1460.
        dhHash = new HashMap<String, Double>();
        dhHash.put("GG", -8.0);
        dhHash.put("GA", -8.2);
        dhHash.put("GT", -8.4);
        dhHash.put("GC", -10.6);

        dhHash.put("AG", -7.8);
        dhHash.put("AA", -7.9);
        dhHash.put("AT", -7.2);
        dhHash.put("AC", -8.4);

        dhHash.put("TG", -8.5);
        dhHash.put("TA", -7.2);
        dhHash.put("TT", -7.9);
        dhHash.put("TC", -8.2);

        dhHash.put("CG", -10.6);
        dhHash.put("CA", -8.5);
        dhHash.put("CT", -7.8);
        dhHash.put("CC", -8.0);


        NumberFormat pf = NumberFormat.getPercentInstance();
        pf.setMaximumFractionDigits(1);
        Primer.percentageNumberFormatter = pf;

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        Primer.numberFormatter = nf;
    }

    /**
     * constructs with both properties
     *
     * @param sequence
     * @param name
     */
    public Primer(String sequence, String name) {
        this.sequence = sequence;
        this.name = name;
    }

    /**
     * constructs with sequence only and gives name as UNNAMED
     *
     * @param sequence
     */
    public Primer(String sequence) {
        this(sequence, "UNNAMED");
    }

    /**
     * @return the sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the length of the sequence
     *
     * @return length
     */
    public int length() {
        return this.sequence.length();
    }

    /**
     * returns a test message concerning the length of the primer
     *
     * @param minimumSuitableLength
     * @param maximumSuitableLength
     * @return test message
     */
    private String getPrimerLengthTestMessage(int minimumSuitableLength, int maximumSuitableLength) {
        if (length() < minimumSuitableLength) {
            return TESTFAIL + ": primer below suitable length of " + minimumSuitableLength;
        } else if (length() > maximumSuitableLength) {
            return TESTFAIL + ": primer above suitable length of " + maximumSuitableLength;
        } else {
            return TESTPASS;
        }
    }

    /**
     * returns a String representation of the base counts
     *
     * @return baseCounts String
     */
    public String getBaseCounts() {
        HashMap<Character, Integer> counts = getBaseCountsMap();
        return "G=" + counts.get('G') + "; A=" + counts.get('A') + "; T=" + counts.get('T') + "; C=" + counts.get('C') + "; Other=" + counts.get('O');
    }

    /**
     * returns the fraction of G+C nucleotides
     *
     * @return gcContent
     */
    public double getGCcontent() {
        double perc = (double) (getBaseCountsMap().get('G') + getBaseCountsMap().get('C')) / sequence.length();
        return perc;
    }

    /**
     * returns base counts, with O=other
     *
     * @return baseCounts map
     */
    public HashMap<Character, Integer> getBaseCountsMap() {
        if (baseCounts == null) {
            baseCounts = new HashMap<Character, Integer>();
        } else {
            return baseCounts;
        }
        baseCounts.put('A', 0);
        baseCounts.put('C', 0);
        baseCounts.put('G', 0);
        baseCounts.put('T', 0);
        baseCounts.put('O', 0);
        for (Character c : this.sequence.toUpperCase().toCharArray()) {
            baseCounts.put(c, baseCounts.get(c) + 1);
        }
        //System.out.println(baseCounts);
        return baseCounts;
    }

    /**
     * returns the molecular weight (D) of this primer, assuming it contains
     * only non-degenerate bases
     *
     * @return molWeight
     */
    public double getMolecularWeight() {
        HashMap<Character, Integer> baseCountsLocal = getBaseCountsMap();
        if (baseCountsLocal.get('O') != 0) {
            throw new UnsupportedOperationException("Ambiguous bases not supported yet");
        }
        double mw = baseCountsLocal.get('G') * 329.21;
        mw += baseCountsLocal.get('A') * 313.21;
        mw += baseCountsLocal.get('T') * 304.2;
        mw += baseCountsLocal.get('C') * 289.18;
        mw -= 61.96;
        return mw;
    }

    /**
     * Simple calculation of melting point:
     * <p>
     * Simple formula when primer length < 14 bases from Rychlik, W. and Rhoads,
     * R.E. (1989) Nucleic Acids Research 17, 8543 Tm = 4C x (number of G's and
     * C's in the primer) + 2C x (number of A's and T's in the primer)
     * <p>
     * When longer use: tm = 64.9C + 41C * (number of G's and C's in the primer
     * - 16.4)/ primer length both assume reaction is carried out in the
     * presence of 50mM monovalent cations
     *
     * @return Tm the melting point
     */
    public int getBasicMeltingPoint() {
        HashMap<Character, Integer> baseCountsLocal = getBaseCountsMap();
        if (baseCountsLocal.get('O') != 0) {
            throw new UnsupportedOperationException("Ambiguous bases not supported yet");
        }

        if (length() < 14) {
            int tm = 4 * (baseCountsLocal.get('G') + baseCountsLocal.get('C')) + 2 * (baseCountsLocal.get('A') + baseCountsLocal.get('T'));
            return tm;
        } else {
            return (int) Math.round(64.9 + 41 * (baseCountsLocal.get('G') + baseCountsLocal.get('C') - 16.4) / length());
        }
    }

    /**
     * Commonly used formula takes into account the molarSalt concentration of
     * the reaction: Tm = 81.5C + 7.21C x Math.log(molarSalt) + (0.41 x GC) -
     * (675 / primer length); see refs Rychlik, W. and Rhoads, R.E. (1989) Nucl.
     * Acids Res. 17, 8543. PCR Core Systems Technical Bulletin #TB254, Promega
     * Corporation. Sambrook, J., Fritsch, E.F. and Maniatis, T. (1989)
     * Molecular Cloning: A Laboratory Manual, Cold Spring Harbor Laboratory
     * Press, Cold Spring Harbor, NY. Mueller, P.R. et al. (1993) In: Current
     * Protocols in Molecular Biology 15.5, Greene Publishing Associates, Inc.
     * and John Wiley and Sons, New York.
     *
     * @return Tm the melting point
     */
    public int getSaltAdjustedMeltingPoint() {
        HashMap<Character, Integer> baseCountsLocal = getBaseCountsMap();
        if (baseCountsLocal.get('O') != 0) {
            throw new UnsupportedOperationException("Ambiguous bases not supported yet");
        }

        double pGC = ((double) (baseCountsLocal.get('G') + baseCountsLocal.get('C')) / length()) * 100;
        return (int) Math.round(81.5 + 7.21 * Math.log(defaultSaltMolarity) + (0.41 * pGC) - (675 / length()));
    }

    /**
     * The most sophisticated Tm calculations take into account the exact
     * sequence and base stacking parameters, not just the base composition. Tm
     * = ((1000* dh)/(ds+(R * Math.log(primer concentration))))-273.15; Borer
     * P.N. et al. (1974) J. Mol. Biol. 86, 843. SantaLucia, J. (1998) Proc.
     * Nat. Acad. Sci. USA 95, 1460. Allawi, H.T. and SantaLucia, J. Jr. (1997)
     * Biochemistry 36, 10581. von Ahsen N. et al. (1999) Clin. Chem. 45, 2094.
     *
     * @return Tm the melting point
     */
    public double getNearestNeighborMeltingPoint() {
        double R = 1.987;
        double ds = 0;
        double dh = 0;

        //perform salt correction
        double defaultMillimolarMagnesium = 1.5;
        double correctedSalt = defaultSaltMolarity + (defaultMillimolarMagnesium / 1000) * 140; //adjust for greater stabilizing effects of Mg compared to Na or K. See von Ahsen et al 1999
        ds += 0.368 * (length() - 1) * Math.log(correctedSalt); //from von Ahsen et al 1999

        String sequenceLocal = this.sequence.toUpperCase();
        //perform terminal corrections
        ds += terminalCorrectionsDsHash.get(sequenceLocal.charAt(0));
        ds += terminalCorrectionsDsHash.get(sequenceLocal.charAt(sequenceLocal.length() - 1));

        dh += terminalCorrectionsDhHash.get(sequenceLocal.charAt(0));
        dh += terminalCorrectionsDhHash.get(sequenceLocal.charAt(sequenceLocal.length() - 1));

        for (int i = 0; i < sequenceLocal.length() - 1; i++) {
            //System.out.println(sequence.substring(i, i+2));
            ds += dsHash.get(sequenceLocal.substring(i, i + 2));//dsValues[sequence.charAt(i) + sequence.charAt(i + 1)];
            dh += dhHash.get(sequenceLocal.substring(i, i + 2));//dh = dh + dhValues[sequence.charAt(i) + sequence.charAt(i + 1)];
        }
        //(((1000 * dh) / (ds + (R * Math.log(molarPrimerTotal / 2)))) - 273.15).toFixed(2);
        int defaultNanoMolarPrimerTotal = 200;
        return (((1000.0 * dh) / (ds + (R * Math.log((double) (defaultNanoMolarPrimerTotal / 1000000000.0) / 2.0)))) - 273.15);
    }

    /**
     * tests for the presence of a base run of maxRunLength or longer
     *
     * @param maxRunLength
     * @return hasBaseRun
     */
    public boolean hasBaseRun(int maxRunLength) {
        boolean hasRun = false;
        char[] nucs = {'G', 'A', 'T', 'C'};
        for (char nuc : nucs) {
            Pattern p = Pattern.compile(nuc + "{" + maxRunLength + "}");
            Matcher m = p.matcher(this.sequence.toUpperCase());
            if (m.find()) {
                hasRun = true;
            }
        }
        return hasRun;
    }

    /**
     * returns a message concerning the presence of a base run of maxRunLength
     * or longer
     *
     * @param maxRunLength
     * @return test result message
     */
    private String getBaseRunTestMessage(int maxRunLength) {
        return hasBaseRun(maxRunLength) ? TESTFAIL + ": base run(s) of over " + maximumBaseRunLength + " bases detected" : TESTPASS;
    }

    /**
     * tests for the presence of a dinucleotide base run of maxRunLength or
     * longer
     *
     * @param maxRunLength
     * @return hasDinucleotideBaseRun
     */
    public boolean hasDinucleotideBaseRun(int maxRunLength) {
        boolean hasRun = false;
        String[] dinucs = {"GA", "GT", "GC", "AG", "AT", "AC", "TG", "TA", "TC", "CG", "CA", "CT"};
        for (String dinuc : dinucs) {
            Pattern p = Pattern.compile("(" + dinuc + ")" + "{" + maxRunLength + "}");

            Matcher m = p.matcher(this.sequence.toUpperCase());
            if (m.find()) {
                //System.out.println(p);
                hasRun = true;
            }
        }
        return hasRun;
    }

    /**
     * returns a message concerning the presence of a dinculeotide base run of
     * maxRunLength or longer
     *
     * @param maxRunLength
     * @return test result message
     */
    private String getDinucleotideBaseRunTestMessage(int maxRunLength) {
        return hasDinucleotideBaseRun(maxRunLength) ? TESTFAIL + ": dinucleotide base run(s) of over " + maximumBaseRunLength + " bases detected" : TESTPASS;
    }

    /**
     * returns the count of G+C nucleotides in the last 3 prime nucleotides
     * (length indicated by desiredThreePrimeEndSize)
     *
     * @param desiredThreePrimeEndSize
     * @return threePrimeGCcount
     */
    public int getThreePrimeGCcount(int desiredThreePrimeEndSize) {
        String threePrimeEnd;
        if (sequence.length() >= desiredThreePrimeEndSize) {
            threePrimeEnd = sequence.substring(sequence.length() - desiredThreePrimeEndSize, sequence.length());
        } else {
            threePrimeEnd = sequence;
        }
        //System.out.println("+++"+threePrimeEnd);

        Primer tpPrimer = new Primer(threePrimeEnd);
        tpPrimer.getBaseCountsMap();

        int gcCounts = tpPrimer.getBaseCountsMap().get('G') + tpPrimer.getBaseCountsMap().get('C');

        return gcCounts;
    }

    /**
     * @param minimumSuitableGCpercentage
     * @param maximumSuitableGCpercentage
     * @return test message
     */
    private String getPrimerGCpercentageTestMessage(int minimumSuitableGCpercentage, int maximumSuitableGCpercentage) {
        if (getGCcontent() * 100 < minimumSuitableGCpercentage) {
            return TESTFAIL + ": primer below suitable GC percentage of " + minimumSuitableGCpercentage;
        } else if (getGCcontent() * 100 > maximumSuitableGCpercentage) {
            return TESTFAIL + ": primer above suitable GC percentage of " + maximumSuitableGCpercentage;
        } else {
            return TESTPASS;
        }
    }

    /**
     * @param minimumSuitableTm
     * @param maximumSuitableTm
     * @return test message
     */
    private String getPrimerTmTestMessage(int minimumSuitableTm, int maximumSuitableTm) {
        double nnTm = getNearestNeighborMeltingPoint();
        if (nnTm < minimumSuitableTm) {
            return TESTFAIL + ": primer below suitable Tm (Nearest Neightbor) of " + minimumSuitableTm;
        } else if (nnTm > maximumSuitableTm) {
            return TESTFAIL + ": primer above suitable Tm (Nearest Neightbor) of " + maximumSuitableTm;
        } else {
            return TESTPASS;
        }
    }

    /**
     * @param minimumSuitableThreePrimeGCnumber
     * @param maximumSuitableThreePrimeGCnumber
     * @return test message
     */
    private String getGCclampTestMessage(int minimumSuitableThreePrimeGCnumber, int maximumSuitableThreePrimeGCnumber) {
        int gcCounts = getThreePrimeGCcount(Primer.desiredThreePrimeEndSize);
        if (gcCounts < minimumSuitableThreePrimeGCnumber) {
            return TESTFAIL + ": there are less than " + minimumSuitableThreePrimeGCnumber + " G's or C's in the last " + desiredThreePrimeEndSize + " bases; ";
        } else if (gcCounts > maximumSuitableThreePrimeGCnumber) {
            return TESTFAIL + ": there are more than " + maximumSuitableThreePrimeGCnumber + " G's or C's in the last " + desiredThreePrimeEndSize + " bases; ";
        } else {
            return TESTPASS;
        }
    }

    public Alignment getSelfComplementarity() {
        /*                 Self-annealing: Warning: There are more than 3 self-annealing bases in a row; 
         : atttaggtgacactatag            
         :             ||||||            
         :             gatatcacagtggattta
         Hairpin formation: Pass
              
         Self-annealing: Warning: There are more than 3 self-annealing bases in a row; 
         :             ggagctgcatgtgtcagagg
         :             | |||| |            
         : ggagactgtgtacgtcgagg          
         */
        try {
            Sequence pFirst = SequenceFactory.createSequence(sequence, SequenceType.DNA);
            Sequence pSecond = SequenceFactory.createSequence(new StringBuilder(sequence).reverse().toString(), SequenceType.DNA);
            ArrayList<Sequence> seqs = new ArrayList<Sequence>();
            seqs.add(pFirst);
            seqs.add(pSecond);
            System.out.println("starting alignment of " + pFirst + " to " + pSecond);
            AlignmentOptions alignmentOptions = new AlignmentOptions(seqs.get(0).getSequenceType(), AlignmentMatrixType.DNA_STRUCTURE_ALIGNMENT);
            alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.ALIGNMENT_LENGTH, 3);
            alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.SW_SCORE, 1);
            //alignmentOptions.set
            alignmentOptions.setAlsoComplement(false);

            AlignmentController aligner = new AlignmentController(
                    AlignmentAlgorithm.LOCAL,
                    AlignmentStrategy.INTERMOLECULAR_FIRST_TO_ALL,
                    alignmentOptions,
                    seqs);
            aligner.start();
            ArrayList<Alignment> alignmentList = aligner.getAlignmentList();

            for (Alignment alignment : alignmentList) {
                System.out.print("score=" + alignment.getPropertyValue(AlignmentProperty.SW_SCORE));
                System.out.println(" length=" + alignment.getPropertyValue(AlignmentProperty.ALIGNMENT_LENGTH));
                System.out.print(String.format("%3s ", (alignment.getTopParent().getSequenceCoordinates().getStart() + 1)));
                System.out.print(alignment.getTopStrand());
                System.out.println(" " + (alignment.getTopParent().getSequenceCoordinates().getStop() + 1));
                System.out.println("    " + alignment.getMiddleLine());
                System.out.print(String.format("%3s ", alignment.getBottomParent().getSequenceCoordinates().getStart() + 1));
                System.out.print(alignment.getBottomStrand());
                System.out.println(" " + (alignment.getBottomParent().getSequenceCoordinates().getStop() + 1));
                System.out.println("");
                //return alignment;
            }

        } catch (SequenceCreationException e) {
            //e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }

        return null;
    }

    private String getPrimerSelfAnnealingTestMessage(int maxContiguousSelfAnnealingBases) {
        Alignment a = getSelfComplementarity();
        System.out.println(a.toString());

        return "NA";//a.getAlignmentProperties().g
    }

    /*              Hairpin formation: Warning: There are more than 3 hairpin bases in a row; 
     :         ctggca
     :          |||| )
     : gtggtttgcaccga
     */

    /**
     * evaluates this primer and returns all properties and tests in a wrapper
     * object
     *
     * @return primerEvaluation
     */
    public PrimerEvaluation evaluate() {
        /**
         * ------------------------------------------------------------ General
         * properties: ------------------- Primer name: KS Primer sequence:
         * cgaggtcgacggtatcg Sequence length: 17 Base counts: G=7; A=3; T=3;
         * C=4; Other=0; GC content (%): 64.71 Molecular weight (Daltons):
         * 5251.46 nmol/A260: 5.99 micrograms/A260: 31.46 Basic Tm (degrees C):
         * 52 Salt adjusted Tm (degrees C): 47 Nearest neighbor Tm (degrees C):
         * 62.07
         *
         * PCR suitability tests (Pass / Warning):
         * ------------------------------------ Single base runs: Pass
         * Dinucleotide base runs: Pass Length: Pass Percent GC: Warning: %GC is
         * greater than 60; Tm (Nearest neighbor): Warning: Tm is greater than
         * 58; GC clamp: Pass Self-annealing: Pass Hairpin formation: Pass
         * ------------------------------------------------------------
         */
        HashMap<PrimerEvaluationProperty, String> properties = new HashMap<PrimerEvaluationProperty, String>();
        HashMap<PrimerEvaluationTest, String> tests = new HashMap<PrimerEvaluationTest, String>();
        PrimerEvaluation pe = new PrimerEvaluation();
        pe.setProperties(properties);
        pe.setTests(tests);

        properties.put(PrimerEvaluationProperty.NAME, getName());
        properties.put(PrimerEvaluationProperty.SEQUENCE, getSequence());

        properties.put(PrimerEvaluationProperty.LENGTH, "" + length());
        properties.put(PrimerEvaluationProperty.BASE_COUNTS, getBaseCounts());
        properties.put(PrimerEvaluationProperty.GC_CONTENT, Primer.percentageNumberFormatter.format(getGCcontent()));
        properties.put(PrimerEvaluationProperty.MOLECULAR_WEIGHT, Primer.numberFormatter.format(getGCcontent()));
        properties.put(PrimerEvaluationProperty.BASIC_TM, "" + getBasicMeltingPoint());
        properties.put(PrimerEvaluationProperty.SALT_ADJUSTED_TM, "" + getSaltAdjustedMeltingPoint());
        properties.put(PrimerEvaluationProperty.NEAREST_NEIGHBOR_TM, numberFormatter.format(getNearestNeighborMeltingPoint()));

        tests.put(PrimerEvaluationTest.SINGLE_BASE_RUNS, getBaseRunTestMessage(maximumBaseRunLength));
        tests.put(PrimerEvaluationTest.DINUCLEOTIDE_BASE_RUNS, getDinucleotideBaseRunTestMessage(maximumBaseRunLength));
        int minimumSuitableLength = 14;
        int maximumSuitableLength = 30;
        tests.put(PrimerEvaluationTest.LENGTH, getPrimerLengthTestMessage(minimumSuitableLength, maximumSuitableLength));
        int minimumSuitableGCpercentage = 40;
        int maximumSuitableGCpercentage = 60;
        tests.put(PrimerEvaluationTest.PERCENT_GC, getPrimerGCpercentageTestMessage(minimumSuitableGCpercentage, maximumSuitableGCpercentage));
        int minimumSuitableThreePrimeGCnumber = 1;
        int maximumSuitableThreePrimeGCnumber = 3;
        tests.put(PrimerEvaluationTest.GC_CLAMP, getGCclampTestMessage(minimumSuitableThreePrimeGCnumber, maximumSuitableThreePrimeGCnumber));
        tests.put(PrimerEvaluationTest.NEAREST_NEIGHBOR_TM, getPrimerTmTestMessage(Primer.minimumSuitableTm, Primer.maximumSuitableTm));
        int maxContiguousSelfAnnealingBases = 3;
        tests.put(PrimerEvaluationTest.SELF_ANNEALING, getPrimerSelfAnnealingTestMessage(maxContiguousSelfAnnealingBases));
        return pe;
    }
}
