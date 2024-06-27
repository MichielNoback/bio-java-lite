package net.cellingo.sequence_tools.annotation;

import java.util.HashMap;
import java.util.HashSet;

/**
 * An enum that represents all possible sequence element types
 * It also provides conversion methods from / to String representation
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceElementType {

    GENE("gene"),
    OPERON("operon"),
    CDS("CDS"),
    STEM_LOOP("hairpin; a double-helical region formed by base-pairing"),
    STRUCTURE("non-hairpin basepaired structure"),
    MRNA("mRNA"),
    TRNA("tRNA"),
    RRNA("rRNA"),
    TMRNA("transfer messenger RNA"),
    MIRNA("miRNA"),
    MISC_RNA("miscellaneous RNA"),
    NCRNA("non-coding RNA"),
    PRECURSOR_RNA("precursor RNA"),
    PRIMARY_TRANSCRIPT("primary unprocessed transcript"),

    INTRON("intron"),
    EXON("exon"),
    PROMOTER("promoter"),
    TATA_BOX("TATA box"),
    UTR_5("5'-UTR"),
    UTR_3("3'-UTR"),
    TERMINATOR("terminator"),
    PRIMER_BIND("primer binding site"),
    PROTEIN_BIND("protein binding site"),
    REPLICATION_ORIGIN("origin of replication"),
    POLY_A_SIGNAL("poly-A signal"),
    POLY_A_SITE("poly-A site"),
    CAAT_SIGNAL("CAAT box"),
    GC_SIGNAL("GC box"),
    ENHANCER("enhancer"),
    ATTENUATOR("attenuator"),
    I_DNA("intervening DNA"),
    MIN_10_SIGNAL("Pribnow box"),
    MIN_35_SIGNAL("minus 35 signal"),
    RBS("RBS"),

    LTR("long terminal repeat"),
    REPEAT_REGION("region containing repeating units"),

    V_SEGMENT("immunoglobulin V-segment"),
    V_REGION("variable region of immunoglobulin L and H chains"),
    D_SEGMENT("immunoglobulin D-segment"),
    J_SEGMENT("immunoglobulin J-segment"),
    C_REGION("immunoglobulin C-region"),
    N_REGION("immunoglobulin N-region"),
    S_REGION("immunoglobulin S-region"),
    D_LOOP("mt displacement loop"),
    TRANSIT_PEPTIDE("transit peptide"),
    SIGNAL_PEPTIDE("signal peptide coding sequence"),
    MAT_PEPTIDE("mature peptide"),

    STS("sequence tagged site"),
    MISC_FEATURE("miscellaneous feature"),
    MISC_RECOMB("miscellaneous recombination"),
    MISC_BINDING("miscellaneous binding site"),
    MISC_DIFFERENCE("miscellaneous difference"),
    MISC_SIGNAL("region containing a signal"),
    MISC_STRUCTURE("secondary or tertiary nucleotide structure"),

    MODIFIED_BASE("modified base"),
    ORI_T("origin of transfer"),

    VARIATION("variation"),
    UNSURE("unsure sequence"),
    CONFLICT("conflict"),
    OLD_SEQUENCE("previous version"),
    GAP("gap in the sequence"),

    PROTEIN("protein"),
    REGION("region"),
    SITE("site / sequence pattern"),
    SNP("SNP"),

    UNDEFINED("undefined element"),
    //ORF("ORF"),
    START_CODON("start codon"),
    STOP_CODON("stop codon"),
    PROTEIN_DOMAIN("protein domain");


    private String type;
    private static HashMap<String, SequenceElementType> types;
    private static HashSet<SequenceElementType> geneElements;

    static {
        types = new HashMap<String, SequenceElementType>();
        types.put("gene", SequenceElementType.GENE);
        types.put("operon", SequenceElementType.OPERON);
        types.put("trna", SequenceElementType.TRNA);
        types.put("rrna", SequenceElementType.RRNA);
        types.put("mrna", SequenceElementType.MRNA);
        types.put("ncrna", SequenceElementType.NCRNA);
        types.put("tmrna", SequenceElementType.TMRNA);
        types.put("misc_rna", SequenceElementType.MISC_RNA);
        types.put("mirna", SequenceElementType.MIRNA);
        types.put("precursor_rna", SequenceElementType.PRECURSOR_RNA);
        types.put("prim_transcript", SequenceElementType.PRIMARY_TRANSCRIPT);

        types.put("cds", SequenceElementType.CDS);
        types.put("exon", SequenceElementType.EXON);
        types.put("intron", SequenceElementType.INTRON);
        types.put("sts", SequenceElementType.STS);
        types.put("v_segment", SequenceElementType.V_SEGMENT);
        types.put("v_region", SequenceElementType.V_REGION);
        types.put("d_segment", SequenceElementType.D_SEGMENT);
        types.put("j_segment", SequenceElementType.J_SEGMENT);
        types.put("c_region", SequenceElementType.C_REGION);
        types.put("s_region", SequenceElementType.S_REGION);
        types.put("n_region", SequenceElementType.N_REGION);
        types.put("misc_feature", SequenceElementType.MISC_FEATURE);
        types.put("misc_recomb", SequenceElementType.MISC_RECOMB);
        types.put("-10_signal", SequenceElementType.MIN_10_SIGNAL);
        types.put("-35_signal", SequenceElementType.MIN_35_SIGNAL);

        types.put("rbs", SequenceElementType.RBS);
        types.put("promoter", SequenceElementType.PROMOTER);
        types.put("enhancer", SequenceElementType.ENHANCER);
        types.put("polya_signal", SequenceElementType.POLY_A_SIGNAL);
        types.put("polya_site", SequenceElementType.POLY_A_SITE);
        types.put("primer_bind", SequenceElementType.PRIMER_BIND);
        types.put("protein_bind", SequenceElementType.PROTEIN_BIND);
        types.put("repeat_region", SequenceElementType.REPEAT_REGION);
        types.put("rep_origin", SequenceElementType.REPLICATION_ORIGIN);
        types.put("stem_loop", SequenceElementType.STEM_LOOP);
        types.put("5'utr", SequenceElementType.UTR_5);
        types.put("3'utr", SequenceElementType.UTR_3);
        types.put("d-loop", SequenceElementType.D_LOOP);
        types.put("attenuator", SequenceElementType.ATTENUATOR);
        types.put("caat_signal", SequenceElementType.CAAT_SIGNAL);
        types.put("gc_signal", SequenceElementType.GC_SIGNAL);
        types.put("tata_signal", SequenceElementType.TATA_BOX);
        types.put("terminator", SequenceElementType.TERMINATOR);
        types.put("ltr", SequenceElementType.LTR);
        types.put("conflict", SequenceElementType.CONFLICT);
        types.put("gap", SequenceElementType.GAP);
        types.put("idna", SequenceElementType.I_DNA);
        types.put("oriT", SequenceElementType.ORI_T);
        types.put("modified_base", SequenceElementType.MODIFIED_BASE);
        types.put("mat_peptide", SequenceElementType.MAT_PEPTIDE);
        types.put("sig_peptide", SequenceElementType.SIGNAL_PEPTIDE);
        types.put("transit_peptide", SequenceElementType.TRANSIT_PEPTIDE);
        types.put("misc_binding", SequenceElementType.MISC_BINDING);
        types.put("misc_difference", SequenceElementType.MISC_DIFFERENCE);
        types.put("misc_signal", SequenceElementType.MISC_SIGNAL);
        types.put("misc_structure", SequenceElementType.MISC_STRUCTURE);
        types.put("old_sequence", SequenceElementType.OLD_SEQUENCE);
        types.put("unsure", SequenceElementType.UNSURE);
        types.put("variation", SequenceElementType.VARIATION);

		/*GenPept types*/
        types.put("protein", SequenceElementType.PROTEIN);
        types.put("region", SequenceElementType.REGION);
        types.put("site", SequenceElementType.SITE);

		/*types below are not specific genbank types*/
        types.put("start_codon", SequenceElementType.START_CODON);
        types.put("stop_codon", SequenceElementType.STOP_CODON);
        types.put("structure", SequenceElementType.STRUCTURE);
        types.put("protein_domain", SequenceElementType.PROTEIN_DOMAIN);

        geneElements = new HashSet<SequenceElementType>();
        geneElements.add(TATA_BOX);
        geneElements.add(CDS);
        geneElements.add(EXON);
        geneElements.add(INTRON);
        geneElements.add(MIN_10_SIGNAL);
        geneElements.add(MIN_35_SIGNAL);
        geneElements.add(RBS);
        geneElements.add(PROMOTER);
        geneElements.add(POLY_A_SIGNAL);
        geneElements.add(POLY_A_SITE);
        geneElements.add(UTR_5);
        geneElements.add(UTR_3);
        geneElements.add(TATA_BOX);
        geneElements.add(TERMINATOR);
        geneElements.add(SIGNAL_PEPTIDE);
        geneElements.add(MISC_BINDING);
        geneElements.add(PRECURSOR_RNA);
        geneElements.add(PRIMARY_TRANSCRIPT);
        geneElements.add(MISC_FEATURE);
    }


    private SequenceElementType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }

    /**
     * returns whether this type is a possible element of a gene
     *
     * @return is possible gene element
     */
    public boolean isGeneElement() {
        return geneElements.contains(this);
    }

    /**
     * conversion method between String description of genbank format and enum
     * e.g. "TRNA" returns SequenceElementType.TRNA
     *
     * @param type
     * @return sequenceElementType
     */
    public static SequenceElementType getType(String type) {
        type = type.toLowerCase();
        if (types.containsKey(type)) {
            return types.get(type);
        }
        return SequenceElementType.UNDEFINED;
    }

}
