/**
 *
 */
package net.cellingo.sequence_tools.annotation;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class encapsulates all attributes on a sequence; NOT to be confused with SequenceElements.
 * Objects of this type will store information such as accession numbers, literature references,
 * what molecule type it is, functions of the molecule, and many things more.
 * See class StandardAttributes for different types of attributes to store on a sequence object or -element.
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class Attributes {
    private HashMap<String, ArrayList<String>> attributes;
    private ArrayList<LiteratureReference> references;

    public Attributes() {
        attributes = new HashMap<>();
    }

    /**
     * add an annotation to the collection
     *
     * @param name
     * @param content
     */
    public void addAttribute(String name, String content) {
        if (!attributes.containsKey(name)) {
            attributes.put(name, new ArrayList<>());
        }
        attributes.get(name).add(content);
    }

    /**
     * Returns an unmodifiable view of the given annotation type
     * @param type
     * @return
     */
    public List<String> getAttributesOfType(String type) {
        if (attributes.containsKey(type)) {
            return Collections.unmodifiableList(attributes.get(type));
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Get an value for a certain type of annotation; if none are present, an
     * empty string will be returned.
     * If more than one are present, only the first value of the list will be returned
     *
     * @param type
     * @return annotation value
     */
    public String getFirstAttributeOfType(String type) {
        if (attributes.containsKey(type)) {
            return attributes.get(type).get(0);
        }
        return null;
    }

    /**
     * test whether a type of annotation is present
     *
     * @param type
     * @return annotation is present
     */
    public boolean containsAttribute(String type) {
        return attributes.containsKey(type);
    }

    /**
     * returns all available Annotation Types
     * @return availableAnnotationTypes
     */
    public Set<String> getAvailableAttributes() {
        return this.attributes.keySet();
    }

    /**
     * add a reference to the list of references
     *
     * @param reference of literature
     */
    public void addLiteratureReference(LiteratureReference reference) {
        if (references == null) {
            this.references = new ArrayList<LiteratureReference>();
        }
        references.add(reference);
    }

    /**
     * get an iterator of the references
     *
     * @return iterator of LiteratureReferences
     */
    public Iterator<LiteratureReference> getReferences() {
        if (references == null) return new ArrayList<LiteratureReference>().iterator();
        return references.iterator();
    }

    /**
     * clone this object
     */
    public Attributes clone() {
        Attributes cloned = new Attributes();
        for (String type : this.getAvailableAttributes()) {
            for (String attribute : this.getAttributesOfType(type)) {
                cloned.addAttribute(type, attribute);
            }
        }

        Iterator<LiteratureReference> refs = this.getReferences();
        while (refs.hasNext()) {
            cloned.addLiteratureReference(refs.next());
        }
        return cloned;
    }

    /**
     * Generates an Attributes object from a Fasta description line
     *
     * @param fastaDescription
     * @return
     */
    public static Attributes fromFastaDescriptionLine(String fastaDescription) {
        //>gi|15215093|gb|AAH12662.1| Fhit protein [Mus musculus]
        Attributes attributes = new Attributes();

        if (! (fastaDescription.contains("|") || fastaDescription.contains("["))) {
            attributes.addAttribute("Name", fastaDescription.substring(1));
            return attributes;
        }

        Pattern withDbIdsPattern = Pattern.compile(">(.+\\|)?(.+?)\\[(.+?)\\] *");
        Matcher m = withDbIdsPattern.matcher(fastaDescription);

        if(m.matches()) {
            //contains DB IDs
            attributes.addAttribute("Name", m.group(2).trim());
            attributes.addAttribute("Organism", m.group(3).trim());
            if (m.group(1) != null) {
                //DB ids
                String[] elements = m.group(1).trim().split("\\|");
                for (int i = 0; i < elements.length - 1; i += 2) {
                    String key = elements[i];
                    String value = elements[i+1];
                    attributes.addAttribute("ID", key + "|" + value);
                }
            }
        } else {
            throw new IllegalArgumentException("Probably not a legal Fasta header: " + fastaDescription);
        }
        return attributes;
    }

    @Override
    public String toString() {
        return "Attributes{" +
                "attributes=" + attributes +
                '}';
    }
}
