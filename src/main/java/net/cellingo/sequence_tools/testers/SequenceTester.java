/**
 * 
 */
package net.cellingo.sequence_tools.testers;

import net.cellingo.sequence_tools.alignment.*;
import net.cellingo.sequence_tools.alphabets.IllegalCharacterException;
import net.cellingo.sequence_tools.alphabets.SequenceAlphabet;
import net.cellingo.sequence_tools.annotation.*;
import net.cellingo.sequence_tools.gene_analysis.GeneAnalysisOptions;
import net.cellingo.sequence_tools.gene_analysis.OrfDefinition;
import net.cellingo.sequence_tools.gene_analysis.OrfFinder;
import net.cellingo.sequence_tools.io.SequenceReader;
import net.cellingo.sequence_tools.io.SequenceReaderListener;
import net.cellingo.sequence_tools.io.UnknownSequenceFormatException;
import net.cellingo.sequence_tools.seq_manipulation.SequenceGrabber;
import net.cellingo.sequence_tools.sequence_matching.SequencePatternFinder;
import net.cellingo.sequence_tools.sequences.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceTester {
	ArrayList<SequenceObject> seqList;
	ArrayList<SequenceObject> seqObjList;
	int seqCounter = 0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SequenceTester tester = new SequenceTester();
//		tester.testSequenceReader();
		//tester.testGenbankReader();
        //        tester.testSequenceReader();

//		//testseq contains structure from miRbase
		String testSeqDNAone = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgat"
                + "ttttaggtatcacaacactgtgaccaagaccatcgaaacccacacagacaatatcgagacaaacatggatgaaaacc"
                + "tccgcattcctgtgactgctgaggttggatcaggctacttcaagatgactgatgtgtcctttgacagcgacaccttg"
                + "ggcaaaatcaagatccgcaatggaaagtctgatgcacagatgaaggaagaagatgcggatcttgtcatcactcccgt"
                + "ggagggccgagcactcgaagtgactgtggggcagaatctcacctttgagggaacattcaaggtgtggaacaacacat"
                + "caagaaagatcaacatcactggtatgcagatggtgccaaagattaacccatcaaaggcctttgtcggtagctccaac"
                + "acctcctccttcacccccgtctctattgatgaggatgaagttggcacctttgtgtgtggtaccacctttggcgcacc"
                + "aattgcagctaccgccggtggaaatcttttcgacatgtacgtgcacgtcacctactctggcactgagaccgagtaaa"
                + "taaatcgtgcttttttatatagatagggaattttaatattacaacaataagaaaataaaacaattgaggaaatttat"
                + "accatattttattgacctacttaaccttcttgctatacaatgaatgtttaggtgactggaaaagtttagcaatatta"
                + "tccttgaacgggaaacatgcaccaattacaggcgcaatttcatacgctctcggcctattggtcttttcctggtcata"
                + "cattttagatacaatagacaaaaatggaatgtttgtatagatagaattggcagacaaatctgcagttctcttaatca"
                + "aaatggacaacatgtctattaacaaataagccaacccaaaagtcatggcagtttctgaacacaactcactgttaata"
                + "aattcaggagctgtatgaggatggttactaaagaacctctcatcagttccccaacatttaaaattgtagtacttttt"
                + "acatggtacaattaaaccaaaatcaatcatcttaggttgaccagttattccatcaattactatattgtcacttttta"
                + "tgtccggattcactaatccttgttgagacaaccgagtgacaatatttacaatttctaccaaaacaaagggcaattgg"
                + "tttaacattctctccctcatttttccaacgatagctatggctgaaattgaatccgtaatgggtttcttgcatttaga"
                + "ttgtagaccttcaggcaggcggccagtagcttgaagcatcctaacaccgtacagagtatccctcatcctactaggtt"
                + "gctgtacaacattctcgTCTCGGATCAGATCGAGCCATTGCTGGTTTCTTCCACAGTGGTACTTTCCATTAGAACTA"
                + "TCACCGGGTGGAAACTAGCAGTGGCTCGATCTTTTCCtccattttacatttcaggtctccatttaatacatcaacgc"
                + "taccagaaatttactggaga";

		Sequence sequenceDNAOne = tester.testSequenceCreation(testSeqDNAone, "testing DNA sequence one");
        System.out.println("sequenceDNAOne = " + sequenceDNAOne);
        List<Integer> o = sequenceDNAOne.findOccurrences("ctttc");
        System.out.println("o = " + o.toString());
	}

    /**
	 * tests grabbing of a sequence region based on a list of coordinates, a sequence object and an optional filterobject  
	 */
	public void testSequenceSelectionFilter(){
		
		File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\Haloarcula_marismortui.gb");//a archaeon with translations

		SequenceReader reader = new SequenceReader( file );
		
		try {
			reader.read();
			seqObjList = reader.getSequenceList();
			SequenceObject seqObject = seqObjList.get(0);
			System.out.println("sequence object has type: " + seqObject.getSequenceObjectType());
			
			/*simple sequence fetching using a list of coordinates*/
			ArrayList<SequenceCoordinates> coords = new ArrayList<SequenceCoordinates>();
			coords.add( new SequenceCoordinates(0, 40) ); //edited to be low-complexity
			coords.add( new SequenceCoordinates(600, 1000) ); //overlaps with "gene" element rrlA-2 and "rRNA" and "operon" element rrnB at complement(621..3542)
			coords.add( new SequenceCoordinates(6900, 7500) ); //overlaps with "gene" and "CDS" elements cdc6b at 6826..8064
			coords.add( new SequenceCoordinates(12280, 13310) ); //overlaps with "repeat_region" at 12262..13330
			
			List<Sequence> grabbedList;
			
			SequenceGrabber seqGrabber = new SequenceGrabber( );
			grabbedList = seqGrabber.filterSequence(seqObject, coords);
			System.out.println("\nsimple sequence grabbing");
			for( Sequence seq : grabbedList ){
				System.out.println( "grabbed seq: " + seq.getSequenceName() + " accession=" + seq.getAccessionNumber() + " seq=" + seq.getSequenceString() );
			}
			
			/*with up-and downstream regions*/
/*			seqGrabber.setIncludeDownstream(100);
			seqGrabber.setIncludeUpstream(10);
			grabbedList = seqGrabber.filterSequence(seqObject, coords);
			System.out.println("\nwith up- and downstream inclusion");
			for( Sequence seq : grabbedList ){
				System.out.println( "grabbed seq: " + seq.getSequenceName() + " accession=" + seq.getAccessionNumber() + " seq=" + seq.getSequenceString() );
			}

			seqGrabber = new SequenceGrabber( );
*/			
			/*with a custom filter*/
/*			seqGrabber.addSequenceFilter(new SequenceWordFilter("GGGG"));
			grabbedList = seqGrabber.filterSequence(seqObject, coords);
			System.out.println("\nwith a custom filter: sequence should contain \"GGGG\" ");
			for( Sequence seq : grabbedList ){
				System.out.println( "grabbed seq: " + seq.getSequenceName() + " accession=" + seq.getAccessionNumber() + " seq=" + seq.getSequenceString() );
			}
			
			seqGrabber = new SequenceGrabber( );
*/			
			/*with a custom filter*/
/*			seqGrabber.addSequenceFilter(new LowComplexityFilter());
			
			grabbedList = seqGrabber.filterSequence(seqObject, coords);
			System.out.println("\nwith a custom filter: sequence should not be low complexity ");
			for( Sequence seq : grabbedList ){
				System.out.println( "grabbed seq: " + seq.getSequenceName() + " accession=" + seq.getAccessionNumber() + " seq=" + seq.getSequenceString() );
			}

			seqGrabber = new SequenceGrabber( );
*/			
			/*with a sequenceElementType inclusive filter*/
			ArrayList<SequenceElementType> includedElements = new ArrayList<SequenceElementType>();
			//includedElements.add( SequenceElementType.REPEAT_REGION );
			//includedElements.add( SequenceElementType.INTRON );
			//includedElements.add( SequenceElementType.OPERON );
			includedElements.add( SequenceElementType.GENE );
			
			seqGrabber.setIncludedElements(includedElements);
			
			System.out.println("\nwith an inclusive SequenceElementType filter: sequence should overlap with given types ");
			grabbedList = seqGrabber.filterSequence(seqObject, coords);
			for( Sequence seq : grabbedList ){
				System.out.println( "grabbed seq: " + seq.getSequenceName() + " accession=" + seq.getAccessionNumber() + " seq=" + seq.getSequenceString() );
			}

			
			seqGrabber = new SequenceGrabber( );
			
			/*with both a sequenceElementType inclusive AND exclusive filter*/
			includedElements = new ArrayList<SequenceElementType>();
			//includedElements.add( SequenceElementType.REPEAT_REGION );
			//includedElements.add( SequenceElementType.INTRON );
			includedElements.add( SequenceElementType.OPERON );
			//includedElements.add( SequenceElementType.GENE );

			ArrayList<SequenceElementType> excludedElements = new ArrayList<SequenceElementType>();
			//excludedElements.add( SequenceElementType.REPEAT_REGION );
			//excludedElements.add( SequenceElementType.INTRON );
			//excludedElements.add( SequenceElementType.OPERON );
			excludedElements.add( SequenceElementType.GENE );

			seqGrabber.setIncludedElements(includedElements);
			seqGrabber.setExcludedElements(excludedElements);
			
			System.out.println("\nwith an inclusive AND an exclusive SequenceElementType filter: sequence should overlap with given inclusive types but not with exclusive ");
			grabbedList = seqGrabber.filterSequence(seqObject, coords);
			for( Sequence seq : grabbedList ){
				System.out.println( "grabbed seq: " + seq.getSequenceName() + " accession=" + seq.getAccessionNumber() + " seq=" + seq.getSequenceString() );
			}
			
		} catch (UnknownSequenceFormatException e) {
			e.printStackTrace();
		} catch (SequenceCreationException e) {
			e.printStackTrace();
		}

		
		
	}
	
	public void testProteinAlignment( ArrayList<Sequence> list ){
 		AlignmentOptions alignmentOptions = new AlignmentOptions(list.get(0).getSequenceType(),AlignmentMatrixType.PROTEIN_BLOSUM62);
		alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.SW_SCORE, 45);
		
		AlignmentController aligner = new AlignmentController(AlignmentAlgorithm.LOCAL, AlignmentStrategy.INTERMOLECULAR_FIRST_TO_ALL, alignmentOptions, list);
		aligner.start();
		ArrayList<Alignment> alignmentList = aligner.getAlignmentList();
		
		for( Alignment alignment : alignmentList){
			System.out.println("Alignment of " + alignment.getTopParent().getParentSequence().getSequenceName() );
			System.out.println( "score=" + alignment.getPropertyValue(AlignmentProperty.SW_SCORE) + " relScore=" + alignment.getPropertyValue(AlignmentProperty.RELATIVE_SCORE));

			System.out.println( "" + (alignment.getTopParent().getSequenceCoordinates().getStart() ) );
			System.out.print(alignment.getTopStrand() );
			System.out.println( "   " + (alignment.getTopParent().getSequenceCoordinates().getStop() ) );
			System.out.println(alignment.getMiddleLine() );
			System.out.print(alignment.getBottomStrand() );
			System.out.println( "   " + alignment.getBottomParent().getSequenceCoordinates().getStop() );
			System.out.println( alignment.getBottomParent().getSequenceCoordinates().getStart() );
			System.out.println("");
		}
		
	}

	
	public void testIntermolecularAlignment( ArrayList<Sequence> list ){
 		AlignmentOptions alignmentOptions = new AlignmentOptions(list.get(0).getSequenceType(),AlignmentMatrixType.SIMPLE_MATCH_ALIGNMENT);
		alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.SW_SCORE, 25);
		
		AlignmentController aligner = new AlignmentController(AlignmentAlgorithm.LOCAL, AlignmentStrategy.INTERMOLECULAR_FIRST_TO_ALL, alignmentOptions, list);
		aligner.start();
		ArrayList<Alignment> alignmentList = aligner.getAlignmentList();
		
		for( Alignment alignment : alignmentList){
			System.out.println("Alignment of " + alignment.getTopParent().getParentSequence().getSequenceName() );
			System.out.println( "score=" + alignment.getPropertyValue(AlignmentProperty.SW_SCORE) + " relScore=" + alignment.getPropertyValue(AlignmentProperty.RELATIVE_SCORE));

			System.out.println( "" + (alignment.getTopParent().getSequenceCoordinates().getStart() ) );
			System.out.print(alignment.getTopStrand() );
			System.out.println( "   " + (alignment.getTopParent().getSequenceCoordinates().getStop() ) );
			System.out.println(alignment.getMiddleLine() );
			System.out.print(alignment.getBottomStrand() );
			System.out.println( "   " + alignment.getBottomParent().getSequenceCoordinates().getStop() );
			System.out.println( alignment.getBottomParent().getSequenceCoordinates().getStart() );
			System.out.println("");
		}
	}

	
	
	public void testIntramolecularAlignment( ArrayList<Sequence> list ){
		System.out.println("starting alignment ");
 		AlignmentOptions alignmentOptions = new AlignmentOptions(list.get(0).getSequenceType(),AlignmentMatrixType.DNA_STRUCTURE_ALIGNMENT);
 		
 		alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.ALIGNMENT_LENGTH, 3);
 		alignmentOptions.setAlsoComplement(true);
		alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.SW_SCORE, 1);
		
		
		AlignmentController aligner = new AlignmentController(AlignmentAlgorithm.GLOBAL, AlignmentStrategy.INTRAMOLECULAR, alignmentOptions, list);
		aligner.start();
		ArrayList<Alignment> alignmentList = aligner.getAlignmentList();
		
		for( Alignment alignment : alignmentList){
			HairpinAlignment hairpin = (HairpinAlignment)alignment;
			System.out.println("Alignment of " + hairpin.getTopParent().getParentSequence().getSequenceName() );
			System.out.print( "score=" + hairpin.getPropertyValue(AlignmentProperty.SW_SCORE) + " relScore=" + hairpin.getPropertyValue(AlignmentProperty.RELATIVE_SCORE));
			System.out.print( " length=" + hairpin.getPropertyValue(AlignmentProperty.HAIRPIN_LENGTH) + " loopLength=" + hairpin.getPropertyValue(AlignmentProperty.LOOP_LENGTH) );
			System.out.println( " strand=" + (hairpin.isOnComplement()?"reverse":"forward") );
			//TODO get the positions of the strands right
			System.out.println( "" + hairpin.getTopParent().getSequenceCoordinates().getStart()  );
			System.out.print(hairpin.getTopStrand() );
			System.out.println( "   " + hairpin.getTopParent().getSequenceCoordinates().getStop() );
			System.out.println( hairpin.getMiddleLine() );
			System.out.print( hairpin.getBottomStrand() );
			System.out.println( "   " + hairpin.getBottomParent().getSequenceCoordinates().getStop() );
			System.out.println( hairpin.getBottomParent().getSequenceCoordinates().getStart() );
			System.out.println("");
			
		}
	}
	
	public void testOldSequenceAlignment( ArrayList<Sequence> list ){
				
		//SIMPLE MATCH ALIGNMENT
 		AlignmentOptions alignmentOptions = new AlignmentOptions(list.get(0).getSequenceType(),AlignmentMatrixType.SIMPLE_MATCH_ALIGNMENT);
		alignmentOptions.setMatchingCharacterIsAlignmentCharacter(true);
		alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.SW_SCORE, 20);
		
