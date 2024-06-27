/**
 *
 */
package net.cellingo.sequence_tools.io;

import net.cellingo.sequence_tools.annotation.*;
import net.cellingo.sequence_tools.sequences.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used in a composite pattern with SequenceReader. It reads Genbank-formatted
 * sequences and reports back to the SequenceReader when a sequence has finished.
 *
 * @author Cellingo (www.cellingo.net; michiel@cellingo.net)
 * @version 1.0
 *
 * //TODO a major revision and cleaning of this class - required after deleting enum AnnotationType
 */
class GenbankReader implements SequenceReaderDelegate {

    private String firstLine;
    private String currentLine;
    private BufferedReader reader;
    private SequenceReaderListener listener;
    private GenbankEntry gb;
    private int lineNumber = 1;

    public GenbankReader(File file) {
        try {
            this.reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            Logger.getLogger("GenBankReader").log(Level.SEVERE, "Error reading file: " + file);
            throw new IllegalStateException(e);
        }
    }

    public void setListener(SequenceReaderListener listener) {
        this.listener = listener;
    }

    /**
     * when this method is called, the BufferedReader object will be read until a sequence has been processed
     */
    public void readSequences() throws SequenceCreationException {
        gb = new GenbankEntry();
        //Attributes annotations = gb.getAttributes();
        //Organism organism = new Organism();
        //gb.getAttributes().setOrganism(organism);
        try {
            firstLine = reader.readLine();
            processGenbankHeader(firstLine);
            while ((currentLine = reader.readLine()) != null) {
                lineNumber++;
                if (currentLine.startsWith("LOCUS")) {
                    /*a new GenBank sequence starts; report back to listener*/
                    if (listener != null) listener.sequenceRead(gb);
                    gb = new GenbankEntry();
                    //organism = new Organism();
                    //gb.getAttributes().setOrganism(organism);
                    processGenbankHeader(currentLine);
                } else {
					/*continuation of current GenBank sequence*/
                    if (currentLine.startsWith("DEFINITION")) {
                        String definition = currentLine.substring(12);
						/*check next line(s)*/
                        definition = concatMultilineElementTwoPrefixSpaces(definition);
                        gb.getAttributes().addAttribute("DEFINITION", definition);
                    }
                    if (currentLine.startsWith("ACCESSION")) {
                        String accession = currentLine.substring(12);
                        accession = concatMultilineElementTwoPrefixSpaces(accession);
                        gb.getAttributes().addAttribute("ACCESSION", accession);//setAccessionNumber(accession);
                    }
                    if (currentLine.startsWith("VERSION")) {
                        String version = currentLine.substring(12);
                        version = concatMultilineElementTwoPrefixSpaces(version);
                        String[] versionArr = version.split("\\s+");
                        for (String element : versionArr) {
                            gb.getAttributes().addAttribute("VERSION", element);
                        }
                    }
                    if (currentLine.startsWith("KEYWORDS")) {
                        String keywords = currentLine.substring(12);
                        if (!keywords.startsWith(".")) {/*empty keywords element*/
                            keywords = concatMultilineElementTwoPrefixSpaces(keywords);
                            String[] keywrds = keywords.split(",");
                            for (String keywrd : keywrds) {
                                keywrd = keywrd.trim();
                                gb.getAttributes().addAttribute("KEYWORDS", keywrd);
                            }
                        }
                    }
                    if (currentLine.startsWith("SOURCE")) {
                        String source = currentLine.substring(12);
                        currentLine = reader.readLine();
                        lineNumber++;
                        if (currentLine.startsWith("  ORGANISM")) {
                            String latinSpeciesName = currentLine.substring(12);
                            gb.getAttributes().addAttribute("organism", latinSpeciesName);
//                            organism.setFullLatinName(latinSpeciesName);
//                            String lineage = concatMultilineElementTwoPrefixSpaces("");
//                            organism.setLineage(lineage);
                        }
                        gb.getAttributes().addAttribute("organism", source);
                    }
                    if (currentLine.startsWith("REFERENCE")) {
                        processReferences();
                    }
                    if (currentLine.startsWith("FEATURES")) {
                        processFeatures();
                    }
                    if (currentLine.startsWith("ORIGIN")) {
                        processSequence();
                    }
                }
            }
			/*process last GenBank sequence*/
            if (listener != null) {
                listener.sequenceRead(gb);
                listener.sequenceReadingFinished();
            }
        } catch (IOException e) {
            System.out.println("a read error occurred at line " + lineNumber);
            System.out.println("");
            e.printStackTrace();
        }
    }

