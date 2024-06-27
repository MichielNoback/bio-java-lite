/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

import java.util.ArrayList;
import java.util.List;

import net.cellingo.sequence_tools.sequences.Sequence;

/**
 * instances of this object choose which aligner object to instantiate: strategy pattern
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
*/
public class AlignmentController {
	private List<Sequence> sequenceList;
	private AlignmentAlgorithm algorithm;
	private AlignmentStrategy strategy;
	private AlignmentOptions options;
//	private ArrayList<Alignment> alignmentList;
	private Aligner aligner;
	

	
	public AlignmentController( AlignmentAlgorithm algorithm, AlignmentStrategy strategy , AlignmentOptions options, List<Sequence> sequenceList ){
		this.algorithm = algorithm;
		this.strategy = strategy;
		this.options = options;
		this.sequenceList = sequenceList;
		initialize();
	}
	
	/**
	 * initialization procedures: choose which aligner object to instantiate
	 */
	private void initialize(){

		if( strategy == AlignmentStrategy.INTRAMOLECULAR || strategy == AlignmentStrategy.INTRAMOLECULAR_SLIDING_WINDOW ){
			if( algorithm == AlignmentAlgorithm.STRUCTURE ){
				//System.out.println("creating StructureAligner");
				this.aligner = new StructureAligner( strategy, algorithm, options );
			}
			else{
				//System.out.println("creating IntramolecularAligner");
				this.aligner = new IntramolecularAligner( strategy, algorithm, options );
			}
		}
		else if( strategy == AlignmentStrategy.INTERMOLECULAR_ALL_TO_ALL || strategy == AlignmentStrategy.INTERMOLECULAR_FIRST_TO_ALL ){
			//System.out.println("creating IntermolecularAligner");
			this.aligner = new IntermolecularAligner( strategy, algorithm, options );
		}
		
		aligner.setSequences(sequenceList);
	}
	
	/**
	 * get the list of alignments as textual (String-) objects
	 * @return alignmentslist
	 */
	public ArrayList<Alignment> getAlignmentList(){
		return aligner.getAlignmentList();
	}

	/**
	 * call to start the analysis
	 */
	public void start(){
		aligner.doAlignment();
	}
	

	
}
