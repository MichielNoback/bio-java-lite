/**
 *
 */
package net.cellingo.sequence_tools.annotation;

import net.cellingo.sequence_tools.sequences.Sequence;

import java.util.*;

/**
 * An annotated sequence object
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.9
 */
public class AnnotatedSequence implements SequenceObject {
    private Sequence sequence;
    protected SequenceObjectType sequenceObjectType;
    private MoleculeType moleculeType;
    /*
	 * Data structure for all sequence elements. Primary key is the SequenceElementType
	 * and the secondary key is the sequenceElementID
	 */
    protected HashMap<SequenceElementType, HashMap<String, SequenceElement>> sequenceElements;

    /**
     * default, no-arg constructor
     */
    public AnnotatedSequence() {
        this(SequenceObjectType.ANNOTATED_SEQUENCE);
    }

    /**
     * default, no-arg constructor
     */
    public AnnotatedSequence(SequenceObjectType objectType) {
        this.sequenceObjectType = objectType;
        this.moleculeType = MoleculeType.UNKNOWN;
        this.sequenceElements = new HashMap<>();
    }

    /**
     * remove all elements of a particular type (gene, ORF/CDS or other annotated features)
     *
     * @param sequenceElementType
     */
    public void removeSequenceElements(SequenceElementType sequenceElementType) {
        if (sequenceElements.containsKey(sequenceElementType)) {
            sequenceElements.get(sequenceElementType).clear();
        }
    }

    /**
     * add a sequence element to the annotation
     *
     * @param element
     */
    public void addElement(SequenceElement element) {
        SequenceElementType type = element.getSequenceElementType();
        if (sequenceElements.containsKey(type)) {
            sequenceElements.get(type).put(element.getId(), element);
        } else {
            HashMap<String, SequenceElement> elements = new HashMap<>();
            elements.put(element.getId(), element);
            sequenceElements.put(element.getSequenceElementType(), elements);
        }
    }

    /**
     * get an iterator to loop all the -sorted- elements of a particular type.
     * If the type does not exist, an empty iterator will be returned
     *
     * @param sequenceElementType
     * @return iterator of elements of one type
     */
    public Iterator<SequenceElement> getElements(SequenceElementType sequenceElementType) {
        ArrayList<SequenceElement> list = new ArrayList<>();
        if (sequenceElements.containsKey(sequenceElementType)) {
            for (SequenceElement element : sequenceElements.get(sequenceElementType).values()) {
                list.add(element);
            }
            Collections.sort(list);
        }
        return list.iterator();
    }

    /**
     * convenience method: get a list of -sorted- elements of a particular type.
     * If the type does not exist, an empty list will be returned
     *
     * @param sequenceElementType
     * @return iterator of elements of one type
     */
    public List<SequenceElement> getElementList(SequenceElementType sequenceElementType) {
        if (sequenceElements.containsKey(sequenceElementType)) {
            ArrayList<SequenceElement> list = new ArrayList<SequenceElement>();
            for (SequenceElement element : sequenceElements.get(sequenceElementType).values()) {
                list.add(element);
            }
            Collections.sort(list);
            return list;
        } else {
            return new ArrayList<SequenceElement>();
        }
    }

    /**
     * convenience method: get a list of -sorted- elements of a particular type.
     * If the type does not exist, an empty list will be returned
     *
     * @param sequenceElementType
     * @param coordinates         the coordinates that elements should overlap to be included
     * @return iterator of elements of one type
     */
    public List<SequenceElement> getElementList(SequenceElementType sequenceElementType, SequenceCoordinates coordinates) {
        ArrayList<SequenceElement> list = new ArrayList<>();
        if (sequenceElements.containsKey(sequenceElementType)) {
            for (SequenceElement element : sequenceElements.get(sequenceElementType).values()) {
                if (element.getSequenceCoordinates().overlaps(coordinates)) list.add(element);
            }
            Collections.sort(list);
        }
        return list;
    }


    /**
     * get an iterator for all sequence element types that are represented in this object
     *
     * @return sequenceElementType iterator
     */
    public Iterator<SequenceElementType> getSequenceElementTypes() {
        return sequenceElements.keySet().iterator();
    }

    /**
     * get an iterator for all sequence element types that are represented in this object
     *
     * @return sequenceElementType iterator
     */
    public List<SequenceElementType> getSequenceElementTypesList() {
        List<SequenceElementType> list = new ArrayList<>();
        list.addAll(sequenceElements.keySet());
        return list;
    }