    private String concatMultilineElement(String elementStart) throws IOException {
        while ((currentLine = reader.readLine()) != null && currentLine.startsWith("            ")) {
            lineNumber++;
            currentLine = currentLine.trim();
            elementStart = elementStart + " " + currentLine;
        }
        lineNumber++;
        return elementStart;
    }

    private String concatMultilineElementTwoPrefixSpaces(String elementStart) throws IOException {
        while ((currentLine = reader.readLine()) != null && currentLine.startsWith("  ")) {
            lineNumber++;
            currentLine = currentLine.trim();
            elementStart = elementStart + " " + currentLine;
        }
        lineNumber++;
        return elementStart;
    }

    private void processSequence() throws IOException, SequenceCreationException {
        StringBuilder sequenceSb = new StringBuilder();
        //check next line(s)
        while ((currentLine = reader.readLine()) != null) {
            lineNumber++;
            if (currentLine.contains("//")) break;

            String seqLine = currentLine.substring(10);
            seqLine = seqLine.toUpperCase();
            seqLine = seqLine.replaceAll(" ", "");
            sequenceSb.append(seqLine);
        }

        SequenceType type = SequenceType.getType(gb.getMoleculeType().toString());
        Sequence sequence;
        if (type == null) {
            sequence = SequenceFactory.createSequence(sequenceSb, SequenceType.DNA);
        } else {
            sequence = SequenceFactory.createSequence(sequenceSb, type);
        }
        //set the  name; try different options;
        if (gb.getAttributes().containsAttribute("locus")) {
            sequence.setSequenceName(gb.getAttributes().getFirstAttributeOfType("locus"));
        } else if (gb.getAttributes().containsAttribute("definition")) {
            sequence.setSequenceName(gb.getAttributes().getFirstAttributeOfType("definition"));
        } else if (gb.getAttributes().containsAttribute("db_xref")) {
            sequence.setSequenceName(gb.getAttributes().getFirstAttributeOfType("db_xref"));
        }

        gb.setSequence(sequence);
    }

    /**
     * processes the features described on the sequence
     *
     * @throws IOException
     */
    private void processFeatures() throws IOException {
        ArrayList<String> featureLines = new ArrayList<String>();
        while ((currentLine = reader.readLine()) != null && currentLine.startsWith("     ")) {
			/*FEATURES listing continued like this
     STS             87328930..87329390
                     /standard_name="PMC19472P1"
                     /db_xref="UniSTS:271775"
     enhancer        complement(87329044..87329625)
     STS             87329243..87329476
                     /standard_name="RH18069"
                     /db_xref="UniSTS:9224"
     gene            complement(87330451..87330515)
			*/
            lineNumber++;
            if (currentLine.startsWith("ORIGIN")) {
                break;
            }
            if (currentLine.startsWith("     source")) {
                processSourceFeature();
            }
            if (!currentLine.startsWith("                     ")) {
                if (featureLines.size() > 0) processFeature(featureLines);
                featureLines.clear();
                featureLines.add(currentLine);
            } else {
                featureLines.add(currentLine);
            }
        }
        if (featureLines.size() > 0) processFeature(featureLines);//last feature
        lineNumber++;
    }

