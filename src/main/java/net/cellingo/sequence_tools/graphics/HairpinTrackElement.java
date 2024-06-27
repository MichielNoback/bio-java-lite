/**
 * 
 */
package net.cellingo.sequence_tools.graphics;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class HairpinTrackElement extends SequenceTrackElement{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stopFw;
	private int startRv;

	private Stroke connectorStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[] { 3, 1 }, 0);
	
	public HairpinTrackElement( int startFw, int stopFw, int startRv, int stopRv){
		super(startFw,stopRv);
		this.stopFw = stopFw;
		this.startRv = startRv;
	}

	/**
	 * Overrides SequenceTrackElement.drawElement() to draw hairpin elements
	 */
	public void drawElement( Graphics2D g, double nt2pixelRatio, int trackStartPosition, int trackStopPosition, int trackXoffset, int elementYoffset, int elementHeight, boolean calculateGraphicsCoordinates ){
		//double nt2pixelRatio = SequenceTrack.getNucleotide2pixelRatio();
		/*sets the correct color*/
		if( isSelected() ) g.setColor( getColor().darker() );
		else g.setColor( getColor() );
		
		/*get and correct element start and stop positions*/
		int elementStartPosition = getParentStart();
		if( elementStartPosition < trackStartPosition){
			elementStartPosition = trackStartPosition;
		}
		int elementStopPosition = getParentStop();
		if( elementStopPosition > trackStopPosition ){
			elementStopPosition = trackStopPosition;
		}

		int elementXoffset = trackXoffset + (int)((double)((elementStartPosition - trackStartPosition) * nt2pixelRatio) );
		int elementWidth = (int)( (double)(elementStopPosition - elementStartPosition) * nt2pixelRatio );

		/*create the elements'clickable graphics coordinates*/
		if(calculateGraphicsCoordinates){//createGraphicsCoordinates && recalculateGraphicsCoordinates ){
			GraphicsCoordinates gc = new GraphicsCoordinates( elementYoffset, elementXoffset, elementWidth, elementHeight );
			setGraphicsCoordinates(gc);
		}
		
		/*draw the three elements of the hairpin*/
		int y = (elementYoffset + (elementHeight/2));

		/*forward stem*/
		int fwWidth = 0;
		if( getForwardStemStop() > trackStartPosition ){
			//is within drawable area
			fwWidth = (int)( (double)(getForwardStemStop() - elementStartPosition) * nt2pixelRatio );
			g.fillRect(elementXoffset, elementYoffset, fwWidth, elementHeight);
		}
		
		/*connecting line*/
		int lineLength = 0;
		if( getReverseStemStart() > trackStartPosition){
			int fsStop = getForwardStemStop();
			if( fsStop < trackStartPosition ){
				fsStop = trackStartPosition;
			}
			lineLength = (int)( (double)(getReverseStemStart() - fsStop) * nt2pixelRatio );
			Stroke oldStroke = g.getStroke();
			
			g.setStroke(connectorStroke);
			g.drawLine( (elementXoffset+fwWidth), y, (elementXoffset+fwWidth+lineLength), y );
			g.setStroke(oldStroke);
		}
		
		/*reverse stem*/
		assert getReverseStemStop() > trackStartPosition : "this element should be within track boundaries";
		
		int rsStart = getReverseStemStart();
		if( rsStart < trackStartPosition ){
			rsStart = trackStartPosition;
		}
		int rvWidth = (int)( (double)( getReverseStemStop() - rsStart) * nt2pixelRatio );
		g.fillRect( (elementXoffset+fwWidth+lineLength), elementYoffset, rvWidth, elementHeight );
	}

	/**
	 * get the start of the forward stem
	 * @return forwardStemStart
	 */
	public int getForwardStemStart(){
		return getParentStart();
	}
	
	/**
	 * get the end coordinate of the forward stem strand
	 * @return forwardStemStop
	 */
	public int getForwardStemStop(){
		return this.stopFw;
	}
	
	/**
	 * get the start of the reverse stem strand
	 * @return reverseStemStart
	 */
	public int getReverseStemStart(){
		return this.startRv;
	}
	
	/**
	 * get the stop of the reverse stem strand
	 * @return reverseStemStop
	 */
	public int getReverseStemStop(){
		return getParentStop();
	}
	
}
