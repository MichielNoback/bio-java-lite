package net.cellingo.sequence_tools.gene_analysis;

import net.cellingo.sequence_tools.seq_manipulation.SequenceFilter;
import net.cellingo.sequence_tools.sequences.SequenceStrand;

public class SequenceAnalysisOptions {
	//start position for analysis
	private int startAnalysisPosition;
	//stop position for analysis
	private int stopAnalysisPosition;
	//analyze one strand or both
	private SequenceStrand strandSelection;
	//possible filter
	private SequenceFilter filter;

	
	public SequenceAnalysisOptions(){
		startAnalysisPosition = 0;
		stopAnalysisPosition = 0;
		strandSelection = SequenceStrand.BOTH;
	}

	/**
	 * set the strand selection: both, reverse or forward
	 * @param strandSelection
	 */
	public void setStrandSelection( SequenceStrand strandSelection ){
		this.strandSelection = strandSelection;
	}
	/**
	 * get the strand selection
	 * @return strandSelection
	 */
	public SequenceStrand getStrandSelection(){
		return strandSelection;
	}
	/**
	 * set the start position at which to begin analysis 
	 * @param start
	 */
	public void setStartAnalysisPosition(int start){
		startAnalysisPosition = start;
	}
	/**
	 * get the start position at which analysis begins 
	 * @return startPosition
	 */
	public int getStartAnalysisPosition(){
		return startAnalysisPosition;
	}
	/**
	 * set the position at which to end analysis
	 * @param stop
	 */
	public void setStopAnalysisPosition(int stop){
		stopAnalysisPosition = stop;
	}
	/**
	 * get the position at which to end analysis
	 * @return stop
	 */
	public int getStopAnalysisPosition(){
		return stopAnalysisPosition;
	}

	/**
	 * @return the filter
	 */
	public SequenceFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(SequenceFilter filter) {
		this.filter = filter;
	}

}