    /**
     * process a single feature
     *
     * @param featureLines
     * @throws IOException
     */
    private void processFeature(ArrayList<String> featureLines) throws IOException {
		/*determine feature type*/
        String[] typeArr = featureLines.get(0).split("\\s+");
        String featureType = typeArr[1];
        String featureCoordinates = typeArr[2];
		/*find out whether there are more lines of coordinates*/
        int elementsStart = 1;
        for (int i = 1; i < featureLines.size(); i++) {
            String line = featureLines.get(i);
            if (line.startsWith("                     /")) {
				/*end of feature coordinates*/
                elementsStart = i;
                break;
            } else {
                featureCoordinates += line.substring(21);
            }
        }
        HashMap<String, ArrayList<String>> featureElements = new HashMap<String, ArrayList<String>>();
		/*get and process feature elements*/
        if (elementsStart < featureLines.size()) {
            featureElements = getFeatureElements(featureLines, elementsStart);
//            ArrayList<String> notes = null;
//            for (Entry<String, ArrayList<String>> entry : featureElements.entrySet()) {
//                ArrayList<String> values = entry.getValue();
//                if (AnnotationType.getType(entry.getKey()) == null) {
//                    notes = new ArrayList<String>();
//                    for (String value : values) {
//                        notes.add("[SequenceTools generated note: NAME=" + entry.getKey() + "; VALUE=" + value + "]");
//                    }
//                }
//            }
//            if (notes != null) {
//                if (featureElements.containsKey("note")) {
//                    featureElements.get("note").addAll(notes);
//                } else {
//                    featureElements.put("note", notes);
//                }
//            }
        }
		
		/*get feature coordinates*/
        ArrayList<SequenceCoordinates> coordinates = getCoordinates(featureCoordinates);

        if (featureType.equals("gene")) {
			/*gene is the basis of most annotations but not all*/
            Gene gene = new Gene(SequenceElement.getNextAutogeneratedId());
			/*unfortunately, not all "gene" features have a "/gene" element; in that case, an alternative is tried*/
            if (featureElements.containsKey("gene")) {
                gene.addAttribute("gene", featureElements.get("gene").get(0));
            } else if (featureElements.containsKey("locus_tag")) {
                gene.addAttribute("locus_tag", featureElements.get("locus_tag").get(0));
            } else if (featureElements.containsKey("db_xref")) {
                gene.addAttribute("db_xref", featureElements.get("db_xref").get(0));
            } else {
				/*no name has been defined for the gene; we have a problem!!*/
                System.out.println("[WARNING GenbankReader.processFeature ] could not assign a name from /gene or /locus_tag name to gene at: " + coordinates.get(0).toString());
            }
            if (coordinates != null && coordinates.size() != 0) {
				/*set the coordinates of the gene; assume genes always (?) have single-coordinate elements*/
                gene.addCoordinates(coordinates.get(0));
            } else {
                System.out.println("[WARNING GenbankReader.processFeature ] could not assign coordinates to gene: ");
            }
			/*process gene feature elements as annotations*/
            for (Entry<String, ArrayList<String>> entry : featureElements.entrySet()) {
                ArrayList<String> values = entry.getValue();
//                AnnotationType type = AnnotationType.getType(entry.getKey());
//                if (type != null) {
                    for (String value : values) {
                        gene.addAttribute(entry.getKey(), value);
                    }
//                }
            }
			/*add the gene to Genbank entry*/
            gb.addElement(gene);
        } else if (featureType.equals("CDS")) {
			/*CDS is a special type of feature. it usually belongs to a gene but not necessarily. If not, one will be created */
            OpenReadingFrame cds = new OpenReadingFrame();
            Attributes annotations = new Attributes();
            cds.setAttributes(annotations);
            cds.setProbablyReal(true);

            if (featureElements.containsKey("translation")) {
                ProteinSequence proteinSequence = new ProteinSequence(featureElements.get("translation").get(0));
                if (featureElements.containsKey("gene")) {
                    proteinSequence.setSequenceName(featureElements.get("gene").get(0));
                    cds.setName(featureElements.get("gene").get(0));
                } else if (featureElements.containsKey("locus_tag")) {
                    proteinSequence.setSequenceName(featureElements.get("locus_tag").get(0));
                    cds.setName(featureElements.get("locus_tag").get(0));
                }

                if (featureElements.containsKey("db_xref")) {
                    for (String xref : featureElements.get("db_xref")) {
						/*a bit superfluous*/
                        proteinSequence.setAccessionNumber(xref);
                        proteinSequence.getAttributes().addAttribute("db_xref", xref);
                    }
                }

                cds.setProteinSequence(proteinSequence);
            }
			/*process gene feature elements as annotations*/
            for (Entry<String, ArrayList<String>> entry : featureElements.entrySet()) {
                ArrayList<String> values = entry.getValue();

                //AnnotationType type = AnnotationType.getType(entry.getKey());
                if (! entry.getKey().equals("translation")) {
                    //(type != null && type != AnnotationType.TRANSLATION) {
                    for (String value : values) {
                        cds.addAttribute(entry.getKey(), value);
                    }
                }
            }
            if (coordinates != null && coordinates.size() != 0) {
				/*set the coordinates of the gene; assume genes always (?) have single-coordinate elements*/
                for (SequenceCoordinates coord : coordinates) {
                    cds.addCoordinates(coord);
                }
            } else {
                System.out.println("[WARNING GenbankReader.processFeature ] could not assign coordinates to CDS: " );
            }

            Gene gene = getGene(featureElements);
            if (gene != null) {
                gene.addOrf(cds);
            } else {
                //System.out.println("[WARNING GenbankReader.processFeature ] could not assign CDS to gene: coordinates=" + cds.getSequenceCoordinates() );
            }
            gb.addElement(cds);
        } else {
			/*all other feature types*/
			/*try to identify the feature type*/
            SequenceElementType etype = SequenceElementType.getType(featureType);
            Gene gene = null;
            if (etype != null && etype != SequenceElementType.UNDEFINED) {//&&
                SequenceElement element;
                if (gb.getMoleculeType() != MoleculeType.PROTEIN) {
                    element = new NucleicAcidSequenceElement(SequenceElement.getNextAutogeneratedId());
                    gene = getGene(featureElements);
                } else {
                    element = new SequenceElement(SequenceElement.getNextAutogeneratedId());
                }
				/*element type should be set before adding the element to the genbank sequence entry !! */
                element.setSequenceElementType(etype);
                //System.out.println("SequenceElementType: " + element.getSequenceElementType());
				
				/*process gene feature elements as annotations*/
                for (Entry<String, ArrayList<String>> entry : featureElements.entrySet()) {
                    ArrayList<String> values = entry.getValue();
                    //AnnotationType at = AnnotationType.getType(entry.getKey());
                    //if (at != null) {
                        for (String value : values) {
                            element.addAttribute(entry.getKey(), value);
                        }
                    //}
                }
				
				/*set the coordinates of the feature*/
                if (coordinates != null && coordinates.size() != 0) {
                    for (SequenceCoordinates coord : coordinates) {
                        element.addCoordinates(coord);
                    }
                } else {
                    System.out.println("[WARNING GenbankReader.processFeature ] could not assign coordinates to element: " + featureType);
                }
				/*add the element to both the gene and the genbank entry as a whole*/
                if (gene != null) {
                    gene.addGeneElement((NucleicAcidSequenceElement) element);
                }
                gb.addElement(element);
            } else {
                System.out.println("unknown feature in sequence; type=" + featureType);
            }
        }
    }

