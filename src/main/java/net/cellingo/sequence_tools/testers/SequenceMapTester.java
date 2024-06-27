/**
 * 
 */
package net.cellingo.sequence_tools.testers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import net.cellingo.sequence_tools.alignment.Alignment;
import net.cellingo.sequence_tools.alignment.AlignmentAlgorithm;
import net.cellingo.sequence_tools.alignment.AlignmentController;
import net.cellingo.sequence_tools.alignment.AlignmentMatrixType;
import net.cellingo.sequence_tools.alignment.AlignmentOptions;
import net.cellingo.sequence_tools.alignment.AlignmentProperty;
import net.cellingo.sequence_tools.alignment.AlignmentStrategy;
import net.cellingo.sequence_tools.alignment.HairpinAlignment;
import net.cellingo.sequence_tools.annotation.OpenReadingFrame;
import net.cellingo.sequence_tools.gene_analysis.GeneAnalysisOptions;
import net.cellingo.sequence_tools.gene_analysis.OrfDefinition;
import net.cellingo.sequence_tools.gene_analysis.OrfFinder;
import net.cellingo.sequence_tools.graphics.SequenceMap;
import net.cellingo.sequence_tools.graphics.SequenceTrack;
import net.cellingo.sequence_tools.graphics.SequenceTrackElement;
import net.cellingo.sequence_tools.sequences.*;
import net.cellingo.sequence_tools.sequences.Sequence;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceMapTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SequenceMapTester tester = new SequenceMapTester();
		
		String testSeqDNAone = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgatttttaggtatcacaacactgtgaccaagaccatcgaaacccacacagacaatatcgagacaaacatggatgaaaacctccgcattcctgtgactgctgaggttggatcaggctacttcaagatgactgatgtgtcctttgacagcgacaccttgggcaaaatcaagatccgcaatggaaagtctgatgcacagatgaaggaagaagatgcggatcttgtcatcactcccgtggagggccgagcactcgaagtgactgtggggcagaatctcacctttgagggaacattcaaggtgtggaacaacacatcaagaaagatcaacatcactggtatgcagatggtgccaaagattaacccatcaaaggcctttgtcggtagctccaacacctcctccttcacccccgtctctattgatgaggatgaagttggcacctttgtgtgtggtaccacctttggcgcaccaattgcagctaccgccggtggaaatcttttcgacatgtacgtgcacgtcacctactctggcactgagaccgagtaaataaatcgtgcttttttatatagatagggaattttaatattacaacaataagaaaataaaacaattgaggaaatttataccatattttattgacctacttaaccttcttgctatacaatgaatgtttaggtgactggaaaagtttagcaatattatccttgaacgggaaacatgcaccaattacaggcgcaatttcatacgctctcggcctattggtcttttcctggtcatacattttagatacaatagacaaaaatggaatgtttgtatagatagaattggcagacaaatctgcagttctcttaatcaaaatggacaacatgtctattaacaaataagccaacccaaaagtcatggcagtttctgaacacaactcactgttaataaattcaggagctgtatgaggatggttactaaagaacctctcatcagttccccaacatttaaaattgtagtactttttacatggtacaattaaaccaaaatcaatcatcttaggttgaccagttattccatcaattactatattgtcactttttatgtccggattcactaatccttgttgagacaaccgagtgacaatatttacaatttctaccaaaacaaagggcaattggtttaacattctctccctcatttttccaacgatagctatggctgaaattgaatccgtaatgggtttcttgcatttagattgtagaccttcaggcaggcggccagtagcttgaagcatcctaacaccgtacagagtatccctcatcctactaggttgctgtacaacattctcgTCTCGGATCAGATCGAGCCATTGCTGGTTTCTTCCACAGTGGTACTTTCCATTAGAACTATCACCGGGTGGAAACTAGCAGTGGCTCGATCTTTTCCtccattttacatttcaggtctccatttaatacatcaacgctaccagaaatttactggaga";
		Sequence sequenceOne = null;
		try {
			sequenceOne = SequenceFactory.createSequence(testSeqDNAone);
		} catch (SequenceCreationException e1) {
			e1.printStackTrace();
		}
		sequenceOne.setSequenceName("testSeq");
		
		tester.testORFanalysisMap( sequenceOne );
		
		//tester.testHairpinAnalysisMap( sequenceOne );
		
	}

	@SuppressWarnings("unused")
	private void testHairpinAnalysisMap(Sequence sequence ) {
		List<Sequence> list = new ArrayList<Sequence>();
		list.add( sequence );
		
 		AlignmentOptions alignmentOptions = new AlignmentOptions( sequence.getSequenceType(),AlignmentMatrixType.RNA_STRUCTURE_ALIGNMENT);
 		alignmentOptions.setAlsoComplement(true);
		alignmentOptions.getAlignmentPropertiesMinimumValues().setProperty(AlignmentProperty.SW_SCORE, 20);
		
		AlignmentController aligner = new AlignmentController(AlignmentAlgorithm.LOCAL, AlignmentStrategy.INTRAMOLECULAR_SLIDING_WINDOW, alignmentOptions, list);
		aligner.start();
		List<Alignment> alignmentList = aligner.getAlignmentList();
		
		SequenceTrack trackFw = new SequenceTrack();
		trackFw.setTrackName("FW hairpins");
		trackFw.setTrackColor( new Color(200,100,100) );
		SequenceTrack trackRv = new SequenceTrack();
		trackRv.setTrackName("RV hairpins");
		trackRv.setTrackColor( new Color(100,200,100) );

		
		int count = 0;
		for( Alignment alignment : alignmentList){
			HairpinAlignment hairpin = (HairpinAlignment)alignment;
/*			System.out.println("Alignment of " + hairpin.getTopParent().getParentSequence().getSequenceName() );
			System.out.print( "score=" + hairpin.getPropertyValue("swScore") + " relScore=" + hairpin.getPropertyValue("relativeScore"));
			System.out.print( " length=" + hairpin.getPropertyValue("hairpinLength") + " loopLength=" + hairpin.getPropertyValue("loopLength") );
			System.out.println( " strand=" + (hairpin.isOnComplement()?"reverse":"forward") );
			System.out.println( "" + hairpin.getTopParent().getParentStart()  );
			System.out.print(hairpin.getTopStrand() );
			System.out.println( "   " + hairpin.getTopParent().getParentStop() );
			System.out.println( hairpin.getMiddleLine() );
			System.out.print( hairpin.getBottomStrand() );
			System.out.println( "   " + hairpin.getBottomParent().getParentStop() );
			System.out.println( hairpin.getBottomParent().getParentStart() );
			System.out.println("");
*/
			count++;
			SequenceTrackElement ste = SequenceTrackElement.createSequenceTrackElement( hairpin );
			if(count%3 == 0){
				ste.setColor( new Color(100,100,200) );
			}
			if( hairpin.isOnComplement() ){
				trackRv.addSequenceTrackElement(ste);
			}
			else{
				trackFw.addSequenceTrackElement(ste);
			}
		}
		//create a test map
		SequenceMap map = new SequenceMap( sequence.getSequenceLength() - 300 , 800 );
		map.setMapTitle(sequence.getSequenceName());
		map.setMapTitleHeight(15);
		map.setRulerHeight(20);
		//map.setSequenceLength( sequence.getSequenceLength() - 300 );
		map.setSequenceStartPosition(200);
		map.setBackground( new Color(250,250,250) );
		//map.setCollapseOverlapping(false);
		//map.setCreateRuler(false);
		map.addSequenceTrack(trackFw);
		map.addSequenceTrack(trackRv);
		
		BufferedImage image = map.drawMap();
		
//		response.setContentType("image/png");
		File out = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\hairpin_map_detail.png");
		
		try {
			ImageIO.write( image, "png" /* "png" "jpeg" format desired, no "gif" yet. */,  out /* target */ );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void testORFanalysisMap(Sequence sequenceOne) {
		//find ORFs
		GeneAnalysisOptions options = new GeneAnalysisOptions();
		options.setMinimumOrfSize(20);
		options.setOrfDefinition(OrfDefinition.START_TO_STOP);
		options.setStrandSelection(SequenceStrand.BOTH);
		OrfFinder orfFinder = new OrfFinder( (NucleicAcidSequence)sequenceOne , options );

		orfFinder.start();
		
		ArrayList<OpenReadingFrame> orfList = orfFinder.getOrfList();

		//
		HashMap<Integer, SequenceTrack> tracks = new HashMap<Integer, SequenceTrack>();
		//create tracks
		SequenceTrack track1 = new SequenceTrack();
		track1.setTrackName("+1");
		tracks.put(1, track1);
		SequenceTrack track2 = new SequenceTrack();
		track2.setTrackName("+2");
		tracks.put(2, track2);
		SequenceTrack track3 = new SequenceTrack();
		track3.setTrackName("+3");
		tracks.put(3, track3);
		SequenceTrack track4 = new SequenceTrack();
		track4.setTrackName("-1");
		tracks.put(4, track4);
		SequenceTrack track5 = new SequenceTrack();
		track5.setTrackName("-2");
		tracks.put(5, track5);
		SequenceTrack track6 = new SequenceTrack();
		track6.setTrackName("-3");
		tracks.put(6, track6);

		for(OpenReadingFrame orf : orfList ){
			//System.out.println( "ORF coordinates: " + orf.getParentStart() + "-" + orf.getParentStop() + " RF=" + orf.getReadFrame());
			SequenceTrackElement element = SequenceTrackElement.createSequenceTrackElement( orf );
			tracks.get( SequenceFrame.getFrameNumber(orf.getReadFrame()) ).addSequenceTrackElement(element);
			//System.out.println( "ORF ID: " + orf.getAutoGeneratedElementId() );
			//System.out.println( "ORF prot seq: " + orf.getProteinSequence().getSequenceString() );
			//System.out.println( "ORF DNA  seq: " + orf.getParentSequenceRegion().getSequenceString() );
		}
		
		//create a test map
		SequenceMap map = new SequenceMap( sequenceOne.getSequenceLength() - 300, 800 );
		map.setMapTitle(sequenceOne.getSequenceName());
		map.setMapTitleHeight(15);
		map.setRulerHeight(20);
//		/map.setSequenceLength( sequenceOne.getSequenceLength() - 300 );
		map.setSequenceStartPosition(200);

		map.setBackground( new Color(250,250,250) );
		//map.setCreateRuler(false);
		map.addSequenceTrack(track1);
		map.addSequenceTrack(track2);
		map.addSequenceTrack(track3);
		map.addSequenceTrack(track4);
		map.addSequenceTrack(track5);
		map.addSequenceTrack(track6);
		
		map.setCreateImageMap(true);
		BufferedImage image = map.drawMap();
		System.out.println("map:\n" +  map.getImageMap() );
		
//		response.setContentType("image/png");
		File out = new File("C:\\Users\\Michiel\\projects\\sequence_tools\\orf_map_detail.png");
		
		try {
			ImageIO.write( image, "png" /* "png" "jpeg" format desired, no "gif" yet. */,  out /* target */ );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