    /**
     * get a particular element based on its ID and type
     *
     * @return sequenceElement
     */
    public SequenceElement getElement(SequenceElementType sequenceElementType, int id) {
        if (sequenceElements.containsKey(sequenceElementType)) {
            return sequenceElements.get(sequenceElementType).get(id);
        } else {
            return null;
        }
    }

    /**
     * returns whether this genbank entry contains elements of the given type
     *
     * @param type
     * @return elements of this type are present
     */
    public boolean containsElementType(SequenceElementType type) {
        return sequenceElements.containsKey(type);
    }

    /**
     * @return the geneList
     */
    public List<Gene> getGeneList() {
        List<Gene> genes = new ArrayList<Gene>();
        if (this.sequenceElements.containsKey(SequenceElementType.GENE)) {
            for (SequenceElement e : sequenceElements.get(SequenceElementType.GENE).values()) {
                if (e instanceof Gene) {
                    genes.add((Gene) e);
                }
            }
        }
        return genes;
    }

    /**
     * get a gene based on its name (eg "ATR-3")
     *
     * @param geneName
     * @return gene
     */
    public Gene getGene(String geneName) {
        if (this.sequenceElements.containsKey(SequenceElementType.GENE)) {
            for (SequenceElement e : sequenceElements.get(SequenceElementType.GENE).values()) {
                if (e instanceof Gene) {
                    Gene g = (Gene) e;
                    if (g.getName().equalsIgnoreCase(geneName)) return g;
                }
            }
        }
        return null;
    }
    /**
     * is the gene already present in the collection?
     * @param geneName
     * @return contains this gene
     */
//	public boolean containsGene(String geneName){
//		return geneList.containsKey(geneName);
//	}


    /**
     * get the accession number of this annotated sequence
     *
     * @return accession
     */
    public String getAccessionNumber() {
        return this.sequence.getAccessionNumber();
    }

    /**
     * set the accession number
     *
     * @param accession
     */
    public void setAccessionNumber(String accession) {
        this.sequence.setAccessionNumber(accession);
    }

    /**
     * get the annotations object associated with this sequence
     * redirected to Sequence.Attributes
     *
     * @return annotations
     */
    public Attributes getAttributes() {
        return sequence.getAttributes();
    }

    /**
     * redirected to Sequence.Attributes
     *
     * @param annotations the annotations to set
     */
    public void setAnnotations(Attributes annotations) {
        this.sequence.setAttributes(annotations);
    }

    /**
     * add an annotation to this sequence; all redirected to Sequence.Attributes
     *
     * @param type
     * @param content
     */
    public void addAnnotation(String type, String content) {
        if(this.sequence.getAttributes() == null) {
            this.sequence.setAttributes(new Attributes());
        }
        this.sequence.getAttributes().addAttribute(type, content);
    }

    /**
     * get the sequenceID assigned by the program to the parent sequence
     *
     * @return sequenceID
     */
    public int getProgramSequenceID() {
        return sequence.getProgramSequenceID();
    }

    /**
     * get the sequence
     *
     * @return sequence
     */
    public Sequence getSequence() {
        return this.sequence;
    }

    /**
     * get a String representation of this sequence object
     *
     * @return sequenceString
     */
    public String getSequenceString() {
        return this.sequence.getSequenceString();
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(Sequence sequence) {
        this.sequence = sequence;

        for (HashMap<String, SequenceElement> typeMap : sequenceElements.values()) {
            for (SequenceElement e : typeMap.values()) {
                e.setParentSequence(sequence);
            }
        }
    }

    /**
     * get the length of this sequence
     *
     * @return sequence length
     */
    public int getSequenceLength() {
        return sequence.getSequenceLength();
    }

    /**
     * get the sequence name
     *
     * @return sequence name
     */
    public String getSequenceName() {
        return sequence.getSequenceName();
    }

    /**
     * get the object type of this sequence
     *
     * @return sequence object type
     */
    public SequenceObjectType getSequenceObjectType() {
        return sequenceObjectType;
    }

    /**
     * @return the moleculeType
     */
    public MoleculeType getMoleculeType() {
        return moleculeType;
    }

    /**
     * @param moleculeType the moleculeType to set
     */
    public void setMoleculeType(MoleculeType moleculeType) {
        this.moleculeType = moleculeType;
    }

    @Override
    public String toString() {
        return "AnnotatedSequence{" +
                "sequence=" + sequence +
                ", sequenceObjectType=" + sequenceObjectType +
                ", moleculeType=" + moleculeType +
                ", No of elements=" + sequenceElements.size() +
                '}';
    }
}