    /**
     * try to get a Gene object based on any of several feature names
     *
     * @param featureElements
     * @return gene object
     */
    private Gene getGene(HashMap<String, ArrayList<String>> featureElements) {
		/*try to get the corresponding gene*/
        Gene gene = null;
        String geneName = null;
        if (featureElements.containsKey("gene")) {
            geneName = featureElements.get("gene").get(0);
        } else if (featureElements.containsKey("locus_tag")) {
            geneName = featureElements.get("locus_tag").get(0);
        } else if (featureElements.containsKey("db_xref")) {
            geneName = featureElements.get("db_xref").get(0);
        }
        if (geneName != null) {
            gene = gb.getGene(geneName);
        }
        return gene;
    }

    /**
     * process coordinates of elements
     *
     * @param coordinates
     * @return list of coordinate elements
     */
    private ArrayList<SequenceCoordinates> getCoordinates(String coordinates) {
        //System.out.println("coords: " + coordinates);
        ArrayList<SequenceCoordinates> dnaCoordinates = new ArrayList<SequenceCoordinates>();
        boolean complete = true;
        boolean complete_5p = true;
        boolean complete_3p = true;
        boolean complete_mid = true;
        boolean complement = false;
        //     mRNA            join(1603..1891,2322..2438,2538..2633,2801..2843,
        //                     2918..3073,3167..3247,3874..3972,4082..4637)

        //                     complement(join(<88170974..88171280,88171367..88171412))

        //     Site            order(25,27..28,35,37,83,90,92,96,98)

        if (coordinates.startsWith("order")) {
			/*Only process start and stop of Sites*/
            coordinates = coordinates.substring(6, coordinates.length() - 1);
            //System.out.println("Site coordinates = " + coordinates);
            coordinates = coordinates.replace('.', 'X');
            String[] siteArr = coordinates.split(",");
            SequenceCoordinates c;
            int start;
            int stop;
            if (siteArr.length == 1) {
                if (siteArr[0].contains("XX")) {
                    String[] spl = siteArr[0].split("XX");
                    start = Integer.parseInt(spl[0]);
                    stop = Integer.parseInt(spl[1]);
                } else {
                    start = Integer.parseInt(siteArr[0]);
                    stop = start;
                }
            } else {
                if (siteArr[0].contains("XX")) {
                    String[] spl = siteArr[0].split("..");
                    start = Integer.parseInt(spl[0]);
                } else {
                    start = Integer.parseInt(siteArr[0]);
                }

                if (siteArr[siteArr.length - 1].contains("XX")) {
                    String[] spl = siteArr[siteArr.length - 1].split("XX");
                    stop = Integer.parseInt(spl[1]);
                } else {
                    stop = Integer.parseInt(siteArr[siteArr.length - 1]);
                }
            }
            c = new SequenceCoordinates(start, stop);
            //System.out.println("Site coordinates object= " + c);
            dnaCoordinates.add(c);
            return dnaCoordinates;
        }
		/*check for complement encoded CDSs*/
        if (coordinates.startsWith("complement")) {//complement encoded feature
            complement = true;
            coordinates = coordinates.substring(11, coordinates.length() - 1);
        }
		/*multiple-element feature (introns/exons)*/
        if (coordinates.startsWith("join")) {
            coordinates = coordinates.substring(5, coordinates.length() - 1);
            String[] coordArr = coordinates.split(",");
            for (int i = 0; i < coordArr.length; i++) {
                String coord = coordArr[i];
                complete = true;
                if (coord.startsWith("<")) {//5'-side incomplete
                    complete = false;
                    complete_5p = false;
                    coord = coord.substring(1);
                }
                if (coord.endsWith(">")) {//3'-incomplete
                    complete = false;
                    complete_3p = false;
                    coord = coord.substring(0, coord.length() - 1);
                }
                if (coord.contains(">") || coord.contains("<")) {
                    complete = false;
                    complete_mid = false;
                    coord = coord.replace('>', 'x');
                    coord = coord.replace('<', 'x');
                }
                int[] start_stop = processSingleElementCoordinates(coord);
                SequenceCoordinates dnaCoordinate = new SequenceCoordinates(start_stop[0], start_stop[1], complement, complete);
                if (!complete) {
                    dnaCoordinate.set5primeIncomplete(complete_5p);
                    dnaCoordinate.set3primeIncomplete(complete_3p);
                    dnaCoordinate.setMidIncomplete(complete_mid);
                }
                dnaCoordinates.add(dnaCoordinate);
            }
        } else {//single-element feature
            //System.out.println("single element coords: " + coordinates);
            if (coordinates.startsWith("<")) {//5'-side incomplete CDS
                complete = false;
                complete_5p = false;
                coordinates = coordinates.substring(1);
            }
            if (coordinates.endsWith(">")) {//3'-incomplete CDS
                complete = false;
                complete_3p = false;
                coordinates = coordinates.substring(0, coordinates.length() - 1);
            }
            if (coordinates.contains(">") || coordinates.contains("<")) {
                complete = false;
                complete_mid = false;
                coordinates = coordinates.replace('>', 'x');
                coordinates = coordinates.replace('<', 'x');
            }
            int[] start_stop = processSingleElementCoordinates(coordinates);
            SequenceCoordinates dnaCoordinate = new SequenceCoordinates(start_stop[0], start_stop[1], complement, complete);
            if (!complete) {
                dnaCoordinate.set5primeIncomplete(complete_5p);
                dnaCoordinate.set3primeIncomplete(complete_3p);
                dnaCoordinate.setMidIncomplete(complete_mid);
            }

            dnaCoordinates.add(dnaCoordinate);
        }
        return dnaCoordinates;
    }

