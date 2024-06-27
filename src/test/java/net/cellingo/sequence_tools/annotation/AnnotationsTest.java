package net.cellingo.sequence_tools.annotation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Creation date: Jul 10, 2017
 *
 * @author Michiel Noback (&copy; 2017)
 * @version 0.01
 */
public class AnnotationsTest {
    @Test
    public void fromFastaDescriptionSimples() throws Exception {
        String line0 = ">Fhit protein";
        Attributes a = Attributes.fromFastaDescriptionLine(line0);
        assertEquals("Fhit protein", a.getAttributesOfType("Name").get(0));
    }
    
    @Test
    public void fromFastaDescriptionLineWithOrganism() throws Exception {
        String line = ">Fhit protein [Mus musculus]";
        Attributes a = Attributes.fromFastaDescriptionLine(line);
        assertEquals("Fhit protein", a.getAttributesOfType("Name").get(0));
        assertEquals("Mus musculus", a.getAttributesOfType("Organism").get(0));
        System.out.println(a);
    }

    @Test
    public void fromFastaDescriptionLineWithOneDbId() throws Exception {
        String line = ">gi|15215093| Fhit protein [Mus musculus]";
        Attributes a = Attributes.fromFastaDescriptionLine(line);
        assertEquals("Mus musculus", a.getFirstAttributeOfType("Organism"));
        assertTrue(a.getAttributesOfType("ID").contains("gi|15215093"));
    }

    @Test
    public void fromFastaDescriptionLineWithTwoDnIds() throws Exception {
        String line = ">gi|15215093|gb|AAH12662.1| Fhit protein [Mus musculus]";
        Attributes a = Attributes.fromFastaDescriptionLine(line);
        assertEquals("Mus musculus", a.getFirstAttributeOfType("Organism"));
        assertTrue(a.getAttributesOfType("ID").contains("gi|15215093"));
        assertTrue(a.getAttributesOfType("ID").contains("gb|AAH12662.1"));

        line = ">gi|21595364|gb|AAH32336.1| FHIT protein(fragment) [Homo sapiens]";
        a = Attributes.fromFastaDescriptionLine(line);
        assertEquals("Homo sapiens", a.getFirstAttributeOfType("Organism"));
        assertTrue(a.getAttributesOfType("ID").contains("gi|21595364"));
        assertTrue(a.getAttributesOfType("ID").contains("gb|AAH32336.1"));
    }

    @Test
    public void fromFastaDescriptionLineWithThreeDnIds() throws Exception {
        String line = ">gi|15215093|gb|AAH12662.1|TAXID|123456| Fhit protein [Mus musculus]";
        Attributes a = Attributes.fromFastaDescriptionLine(line);
        assertEquals("Mus musculus", a.getFirstAttributeOfType("Organism"));
        assertTrue(a.getAttributesOfType("ID").contains("gi|15215093"));
        assertTrue(a.getAttributesOfType("ID").contains("gb|AAH12662.1"));
        assertTrue(a.getAttributesOfType("ID").contains("TAXID|123456"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromFastaDescriptionLineIllegal() throws Exception {
        String line = "Fhit protein [Mus musculus]";
        Attributes.fromFastaDescriptionLine(line);
    }
}