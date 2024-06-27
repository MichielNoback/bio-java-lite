/**
 * 
 */
package net.cellingo.sequence_tools.graphics;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.cellingo.sequence_tools.annotation.SequenceElementType;

/**
 * Sequence elements can be grouped in a sequence track to be displayed in the SequenceMap object.
 * This is the base class simply drawing rounded rectangles for all sequence elements. 
 * It should be subclassed whenever other sequence tracks should be represented. 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceTrack {
	//hashmap to hold the element colors based on element type
	private static HashMap<SequenceElementType, Color> mapColors;
	//the default element color for unknown element types
	private static Color defaultElementColor = Color.CYAN;
	//if the SequenceMap has listeners, the coordinates should be stored
	protected boolean createGraphicsCoordinates;

	//the name of the track
	private String trackName;
	//the source of this track (ie NCBI, ORFfinder etc)
	private String trackSource;
	//the color to be used for this track
	private Color trackColor;
	
	//the id of this track
	private int trackID;
	//the number of lanes within this track
	private int trackLaneNumber = 1;
	//the track lane x offset
	private int trackXoffset;
	//the track lane y offset
	private int trackYoffset;
	//the height of elements
	private int elementHeight;
	//the height of this track
	private double trackHeight = 15;
	//the width of the track lane
	private int trackWidth;
	//top and bottom padding of the track elements
	private int trackPadding = 2;
	//when displaying overlapping elements, collapse?
	private boolean collapseOverlapping = true;
	//determine the track height dynamically based on max number of overlapping elements
	private boolean determineTrackHeightDynamically = false;
	//flag to indicate whether the track height was already calculated
	private boolean trackHeightCalculated = false;
	
	//the sequence position at which to start displaying the track 
	private int startPosition;
	//the sequence position at which to end displaying the track
	private int endPosition;
	
	//the composing elements
	private ArrayList<SequenceTrackElement> elements;
	//whether the list is sorted
	private boolean sorted = false;
	//the GraphicsCoordinates which can be used for click events: usually only legend text
	private GraphicsCoordinates graphicsCoordinates;
	//the selected status
	private boolean selected;
	
	static{
		mapColors = new HashMap<SequenceElementType, Color>();
		mapColors.put(SequenceElementType.CDS, new Color(100,155,255) );
		mapColors.put(SequenceElementType.GENE, new Color(255,200,155) );
		mapColors.put(SequenceElementType.EXON, new Color(155,200,155) );
		mapColors.put(SequenceElementType.INTRON, new Color(155,100,155) );
		mapColors.put(SequenceElementType.UNDEFINED, new Color(150,155,200) );
		mapColors.put(SequenceElementType.MIRNA, new Color(50,100,100) );
	}
	
	
	/**
	 * bean constructor constructs with a height of 15, and collapses overlapping elements
	 */
	public SequenceTrack(){
		this( 15, false );
	}

	/**
	 * constructs with given height of 15, and collapses overlapping elements
	 * @param trackHeight
	 */
	public SequenceTrack( int trackHeight ){
		this( trackHeight, false );
	}

	/**
	 * construct with setting for whether overlapping track elements should be collapsed. This property defaults to true; 
	 * if it is set to false, the track element height will be changed according to the maximum number of overlapping 
	 * elements in the track.
	 * @param trackHeight
	 * @param collapseOverlapping
	 */
	public SequenceTrack( int trackHeight, boolean collapseOverlapping ){
		this.trackHeight = trackHeight;
		this.collapseOverlapping = collapseOverlapping;
	}

	/**
	 * construct with setting for whether overlapping track elements should be collapsed. This property defaults to true; 
	 * if it is set to false, the track element height will be changed according to the maximum number of overlapping 
	 * elements in the track. If collapseOverlapping is set to false and the determineTrackHeightDynamically is set 
	 * to true, the height of the track is determined dynamically
	 * @param height  
	 * @param collapseOverlapping
	 * @param determineTrackHeightDynamically
	 */
	public SequenceTrack( boolean determineTrackHeightDynamically, int elementHeigth ){
		this.determineTrackHeightDynamically = determineTrackHeightDynamically;
		this.elementHeight = elementHeigth;
		this.collapseOverlapping = false;
	}

	/*init block*/
	{
		this.elements = new ArrayList<SequenceTrackElement>();
		this.elementHeight = (int)trackHeight - trackPadding;
	}
	
	/**
	 * @param createGraphicsCoordinates the createGraphicsCoordinates to set
	 */
	public void setCreateGraphicsCoordinates( boolean createGraphicsCoordinates ){
		this.createGraphicsCoordinates = createGraphicsCoordinates;
	}


	/**
	 * this method makes the track draw itself within the given boundaries.
	 * Can be overriden by subclasses to implement specific track behavior
	 * @param g
	 * @param nt2pixelRatio the conversion factor between nucleotides and picels
	 * @param trackXoffset the offset from the left
	 * @param trackYoffset the offset from the top
	 * @param startPosition
	 * @param endPosition
	 */
	public void drawTrack( Graphics2D g, double nt2pixelRatio, int trackXoffset, int trackYoffset, int startPosition, int endPosition ){
		if(! this.sorted ){
			Collections.sort( elements );
			sorted = true;
		}
		/*this is done only once for a track*/
		if( ! collapseOverlapping && ! determineTrackHeightDynamically) calculateElementHeight();
		else if(! determineTrackHeightDynamically ) this.elementHeight = (int)(trackHeight) - trackPadding - trackPadding;
		
		//toggle to keep track of whether the GraphicsCoordinates should be recalculated
		//boolean recalculateGraphicsCoordinates = false;

		if( this.trackXoffset != trackXoffset 
				|| this.trackYoffset != trackYoffset
				|| this.startPosition != startPosition
				|| this.endPosition != endPosition ){
			
			this.trackXoffset = trackXoffset;
			this.trackYoffset = trackYoffset;
			this.startPosition = startPosition;
			this.endPosition = endPosition;

			/*this is done each time a track is redrawn*/
			//if( createGraphicsCoordinates ) recalculateGraphicsCoordinates = true;
			
			/*this is done each time a track is redrawn*/
			if( ! trackHeightCalculated && ! collapseOverlapping && determineTrackHeightDynamically) calculateTrackHeight( this.startPosition, this.endPosition );
		}

		if( selected ){
			/*track is selected; draw a highlight box*/
			GradientPaint gp = new GradientPaint( (this.trackWidth-1)/2,
					trackYoffset,
					g.getBackground(),
					(this.trackWidth-1)/2,
					trackYoffset+(int)trackHeight,
					g.getBackground().darker() );
			
			g.setPaint( gp );
			g.fillRect( 1, trackYoffset, this.trackWidth-2, (int)trackHeight );
		}
		
		/*determine the y-offset of the track elements*/
		int elementYoffset = trackYoffset + trackPadding;
		
		/*only draw if within range of the map*/
		int firstIndex = getFirstViewableElementIndex( this.startPosition, this.endPosition);
		//System.out.println( this.getClass().getSimpleName() + ".drawTrack(): first viewable index=" + firstIndex );
		
		if( firstIndex == -1 ) return;
		
		/*draw the elements*/
		for( int i=firstIndex; i<elements.size(); i++ ){
			SequenceTrackElement element = elements.get(i);

			/*if out of range of viewable range, break off*/
			if(element.getParentStart() > endPosition) return;

			/*draw the element*/
			if( createGraphicsCoordinates  ){
				element.drawElement( g, nt2pixelRatio, startPosition, endPosition, trackXoffset, elementYoffset + (element.getTrackLane() * (elementHeight+trackPadding) ) , elementHeight, true);
			}
			else{
				element.drawElement( g, nt2pixelRatio, startPosition, endPosition, trackXoffset, elementYoffset + (element.getTrackLane() * (elementHeight+trackPadding) ), elementHeight, false);
			}
		}
	}
	
	/**
	 * calculates the height of this track dynamically, when the field collapseOverlapping is false and calculateTrackHeightDynamically is true
	 */
	private void calculateTrackHeight( int startPosition, int endPosition ){
		/*only draw if within range of the map*/
		int firstIndex = getFirstViewableElementIndex( startPosition, endPosition );
		//System.out.println( this.getClass().getSimpleName() + ".calculateTrackHeight(): first viewable index=" + firstIndex );
		
		if( firstIndex == -1 ) return;

		/*first determine the max number of overlapping track lanes*/
		calculateTrackLaneNumber( false, firstIndex, startPosition, endPosition);
		setTrackHeight( this.trackLaneNumber * ( elementHeight + trackPadding ) );
		trackHeightCalculated = true;

		//System.out.println( this.getClass().getSimpleName() + ".calculateTrackHeight(): track height=" + trackHeight + "; tracklanes " + trackLaneNumber + "; element height=" + elementHeight );
	}
	
	/**
	 * determines the height of elements dynamically
	 */
	private void calculateElementHeight(){
		/*first determine the max number of overlapping track lanes*/
		calculateTrackLaneNumber( true, 0, startPosition, endPosition);
		/*set the height of the elements, with a minimum of 1*/
		this.elementHeight = (int)( trackHeight / this.trackLaneNumber ) - trackPadding;//( (int)( trackHeight / this.trackLaneNumber ) == 0 ? 1 : (int)( trackHeight / this.trackLaneNumber ) );
		if( this.elementHeight < 1 ) elementHeight = 1;
		
		//System.out.println( this.getClass().getSimpleName() + ".calculateElementHeight(): track height=" + trackHeight + "; tracklanes " + trackLaneNumber + "; element height=" + elementHeight );
	}
	
	/**
	 * Calculates the number of track lanes and registers the correct lane with each element
	 * @param allElements
	 * @param firstIndex
	 */
	private void calculateTrackLaneNumber( boolean allElements, int firstIndex, int startPosition, int endPosition  ){
		int maxTrackLanes = 1;
		int trackLane = 0;
		int firstLaneStop = -1;
		
		//System.out.println( this.getClass().getSimpleName() + ".calculateTrackLaneNumber(): allElements=" + allElements 
		//		+ "; firstIndex " + firstIndex + "; endPosition=" + endPosition );
		
		/*determine the track lane of each element and the total number of track lanes*/
		for( int i=firstIndex; i<elements.size(); i++ ){
			SequenceTrackElement element = elements.get(i);
			
			int start = element.getParentStart();
			
			/*if out of range of viewable range, break off*/
			if( ! allElements && element.getParentStart() > endPosition) return;
			
			if(start > firstLaneStop){ //no overlap
				trackLane = 0;
				element.setTrackLane(trackLane);
				firstLaneStop = element.getParentStop();
			}
			else{ //there is an overlap
				trackLane++;
				element.setTrackLane(trackLane);
				if(trackLane > maxTrackLanes){
					maxTrackLanes = trackLane;
				}
			}	
		}
		setTrackLaneNumber( maxTrackLanes + 1 );
	}
	
	/**
	 * Returns the index of the first viewable element, as identified by binary search.
	 * Return -1 of no elements are within the viewable range
	 * @return firstViewableElementIndex
	 */
	private int getFirstViewableElementIndex( int startPosition, int endPosition ){
		//TODO implement this correctly
		if( elements.get( elements.size()-1).getParentStop() < startPosition ) return -1;
		for( int i=0; i<elements.size(); i++){
			if( elements.get(i).getParentStop() > startPosition ) return i;
		}
		return -1;
		
//		int index = -1;
//        int low = 0;
//        int high = elements.size()-1;
//        int mid;
//
//        while( low <= high ){
//            mid = ( low + high ) / 2;
//            
//            System.out.println( this.getClass().getSimpleName() + ".getFirstViewableElementIndex(): BEFORE mid=" + mid + "; low=" + low + "; high=" + high 
//            		+ "; mid:parentStart=" + elements.get(mid).getParentStart() + "; mid:parentStop=" + elements.get(mid).getParentStop() + " start=" + startPosition);
//            //if( mid==0 ) return 0;
//            
//            if( elements.get(mid).getParentStop() < this.startPosition ) low = mid + 1;
//            
//            else if( elements.get(mid).getParentStart() > this.startPosition ) high = mid - 1;
//            
//            else{
//            	System.out.println( this.getClass().getSimpleName() + ".getFirstViewableElementIndex(): return mid=" + mid );
//            	return mid;
//            }
//            
//            System.out.println( this.getClass().getSimpleName() + ".getFirstViewableElementIndex(): AFTER mid=" + mid + "; low=" + low + "; high=" + high 
//            		+ "; mid:parentStart=" + elements.get(mid).getParentStart() + "; mid:parentStop=" + elements.get(mid).getParentStop() + " start=" + startPosition);
//            if( low >= high ) return high;
//
//        }
//    	System.out.println( this.getClass().getSimpleName() + ".getFirstViewableElementIndex(): return index=" + index );
//		return index; // -1 NOT FOUND
	}
	
	/**
	 * creates a highlight variant of a given color
	 * @param c
	 * @return
	 */
	public static Color createHighlightColor( Color c ){
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		r = ( r>10 ? r-15 : 0);
		g = ( g>10 ? g-15 : 0);
		b = ( b>10 ? b-15 : 0);
		Color h = new Color( r, g, b  ) ;
		return h;
	}
	
	/**
	 * sets the graphics coordinates used for mouse events
	 * @param graphicsCoordinates 
	 */
	public void setGraphicsCoordinates(GraphicsCoordinates graphicsCoordinates) {
		this.graphicsCoordinates = graphicsCoordinates;
	}

	/**
	 * returns the graphics coordinates used for mouse events
	 * @return the graphicsCoordinates
	 */
	public GraphicsCoordinates getGraphicsCoordinates() {
		return graphicsCoordinates;
	}

	/**
	 * add an element to this track
	 * @param element
	 */
	public void addSequenceTrackElement( SequenceTrackElement element ){
		/* determine element color. If already defined on element, keep it. If a track color is defined
		 * this takes second precedence. Third option is the color defined in the colors map. 
		 * Finally, the default element color is used. 
		 */
		if(this.trackColor != null && element.getColor() == null){
			element.setColor( this.trackColor );
		}
		else if( this.trackColor == null && element.getColor() == null){
			if( mapColors.containsKey( element.getSequenceElementType() )){
				element.setColor( mapColors.get( element.getSequenceElementType() ) );
			}
			else{
				element.setColor( defaultElementColor );
			}
		}
		this.elements.add(element);
	}

	/**
	 * get an iterator of all (sorted) sequence track elements
	 * @return elementIterator
	 */
	public Iterator<SequenceTrackElement> getSequenceTrackElements(){
		if(! this.sorted ){
			Collections.sort( elements );
			sorted = true;
		}
		return elements.iterator();
	}
	
	/**
	 * convenience method returns a List of track elements
	 * @return sequencetrackelements
	 */
	public List<SequenceTrackElement> getSequenceTrackElementList(){
		if(! this.sorted ){
			Collections.sort( elements );
			sorted = true;
		}
		return elements;
	}
	
	/**
	 * get the height of this sequence track in pixels. The given start and end positions are relevant only for dynamic track heights
	 * @param startPosition
	 * @param endPosition
	 * @return
	 */
	public double getTrackHeight( int startPosition, int endPosition ) {
		if( ! this.trackHeightCalculated && ! this.collapseOverlapping && this.determineTrackHeightDynamically ) calculateTrackHeight( startPosition, endPosition);
		return trackHeight;
	}

	/**
	 * set the height of this track in pixels.
	 * @param trackHeight the trackHeight to set
	 */
	public void setTrackHeight(double trackHeight) {
		this.trackHeight = trackHeight;
	}

	/**
	 * get the number of lanes within this track
	 * @return the trackLaneNumber
	 */
	protected int getTrackLaneNumber() {
		return trackLaneNumber;
	}

	/**
	 * set the number of lanes within this track
	 * @param trackLaneNumber the trackLaneNumber to set
	 */
	protected void setTrackLaneNumber(int trackLaneNumber) {
		this.trackLaneNumber = trackLaneNumber;
	}

	/**
	 * @param trackWidth the trackWidth to set
	 */
	public void setTrackWidth(int trackWidth) {
		this.trackWidth = trackWidth;
	}

	/**
	 * @return the trackLaneWidth
	 */
	public int getTrackWidth() {
		return trackWidth;
	}

	/**
	 * @param trackPadding the trackPadding to set
	 */
	public void setTrackPadding(int trackPadding) {
		this.trackPadding = trackPadding;
	}

	/**
	 * @return the trackLanePadding
	 */
	public int getTrackPadding() {
		return trackPadding;
	}

	/**
	 * @return the trackColor
	 */
	public Color getTrackColor() {
		return trackColor;
	}

	/**
	 * @param trackColor the trackColor to set
	 */
	public void setTrackColor(Color trackColor) {
		this.trackColor = trackColor;
	}
	/**
	 * @return the trackName
	 */
	public String getTrackName() {
		return trackName;
	}

	/**
	 * @param trackName the trackName to set
	 */
	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	/**
	 * @return the trackSource
	 */
	public String getTrackSource() {
		return trackSource;
	}

	/**
	 * @param trackSource the trackSource to set
	 */
	public void setTrackSource(String trackSource) {
		this.trackSource = trackSource;
	}

	/**
	 * @return the trackID
	 */
	public int getTrackID() {
		return trackID;
	}

	/**
	 * @param trackID the trackID to set
	 */
	public void setTrackID(int trackID) {
		this.trackID = trackID;
	}

	/**
	 * mark this track as selected (or not)
	 * @param selected
	 */
	public void setSelected(boolean selected ) {
		this.selected = selected;
	}
	/**
	 * return the selected status of this track
	 * @return
	 */
	public boolean isSelected(){
		return selected;
	}

	public String toString(){
		return this.getClass().getSimpleName() + "[name=" + this.getTrackName() + " id=" + this.getTrackID() + " source=" + this.getTrackSource() + "]";
	}

}