    /**
     * process the coordinates of a basic coordinate element
     *
     * @param elementCoordinates
     * @return
     */
    private int[] processSingleElementCoordinates(String elementCoordinates) {
        int start = 0;
        int stop = 0;
        //funky bypass here because of problems with "." and ".."
        elementCoordinates = elementCoordinates.replace('.', 'x');
        String[] start_stop = elementCoordinates.split("x");
        String startString = start_stop[0];
        start = Integer.parseInt(startString);
        String stopString = start_stop[start_stop.length - 1];
        stop = Integer.parseInt(stopString);
        int[] strt_stop = {start, stop};
        return strt_stop;
    }

    /**
     * process the elements of a feature. The first line(s) does not contain feature elements but the feature
     * type and coordinates
     *
     * @return a map of name - content feature properties
     */
    private HashMap<String, ArrayList<String>> getFeatureElements(ArrayList<String> featureLines, int elementsStart) {
        HashMap<String, ArrayList<String>> featureElements = new HashMap<String, ArrayList<String>>();
        String name = null;
        String content = "";

        for (int i = elementsStart; i < featureLines.size(); i++) {
            String line = featureLines.get(i);
            if (line.startsWith("                     /")) {
				/*a feature starts; process the last one*/
                if (name != null) {
                    if (content.startsWith("\"")) {
                        content = content.substring(1, content.length() - 1);
                    }
                    if (featureElements.containsKey(name)) {
                        featureElements.get(name).add(content);
                    } else {
                        ArrayList<String> elmnts = new ArrayList<String>();
                        elmnts.add(content);
                        featureElements.put(name, elmnts);
                    }
                }
                line = line.substring(22);
                if (line.contains("=")) {
                    String[] split = line.split("=");
                    name = split[0];
                    content = split[1];
                } else {
                    name = line;
					/*this dirty workaround chosen for practical reasons; some elements have no content e.g. /pseudo */
                    content = "true";
                }
            } else {
                content += line.substring(21);
            }
        }
        if (content.startsWith("\"")) {
            content = content.substring(1, content.length() - 1);
        }
        if (featureElements.containsKey(name)) {
            featureElements.get(name).add(content);
        } else {
            ArrayList<String> elmnts = new ArrayList<String>();
            elmnts.add(content);
            featureElements.put(name, elmnts);
        }
        //System.out.println("name=" + name + " content=" + content);
        return featureElements;
    }


