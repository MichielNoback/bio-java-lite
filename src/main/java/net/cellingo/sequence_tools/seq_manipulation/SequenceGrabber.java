/**
 * 
 */
package net.cellingo.sequence_tools.seq_manipulation;

import net.cellingo.sequence_tools.annotation.*;
import net.cellingo.sequence_tools.sequences.IllegalSequenceOperation;
import net.cellingo.sequence_tools.sequences.Sequence;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to instantiate objects that can be used as a filter for selecting 
 * sequence regions. SequenceSelectionFilter objects provide several methods 
 * that can be used for different filtering purposes.
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceGrabber {
	/*when overlapping with these elements types, sequence regions will be excluded*/
	private List<SequenceElementType> excludedElements;
	/*only when overlapping with these elements types, sequence regions will be included*/
	private List<SequenceElementType> includedElements;
	/*the number of characters to include downstream from the selected SequenceCoordinates*/
	private int includeDownstream = 0;
	/*the number of characters to include upstream from the selected SequenceCoordinates*/
	private int includeUpstream = 0;
	/*a possible custom SequenceFilter object that specifies a single method: public boolean filter( Sequence sequence );*/
	private ArrayList<SequenceFilter> sequenceFilters;
	/*filter strand specific*/
	//private boolean strandSpecific;
	
	/**
	 * default constructor
	 */
	public SequenceGrabber(){ }
	
	/**
	 * set this filter to scan strand specific (only relevant with nucleotide sequences)
	 * @param strandSpecific filter setting
	 */
/*	public void setStrandSpecific(boolean strandSpecific) {
		this.strandSpecific = strandSpecific;
	}
*/
	/**
	 * returns the list of excluded elements
	 * @return the excluded elements
	 */
	public List<SequenceElementType> getExcludedElements(){
		return excludedElements;
	}

	/**
	 * when overlapping with these elements types, sequence regions will be excluded. Only relevant for 
	 * annotated sequences (SequenceObjectType.GENBANK_SEQUENCE or SequenceObjectType.ANNOTATED_SEQUENCE)
	 * @param excludedElements
	 */
	public void setExcludedElements( List<SequenceElementType> excludedElements ){
		this.excludedElements = excludedElements;
	}

	/**
	 * returns the list of included elements
	 * @return the included elements
	 */
	public List<SequenceElementType> getIncludedElements() {
		return includedElements;
	}

	/**
	 * only when overlapping with these elements types, sequence regions will be included. Only relevant for 
	 * annotated sequences (SequenceObjectType.GENBANK_SEQUENCE or SequenceObjectType.ANNOTATED_SEQUENCE)
	 * @param includedElements the includedElements to set
	 */
	public void setIncludedElements( List<SequenceElementType> includedElements ){
		this.includedElements = includedElements;
	}

	/**
	 * returns the number of characters to include downstream from the selected SequenceCoordinates
	 * @return the include downstream number
	 */
	public int getIncludeDownstream() {
		return includeDownstream;
	}

	/**
	 * sets the number of characters to include downstream from the selected SequenceCoordinates
	 * @param includeDownstream
	 */
	public void setIncludeDownstream(int includeDownstream) {
		this.includeDownstream = includeDownstream;
	}

	/**
	 * returns the number of characters to include upstream from the selected SequenceCoordinates
	 * @return the include upstream number
	 */
	public int getIncludeUpstream() {
		return includeUpstream;
	}

	/**
	 * sets the number of characters to include upstream from the selected SequenceCoordinates
	 * @param includeUpstream
	 */
	public void setIncludeUpstream(int includeUpstream) {
		this.includeUpstream = includeUpstream;
	}

	/**
	 * returns the List of filters used as filters steps.
	 * @return the sequence filters
	 */
	public ArrayList<SequenceFilter> getSequenceFilters() {
		return sequenceFilters;
	}

	/**
	 * sets the List of filters used as filters steps. Only when passing all the filters,
	 * a sequence will be accepted
	 * @param sequenceFilters
	 */
	public void setSequenceFilters(ArrayList<SequenceFilter> sequenceFilters) {
		this.sequenceFilters = sequenceFilters;
	}

	/**
	 * add a single sequence filter
	 * @param filter
	 */
	public void addSequenceFilter( SequenceFilter filter ){
		if( this.sequenceFilters == null ) sequenceFilters = new ArrayList<SequenceFilter>();
		this.sequenceFilters.add(filter);
	}
	
	/**
	 * Filter a sequence against all the settings of this filter object. The filter is applied to sequence regions 
	 * specified in the list of coordinates, including the includeUpstream and includeDownstream number of characters. 
	 * @param sequence
	 * @param coordinates list of to scan
	 * @return sequenceRegions list of that were accepted by this filter
	 */
	public List<Sequence> filterSequence(SequenceObject sequence, List<SequenceCoordinates> coordinates ){
		
		List<Sequence> filtered = new ArrayList<Sequence>();
		/*iterate over all coordinates, get the region and filter it*/
		for( SequenceCoordinates coordinate : coordinates ){
			Sequence region = null;
			try {
				region = sequence.getSequence().getSubSequenceArrayBoundsSafe( 
						(coordinate.getStart() - includeUpstream), (coordinate.getStop() + includeDownstream) );
				if( applySequenceFilters( region ) && applyElementsFilter( sequence, coordinate )){
					region.setSequenceName( sequence.getSequenceName() + " [region " + (coordinate.getStart() - includeUpstream) + "-" + (coordinate.getStop() + includeDownstream) + "]");
					if(region.getAttributes().getFirstAttributeOfType("db_xref") != null){
						region.setAccessionNumber( sequence.getAttributes().getFirstAttributeOfType("db_xref") );
					}
					filtered.add( region );
				}
			} catch (IllegalSequenceOperation e) {
				e.printStackTrace();
			}
		}
		return filtered;
		/*check sequence object type. If unannotated, return true. If annotated type, check against 
		  includedElements and excludedElements */
	}

	/**
	 * Filter a sequence against all the settings of this filter object. The filter is applied to sequence regions 
	 * specified in the list of coordinates, including the includeUpstream and includeDownstream number of characters. 
	 * @param sequence
	 * @param coordinates to scan
	 * @return the sequence region specified by the coordinates if accepted by this filter
	 */
//	public boolean filterSequence( SequenceObject parent, Sequence sequence ){
//		
//		/*iterate over all coordinates, get the region and filter it*/
//		Sequence region = null;
//		try {
//			//System.out.println( "region=" + region.toString());
//			if( applySequenceFilters( region ) && applyElementsFilter( sequence, coordinates ) ){
//				//System.out.println( "filter returns region " + region.toString());
//				return region;
//			}
//			else return null;
//		} catch (IllegalSequenceOperation e) {
//			e.printStackTrace();
//		}
//		return null;
//		/*check sequence object type. If unannotated, return true. If annotated type, check against 
//		  includedElements and excludedElements */
//	}

	/**
	 * returns whether the given coordinate overlaps with any of the object types specified
	 * in the includedElements list 
	 * @param sequence
	 * @param coordinate
	 * @return overlaps with an element of the includedElements list
	 */
	private boolean applyElementsFilter(SequenceObject sequence, SequenceCoordinates coordinate) {
		
		/*with simple sequences there are no associated elements; therefore return true*/
		if( sequence.getSequenceObjectType() == SequenceObjectType.SIMPLE_SEQUENCE ) return true;
		
		if( sequence.getSequenceObjectType() == SequenceObjectType.GENBANK_SEQUENCE ){
			if( includedElements == null && excludedElements == null ){
				//System.out.println( coordinate + " returns an easy true" );
				return true;
			}
			ArrayList<SequenceElementType> elementsToSearch = new ArrayList<SequenceElementType>();
			elementsToSearch.addAll(includedElements);
			
			if( excludedElements != null ){
				elementsToSearch.addAll(excludedElements);
			}
			GenbankEntry gb = (GenbankEntry)sequence;
			List<SequenceElement> elements = gb.getOverlappingElements( coordinate, elementsToSearch, null );
			
			/*now do the negative filtering*/
			if( excludedElements != null && elements.size() > 0 ){
				for( SequenceElement element : elements ){
					//System.out.println( coordinate + " overlaps with element " + element.getSequenceElementType() + " at " + element.getSequenceCoordinates() );
					if( excludedElements.contains( element.getSequenceElementType() ) ){
						//System.out.println( "NEGATIVE: " + coordinate + " overlaps with element " + element.getSequenceElementType() + " at " + element.getSequenceCoordinates() );
						return false;
					}
				}
			}
			
			if( elements.size() > 0 ) return true;
		}
		return false;
	}

	/**
	 * Filter a sequence against the SequenceFilter objects set on this filter object.
	 * @param sequence
	 * @return sequence passed the filters
	 */
	private boolean applySequenceFilters( Sequence sequence ){
		/*first scan against custom filters*/
		if( this.sequenceFilters != null ){
			for( SequenceFilter filter : sequenceFilters ){
				//System.out.println( "applying filter: " + filter.getClass());
				/*if any of the filters returns false, return false on this method*/
				if( ! filter.filter( sequence.getSequence() ) ){
					return false;
				}
			}
		}
		return true;
	}
	
}