/*		SmithWatermanAnalyser swAnalyser = new SmithWatermanAnalyser(AlignmentAlgorithm.LOCAL, AlignmentStrategy.INTERMOLECULAR_FIRST_TO_ALL, alignmentOptions, list);
		//TODO process correct positions of this type of alignment
		
		//TODO do correct "alsoComplement" analysis on all nucleic acid alignments
		
		swAnalyser.start();
		ArrayList<TextAlignment> alignments = swAnalyser.getAlignmentTextList();
		
		for(TextAlignment al: alignments){
			System.out.println("__alignment of " + al.getParentSequenceTopStrand().getSequenceName() + " and " + al.getParentSequenceBottomStrand().getSequenceName());
			System.out.println(" on strand: " + al.isOnForwardStrand() + ", score: " + al.getAlignmentScore());// + "; looplength: " + al.getLoopSequence().length()
			System.out.println(al.getStartPositionTopStrand());
			System.out.println("  " + al.getTopStrand() + "  " + al.getEndPositionTopStrand());
			System.out.println("  " + al.getMiddleLine());
			System.out.println("  " + al.getBottomStrand() + "  " + al.getEndPositionBottomStrand());
			System.out.println(al.getStartPositionBottomStrand());
		}
*/		
		
	}
	
	public void testOrfFinder( Sequence sequence ){
		
		System.out.println( "testing ORF analysis");
		
		GeneAnalysisOptions options = new GeneAnalysisOptions();
		options.setMinimumOrfSize(100);
		options.setOrfDefinition(OrfDefinition.ATG_TO_STOP);
		options.setStrandSelection(SequenceStrand.BOTH);
		OrfFinder orfFinder = new OrfFinder( (NucleicAcidSequence)sequence , options );

		orfFinder.start();
		
		ArrayList<OpenReadingFrame> orfList = orfFinder.getOrfList();
		
		for(OpenReadingFrame orf : orfList ){
			System.out.println( "ORF coordinates: " + orf.getSequenceCoordinates() );
			//System.out.println( "ORF ID: " + orf.getAutoGeneratedElementId() );
			System.out.println( "ORF prot seq: " + orf.getProteinSequence().getSequenceString() );
			System.out.println( "ORF DNA  seq: " + orf.getParentSequenceRegion().getSequenceString() );
		}
		
	}
	
	public void testSequencePatternFinder( Sequence sequence ){
		//
		SequencePatternFinder patternFinder = new SequencePatternFinder( sequence, "aaanaaa", true );
		patternFinder.findPatterns();
		System.out.println( "total matches: " + patternFinder.getMatchNumber() );
		System.out.println( "message: " + patternFinder.getReturnMessage() );
		
		ArrayList<SequenceElement> matches = patternFinder.getMatches();
		for(SequenceElement match : matches){
			System.out.println( "match start: " + match.getSequenceCoordinates().getStart() + " C=" + ((NucleicAcidSequenceElement)match).getSequenceCoordinates().isComplement() + " match sequence=" + match.getParentSequenceRegion().getSequenceString() );
		}
		
	}
	
	public void testMultiFastaSequenceReader(){
		//read a MultiFasta file
		File file = new File("E:\\workspace\\SequenceTools\\sample_data\\pac185a4.fas");
		SequenceReader reader = new SequenceReader( file );
		try {
			reader.read();
			List<SequenceObject> seqList = reader.getSequenceList();
			for(SequenceObject seq : seqList){
				System.out.println("sequence object has type: " + seq.getSequenceObjectType());
				System.out.println("sequence object has name: " + seq.getSequenceName() );
				//System.out.println("accNo: " + seq.getAttributes().getFirstAttributeOfType(AnnotationType.ACCESSION) );
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGenbankReader(){
		//read a GenBank file
		//File file = new File("C:\\Users\\Michiel\\projects\\workspace_general\\SequenceTools\\sample_data\\genBank.gb");//a small sample file
		//File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\Methanoculleus_marisnigri.gb");//a methanogen
		File file = new File( "/share/home/michiel/tmp/thema12/rdp_download_92seqs.gen");
		//File file = new File( "/data/storage2/tdeboer/RDP/rdp_bacteria.gen");
		
		//File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\Haloarcula_marismortui.gb");//a archaeon with translations
		//File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\Bacillus_subtilis.gb");//a B subtilis with translations		
		//File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\at_chrom4.gb"); //arabidopsis chromosome
		//File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\hs_chrom14.gb"); //human chromosome
		//File file = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\Fhit_protein.gb");//a mouse FHIT protein		
		SequenceReader reader = new SequenceReader( file );
		
		reader.addSequenceReaderListener(new SequenceReaderListener() {
			
			@Override
			public void sequenceReadingFinished() {
				System.out.println("IO finished; " + seqCounter + " sequences read");
			}
			
			@Override
			public void sequenceRead(SequenceObject sequenceObject) {
				seqCounter++;
			}
		});
		
		try {
			reader.read();
		} catch (UnknownSequenceFormatException e) {
			e.printStackTrace();
		} catch (SequenceCreationException e) {
			e.printStackTrace();
		}
		
//		try {
//			reader.read();
//			seqObjList = reader.getSequenceList();
//			for(SequenceObject seqObject : seqObjList){
//				System.out.println("sequence object has type: " + seqObject.getSequenceObjectType());
//				System.out.println("sequence object has name: " + seqObject.getSequenceName() );
//				System.out.println("sequence object has sequence: " + seqObject.getSequence().toString() );
//				System.out.println("db-xref=" + seqObject.getAttributes().getOrganism().getTaxonID());
//				
//				if(seqObject.getSequenceObjectType() == SequenceObjectType.GENBANK_SEQUENCE ){
//					GenbankEntry gbEntry = (GenbankEntry) seqObject;
//					List<Gene> genes = gbEntry.getGeneList();
//					for( Gene gene : genes){
//						
//						System.out.println("gene name: " + gene.getName() );
//						if( gene.hasElementType(SequenceElementType.CDS)){
//							SequenceElement cds = gene.getFirstElementOf(SequenceElementType.CDS);
//							OpenReadingFrame orf = (OpenReadingFrame)cds;
//							System.out.println("gene translation: " + orf.getName() + "; type=" + orf.getProteinSequence().getSequenceType() + " \n" + orf.getProteinSequence().getSequenceString() );
//						}
//					}
//				}
//				AnnotatedSequence as = (AnnotatedSequence)seqObject;
//				Sequence bs = as.getSequence();
//				
//			}
//		} catch (UnknownSequenceFormatException e) {
//			e.printStackTrace();
//		} catch (SequenceCreationException e) {
//			e.printStackTrace();
//		}
		
	}

	public void testSequenceAlphabets( ){
		System.out.println( "testing alphabets");
		try {
			System.out.println( "get alphabet letter DNA: A=" + SequenceAlphabet.ALPHABET_DNA.getAlphabetCharacterObject('A').getFullName());
			System.out.println( "get alphabet letter RNA: A=" + SequenceAlphabet.ALPHABET_RNA.getAlphabetCharacterObject('A').getFullName());
			//System.out.println( "get alphabet letter RNA: X=" + Sequence.ALPHABET_RNA.getAlphabetCharacterObject('X').getFullName());
			System.out.println( "A & C ambiguous match:" + SequenceAlphabet.ALPHABET_DNA.checkAmbiguousCodeMatch('A', 'C') );
			System.out.println( "N & C ambiguous match:" + SequenceAlphabet.ALPHABET_DNA.checkAmbiguousCodeMatch('N', 'C') ); //TODO is this desirable behaviour?
		} catch (IllegalCharacterException e) {
			e.printStackTrace();
		}
	}
	
	public void testSequenceCloning( Sequence seq ){
		System.out.println("cloning sequence; ID= " + seq.getProgramSequenceID() );
		System.out.println( seq );
		Sequence cloned = (Sequence) seq.clone();
		System.out.println("cloned sequence; ID= " + cloned.getProgramSequenceID() );
		System.out.println( cloned );
		
	}
	
	public void testSequenceManipulation( Sequence seq ){
		System.out.println("reversing sequence: ");
		System.out.println( seq );
		//seq.reverse();
		System.out.println("reversed sequence: ");
		System.out.println( seq );

		try {
			System.out.println("get subsequence ArrayBoundsSafe of first and last 20 characters of sequence: ");
			System.out.println( seq );
			Sequence subseq20 = seq.getSubSequenceArrayBoundsSafe(0,19);
			System.out.println("subsequence first 20: ");
			System.out.println( subseq20 );

			Sequence subseqlast20 = seq.getSubSequenceArrayBoundsSafe( seq.getSequenceLength() - 19, seq.getSequenceLength());
			System.out.println("subsequence last 20: ");
			System.out.println( subseqlast20 );
			
			System.out.println("get subsequence not ArrayBoundsSafe of first and last 20 characters of sequence: ");
			//System.out.println( seq );
			Sequence subseqNS20 = seq.getSubSequence(0,19);
			System.out.println("subsequence first 20: ");
			System.out.println( subseqNS20 );

			Sequence subseqlastNS20 = seq.getSubSequence( seq.getSequenceLength() - 19, seq.getSequenceLength());
			System.out.println("subsequence last 20: ");
			System.out.println( subseqlastNS20 );
			
			System.out.println("get subsequence not ArrayBoundsSafe with illegal positions -1 - 9: ");
			Sequence subseqNSI20 = seq.getSubSequence( -1, 9);
			System.out.println("subsequence: ");
			System.out.println( subseqNSI20 );


		} catch (IllegalSequenceOperation e) {
			System.out.println(e.getMessage());
		}

	}
	
	public Sequence testSequenceCreation(String seq, String name){
		//testing sequence creation methods
		
		//System.out.println("received sequence: " + seq);
		
		try {
			Sequence sequenceOne = SequenceFactory.createSequence(seq);
			//System.out.println("created Sequence sequence has type: " + sequenceOne.getSequenceType() );
			//System.out.println("created Sequence sequence has length: " + sequenceOne.getSequenceLength() );
			
			//System.out.println("set sequence name sequence: " + name);
			sequenceOne.setSequenceName( name );
			return sequenceOne;
			
		} catch (SequenceCreationException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}


}