    private void processSourceFeature() throws NumberFormatException, IOException {
        while ((currentLine = reader.readLine()) != null && currentLine.startsWith("                     ")) {
            currentLine = currentLine.substring(21);
            lineNumber++;
			/*/organism= has already been read*/
            if (currentLine.startsWith("/db_xref=")) {
                if (currentLine.startsWith("/db_xref=\"taxon:")) {
                    String xref = currentLine.substring(16, (currentLine.length() - 1));
                    //System.out.println(currentLine + " +++" + xref + "+++");

                    int taxID = 0;
                    try {
                        taxID = Integer.parseInt(xref);
                    } catch (NumberFormatException nfe) {
                        System.err.println("could not parse taxID for " + xref + "; I will try again");
						/*	taxID could not be processed for 654" /germline
							taxID could not be processed for 29489" /germline
							unknown feature in sequence; type=source
							taxID could not be processed for 35783" /germline*/
                        if (xref.endsWith("/germline")) {
                            xref = xref.substring(0, xref.length() - 11);
                            //System.err.println(xref);

                            try {
                                taxID = Integer.parseInt(xref);
                            } catch (NumberFormatException nfe2) {
                                System.err.println("No, really could not parse taxID for " + xref);
                            }
                        } else {
                            System.err.println("Sorry, I failed; taxID was set to 0");
                        }
                    }
                    gb.getAttributes().addAttribute("db_xref", "taxon=" + taxID);
                    //gb.getAttributes().addAttribute(AnnotationType.TAX_ID, taxID);//.getOrganism().setTaxonID(taxID);
                } else {
                    String xref = currentLine.substring(10, (currentLine.length() - 1));
                    //System.out.println(xref);
                    gb.getAttributes().addAttribute("db_xref", xref);
                }
            } else if (currentLine.startsWith("/chromosome=")) {
                gb.setChromosome(currentLine.substring(13, (currentLine.length() - 1)));
            } else if (currentLine.startsWith("/map=")) {
                String map = currentLine.substring(6, (currentLine.length() - 1));
                //int mapID = Integer.parseInt(map);
                gb.setMapID(map);
            }
/*			else if(line.startsWith("/mol_type=")){
				///mol_type="genomic DNA"
				String moleculeTypeStr = currentLine.substring(11,(currentLine.length()-1));
				gb.processMoleculeType(moleculeTypeStr);
			}
*/
            else if (currentLine.startsWith("/ecotype=")) {
                String ecoType = currentLine.substring(10, (currentLine.length() - 1));
                gb.getAttributes().addAttribute("ecotype", ecoType);
            }
        }
        lineNumber++;
    }

