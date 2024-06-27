package net.cellingo.sequence_tools.annotation;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum MoleculeType {
    UNKNOWN("unknown molecule type"),
    DNA("DNA"),
    C_DNA("copy DNA"),
    CHROMOSOMAL_DNA("chromosomal DNA"),
    GENOMIC_DNA("genomic DNA"),

    RNA("RNA"),
    M_RNA("mRNA"),
    R_RNA("rRNA"),
    T_RNA("tRNA"),
    HN_RNA("hnRNA"),
    MI_RNA("miRNA"),

    PROTEIN("protein"),
    POLYPEPTIDE("polypeptide"),
    ENZYME("enzyme");

    private String type;

    MoleculeType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }

}
