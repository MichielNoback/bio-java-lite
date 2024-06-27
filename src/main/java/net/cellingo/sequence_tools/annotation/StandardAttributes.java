package net.cellingo.sequence_tools.annotation;

import java.util.*;

/**
 * StandardAttributes for different sequence formats/domains.
 * Creation date: Jul 13, 2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public abstract class StandardAttributes {
    public static final StandardAttributes GFF_ATTRIBUTES = new StandardGffAttributes();
    public static final StandardAttributes GENBANK_ATTRIBUTES = new StandardGenbankAttributes();
    public static final StandardAttributes SIMPLE_SEQUENCE_ATTRIBUTES = new StandardFastaAttributes();
    public static StandardAttributes getStandardAttributes(SequenceObjectType sequenceObjectType) {
        switch (sequenceObjectType) {
            case GFF3_SEQUENCE:
                return GFF_ATTRIBUTES;
            case GENBANK_SEQUENCE:
                return GENBANK_ATTRIBUTES;
            case SIMPLE_SEQUENCE:
                return SIMPLE_SEQUENCE_ATTRIBUTES;
            default:
                return new StandardAttributes() {
                    //NULL OBJECT
                    @Override
                    protected Map<String, String> getAttributesMap() {
                        return Collections.emptyMap();
                    }
                };
        }
    }

    protected abstract Map<String, String> getAttributesMap();

    public boolean isStandardAttribute(String attribute) {
        return getAttributesMap().containsKey(attribute);
    }

    public List<String> getStandardAttributes() {
        List<String> attributeNames = new ArrayList<>();
        attributeNames.addAll(getAttributesMap().keySet());
        return attributeNames;
    }

    public String getDescriptionOf(String attribute) {
        if (getAttributesMap().containsKey(attribute)) {
            return getAttributesMap().get(attribute).length() == 0 ? "" : getAttributesMap().get(attribute);
        }
        return "";
    }


    /**
     * GFF3 Attributes
     */
    private static class StandardFastaAttributes extends StandardAttributes {
        private static final Map<String, String> standardAttributes = new HashMap<>();

        static {
            standardAttributes.put("ID", "Indicates the unique identifier of the feature");
            standardAttributes.put("Dbxref", "A database cross reference");
            standardAttributes.put("Name", "Display name for the feature. This is the name to be displayed to the user. Unlike IDs, there is no requirement that the Name be unique within the file.");
            standardAttributes.put("Organism", "");
        }

        @Override
        protected Map<String, String> getAttributesMap() {
            return standardAttributes;
        }
    }

    /**
     * GFF3 Attributes
     */
    private static class StandardGffAttributes extends StandardAttributes {
        private static final Map<String, String> standardAttributes = new HashMap<>();

        static {
            standardAttributes.put("ID", "Indicates the unique identifier of the feature. IDs must be unique within the scope of the GFF file");
            standardAttributes.put("Name", "Display name for the feature. This is the name to be displayed to the user. Unlike IDs, there is no requirement that the Name be unique within the file.");
            standardAttributes.put("Alias", "A secondary name for the feature. It is suggested that this tag be used whenever a secondary identifier for the feature is needed, such as locus names and accession numbers. Unlike ID, there is no requirement that Alias be unique within the file");
            standardAttributes.put("Parent", "Indicates the parent of the feature. A parent ID can be used to group exons into transcripts, transcripts into genes, and so forth. A feature may have multiple parents. Parent can *only* be used to indicate a partof relationship");
            standardAttributes.put("Target", "Indicates the target of a nucleotide-to-nucleotide or protein-to-nucleotide alignment. The format of the value is \"target_id start end [strand]\", where strand is optional and may be \"+\" or \"-\". If the target_id contains spaces, they must be escaped as hex escape %20.");
            standardAttributes.put("Gap", "The alignment of the feature to the target if the two are not collinear (e.g. contain gaps). The alignment format is taken from the CIGAR format described in the Exonerate documentation. http://cvsweb.sanger.ac.uk/cgi-bin/cvsweb.cgi/exonerate?cvsroot=Ensembl)");
            standardAttributes.put("Derives_from", "Used to disambiguate the relationship between one feature and another when the relationship is a temporal one rather than a purely structural \"part of\" one. This is needed for polycistronic genes");
            standardAttributes.put("Note", "A free text note");
            standardAttributes.put("Dbxref", "A database cross reference");
            standardAttributes.put("Ontology_term", "A cross reference to an ontology term");
        }

        @Override
        protected Map<String, String> getAttributesMap() {
            return standardAttributes;
        }
    }

    /**
     * GFF3 Attributes
     * @See <a href = "http://www.insdc.org/files/feature_table.html">feature table</a>
     */
    private static class StandardGenbankAttributes extends StandardAttributes {
        private static final Map<String, String> standardAttributes = new HashMap<>();

        static {
            //main types
            standardAttributes.put("organism", "");
            standardAttributes.put("strain", "");
            standardAttributes.put("db_xref", "db cross-reference: <database>:<identifier>");
            standardAttributes.put("function", "");
            standardAttributes.put("gene", "symbol of the gene corresponding to a sequence region");
            standardAttributes.put("transl_table", "definition of genetic code table used if other than universal genetic code table");
            standardAttributes.put("product", "");
            standardAttributes.put("translation", "automatically generated one-letter abbreviated amino acid sequence derived from either the universal genetic code or the table as specified in /transl_table and as determined by an exception in the /transl_except qualifier");

            //others alpabetically
            standardAttributes.put("altitude", "geographical altitude");
            standardAttributes.put("allele", "name of the allele for the given gene");
            standardAttributes.put("anticodon", "location of the anticodon of tRNA and the amino acid for which it codes");
            standardAttributes.put("artificial_location", "artificial location");
            standardAttributes.put("bio_material", "");
            standardAttributes.put("bound_moiety", "name of the molecule/complex that may bind to the given feature");
            standardAttributes.put("cell_line", "cell line");
            standardAttributes.put("cell_type", "cell type");
            standardAttributes.put("citation", "");
            standardAttributes.put("chromosome", "");
            standardAttributes.put("clone", "");
            standardAttributes.put("clone_lib", "");
            standardAttributes.put("codon_start", "codon start");
            standardAttributes.put("collected_by", "");
            standardAttributes.put("collection_date", "");
            standardAttributes.put("compare", "");
            standardAttributes.put("country", "");
            standardAttributes.put("cultivar", "");
            standardAttributes.put("culture_collection", "institution code and identifier for the culture from which the nucleic acid sequenced was obtained");
            standardAttributes.put("dev_stage", "developmental stage");
            standardAttributes.put("direction", "");
            standardAttributes.put("ecotype", "");
            standardAttributes.put("environmental_sample", "");
            standardAttributes.put("EC_number", "Enzyme Commission number for enzyme product of sequence");
            standardAttributes.put("ecotype", "");
            standardAttributes.put("estimated_length", "estimated length of the gap in the sequence");
            standardAttributes.put("exception", "");
            standardAttributes.put("experiment", "");
            standardAttributes.put("focus", "");
            standardAttributes.put("frequency", "frequency of the occurrence of a feature");
            standardAttributes.put("gap_type", "type of gap connecting components in records of a genome assembly, or the type of biological gap in a record that is part of a genome assembly");
            standardAttributes.put("gene_synonym", "gene synonym");
            standardAttributes.put("germline", "");
            standardAttributes.put("haplogroup", "");
            standardAttributes.put("haplotype", "");
            standardAttributes.put("host", "");
            standardAttributes.put("identified_by", "");
            standardAttributes.put("inference", "a structured description of non-experimental evidence that supports the feature identification or assignment: [CATEGORY:]TYPE[ (same species)][:EVIDENCE_BASIS]");
            standardAttributes.put("isolate", "individual isolate from which the sequence was obtained");
            standardAttributes.put("isolation_source", "");
            standardAttributes.put("lab_host", "");
            standardAttributes.put("lat_lon", "geographical coordinates of the location where the specimen was collected");
            standardAttributes.put("linkage_evidence", "");
            standardAttributes.put("locus_tag", "a submitter-supplied, systematic, stable identifier for a gene and its associated features, used for tracking purposes");
            standardAttributes.put("macronuclear", "");
            standardAttributes.put("map", "genomic map position of feature");
            standardAttributes.put("mating_type", "");
            standardAttributes.put("mobile_element_type", "type and name or identifier of the mobile element which is described by the parent feature");
            standardAttributes.put("mod_base", "");
            standardAttributes.put("mol_type", "in vivo molecule type of sequence");
            standardAttributes.put("ncRNA_class", "");
            standardAttributes.put("note", "note");
            standardAttributes.put("number", "number");
            standardAttributes.put("old_locus_tag", "");
            standardAttributes.put("operon", "name of the group of contiguous genes transcribed into a single transcript to which that feature belongs");
            standardAttributes.put("organelle", "");
            standardAttributes.put("partial", "");
            standardAttributes.put("PCR_primers", "");
            standardAttributes.put("phenotype", "");
            standardAttributes.put("plasmid", "");
            standardAttributes.put("pop_variant", "");
            standardAttributes.put("protein_id", "protein id");
            standardAttributes.put("proviral", "this qualifier is used to flag sequence obtained from a virus or phage that is integrated into the genome of another organism");
            standardAttributes.put("pseudo", "pseudo");
            standardAttributes.put("pseudogene", "pseudogene: TYPE");
            standardAttributes.put("rearranged", "");
            standardAttributes.put("recombination_class", "");
            standardAttributes.put("regulatory_class", "regulatory class");
            standardAttributes.put("replace", "indicates that the sequence identified a feature's intervals is replaced by the sequence shown in \"text\"");
            standardAttributes.put("ribosomal_slippage", "");
            standardAttributes.put("rpt_family", "type of repeated sequence; \"Alu\" or \"Kpn\", for example");
            standardAttributes.put("rpt_type", "structure and distribution of repeated sequence");
            standardAttributes.put("rpt_unit_range", "");
            standardAttributes.put("rpt_unit_seq", "identity of a repeat sequence");
            standardAttributes.put("satellite", "");
            standardAttributes.put("segment", "");
            standardAttributes.put("serotype", "");
            standardAttributes.put("serovar", "");
            standardAttributes.put("sex", "");
            standardAttributes.put("specimen_voucher", "");
            standardAttributes.put("standard_name", "accepted standard name for this feature");
            standardAttributes.put("sub_clone", "");
            standardAttributes.put("sub_species", "");
            standardAttributes.put("sub_strain", "");
            standardAttributes.put("tag_peptide", "");
            standardAttributes.put("tissue_lib", "");
            standardAttributes.put("tissue_type", "");
            standardAttributes.put("transl_except", "");
            standardAttributes.put("trans_splicing", "");
            standardAttributes.put("transgenic", "");
            standardAttributes.put("type_material", "");
            standardAttributes.put("variety", "");
        }

        @Override
        protected Map<String, String> getAttributesMap() {
            return standardAttributes;
        }
    }

}