    /**
     * process a literature reference
     *
     * @throws IOException
     */
    private void processReferences() throws IOException {
		/*
		REFERENCE   1  (bases 1 to 5028)
		  AUTHORS   Torpey,L.E., Gibbs,P.E.,
		            Nelson,J. and Lawrence,C.W.
		  TITLE     Cloning and sequence of REV7, a gene whose function is required for
		            DNA damage-induced mutagenesis in Saccharomyces cerevisiae
		  JOURNAL   Yeast 10 (11), 1503-1509 (1994)
		  PUBMED    7871890
		 * */
        LiteratureReference reference = new LiteratureReference();
		
		/*currentLine holds first line of reference; this one is skipped*/
        while ((currentLine = reader.readLine()) != null && (currentLine.startsWith(" ") || currentLine.startsWith("REFERENCE"))) {
            lineNumber++;
            if (currentLine.contains(" AUTHORS ")) {
                String authorsString = currentLine.substring(12);
                authorsString = concatMultilineElement(authorsString);
                reference.setAuthors(authorsString);
                //System.out.println("authors found: " + authorsString  );
            }
            if (currentLine.contains(" TITLE ")) {
                String titleString = currentLine.substring(12);
                titleString = concatMultilineElement(titleString);
                reference.setTitle(titleString);
            }
            if (currentLine.contains(" JOURNAL ")) {
                String journalString = currentLine.substring(12);
                journalString = concatMultilineElement(journalString);
                reference.setJournal(journalString);
            }
            if (currentLine.contains(" PUBMED ")) {
                String pubmed = currentLine.substring(12);
                int pubmedNumber = Integer.parseInt(pubmed);
                reference.setPubMedNumber(pubmedNumber);
                //System.out.println("pubmed found: " + pubmedNumber );
            }
            if (currentLine.startsWith("REFERENCE")) {
                //System.out.println("new ref found" + currentLine );
                processReferences(); /*recurse*/
            }
            if (!currentLine.startsWith(" ")) break;
        }
        gb.getAttributes().addLiteratureReference(reference);
    }

    /**
     * processes the Genbank format header line
     *
     * @param headerLine
     */
    private void processGenbankHeader(String headerLine) {
		/*LOCUS       SCU49845     5028 bp    DNA             PLN       21-JUN-1999*/
		/*LOCUS       ABM66093                  48 aa            linear   PRI 17-JAN-2007*/
		/*get header elements*/
        String[] firstLineArray = headerLine.split("\\s+");

        int i = 0;
        for (String element : firstLineArray) {
            if (element.length() != 0) {
                if (i == 1) {//second element
                    gb.getAttributes().addAttribute("locus", element);
                }
                if (element.equalsIgnoreCase("DNA")) {
                    gb.setMoleculeType(MoleculeType.DNA);
                } else if (element.equalsIgnoreCase("RNA")) {
                    gb.setMoleculeType(MoleculeType.RNA);
                } else if (element.equalsIgnoreCase("aa")) {
                    gb.setMoleculeType(MoleculeType.PROTEIN);
                } else if (element.equalsIgnoreCase("protein")) {
                    gb.setMoleculeType(MoleculeType.PROTEIN);
                }
                if (i == 5) {
                    gb.setGenBankDivision(element);
                }
                i++;
            }
        }
    }


}
