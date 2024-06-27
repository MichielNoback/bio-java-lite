/**
 *
 */
package net.cellingo.sequence_tools.graphics;

import net.cellingo.sequence_tools.alignment.HairpinAlignment;
import net.cellingo.sequence_tools.annotation.SequenceCoordinates;
import net.cellingo.sequence_tools.annotation.SequenceElement;
import net.cellingo.sequence_tools.annotation.SequenceElementType;
import net.cellingo.sequence_tools.annotation.SequenceHairpinElement;

import java.awt.*;
import java.io.Serializable;

/**
 * The basic sequence element visualization. Also provides conversion methods with general SequenceElement types.
 *
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceTrackElement implements Serializable, Comparable<SequenceTrackElement> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int elementId;
    private SequenceElementType sequenceElementType;
    private boolean complete = true;
    private int parentStart;
    private int parentStop;
    private String name;
    private int trackLane;
    /*the graphics element will be null unless a map listener is defined*/
    private GraphicsCoordinates graphicsCoordinates;
    private Color color;
    private boolean selected;

    public SequenceTrackElement() {
    }

    /**
     * constructor that takes start and stop as arguments
     *
     * @param start
     * @param stop
     */
    public SequenceTrackElement(int start, int stop) {
        this.parentStart = start;
        this.parentStop = stop;
    }

    /**
     * Method that draws the actual sequence element. Can be overriden in subclasses to draw elements or tracks in other ways
     *
     * @param g
     * @param nt2pixelRatio                the conversion factor between nucleotides and picels
     * @param trackStartPosition
     * @param trackStopPosition
     * @param trackXoffset
     * @param elementYoffset
     * @param elementHeight
     * @param calculateGraphicsCoordinates
     */
    public void drawElement(Graphics2D g,
                            double nt2pixelRatio,
                            int trackStartPosition,
                            int trackStopPosition,
                            int trackXoffset,
                            int elementYoffset,
                            int elementHeight,
                            boolean calculateGraphicsCoordinates) {
        /*sets the correct color*/
        if (selected) g.setColor(getColor().darker());
        else g.setColor(getColor());
		
		/*get and correct element start and stop positions*/
        int elementStartPosition = getParentStart();
        if (elementStartPosition < trackStartPosition) {
            elementStartPosition = trackStartPosition;
        }
        int elementStopPosition = getParentStop();
        if (elementStopPosition > trackStopPosition) {
            elementStopPosition = trackStopPosition;
        }

        int elementXoffset = trackXoffset + (int) ((double) ((elementStartPosition - trackStartPosition) * nt2pixelRatio));
        int elementWidth = (int) ((double) (elementStopPosition - elementStartPosition) * nt2pixelRatio);

		/*create the elements'clickable graphics coordinates*/
        if (calculateGraphicsCoordinates) {
            GraphicsCoordinates gc = new GraphicsCoordinates(elementYoffset, elementXoffset, elementWidth, elementHeight);
            setGraphicsCoordinates(gc);
            //System.out.println( this.getClass().getSimpleName() + ".drawElement() created graphics coordinates for element " + this + "\n\t" + gc );
        }


        int y = (elementYoffset + (elementHeight / 2));
        GradientPaint gp;
        if (selected) {
			/*track is selected; draw a highlight box*/
            gp = new GradientPaint(
                    elementXoffset,
                    y,
                    getColor().darker(),
                    elementXoffset + elementWidth,
                    y,
                    getColor().darker().darker());
        } else {
			/*track is not selected; draw a lighter box*/
            gp = new GradientPaint(
                    elementXoffset,
                    y,
                    getColor(),
                    elementXoffset + elementWidth,
                    y,
                    getColor().darker());
        }
        g.setPaint(gp);
        //g.fillRoundRect(elementXoffset, elementYoffset, elementWidth, elementHeight, elementWidth/4, elementHeight/4);
        g.fillRect(elementXoffset, elementYoffset, elementWidth, elementHeight);
    }

    /**
     * Method that draws the actual sequence element AND appends an element to the given imagemap.
     * Can be overriden in subclasses to draw elements or tracks in other ways
     *
     * @param g
     * @param nt2pixelRatio                the conversion factor between nucleotides and picels
     * @param trackStartPosition
     * @param trackStopPosition
     * @param trackXoffset
     * @param elementYoffset
     * @param elementHeight
     * @param calculateGraphicsCoordinates
     * @param imageMap
     */
    public void drawElement(Graphics2D g, double nt2pixelRatio, int trackStartPosition, int trackStopPosition, int trackXoffset, int elementYoffset, int elementHeight, boolean calculateGraphicsCoordinates, StringBuilder imageMap) {
		/*sets the correct color*/
        if (selected) g.setColor(getColor().darker());
        else g.setColor(getColor());
		
		/*get and correct element start and stop positions*/
        int elementStartPosition = getParentStart();
        if (elementStartPosition < trackStartPosition) {
            elementStartPosition = trackStartPosition;
        }
        int elementStopPosition = getParentStop();
        if (elementStopPosition > trackStopPosition) {
            elementStopPosition = trackStopPosition;
        }

        int elementXoffset = trackXoffset + (int) ((double) ((elementStartPosition - trackStartPosition) * nt2pixelRatio));
        int elementWidth = (int) ((double) (elementStopPosition - elementStartPosition) * nt2pixelRatio);

		/*create the elements'clickable graphics coordinates*/
        if (calculateGraphicsCoordinates) {//createGraphicsCoordinates && recalculateGraphicsCoordinates ){
            GraphicsCoordinates gc = new GraphicsCoordinates(elementYoffset, elementXoffset, elementWidth, elementHeight);
            setGraphicsCoordinates(gc);
        }


        int y = (elementYoffset + (elementHeight / 2));
        GradientPaint gp;
        if (selected) {
			/*track is selected; draw a highlight box*/
            gp = new GradientPaint(
                    elementXoffset,
                    y,
                    getColor().darker(),
                    elementXoffset + elementWidth,
                    y,
                    getColor().darker().darker());
        } else {
			/*track is not selected; draw a lighter box*/
            gp = new GradientPaint(
                    elementXoffset,
                    y,
                    getColor(),
                    elementXoffset + elementWidth,
                    y,
                    getColor().darker());
        }
        g.setPaint(gp);
        //g.fillRoundRect(elementXoffset, elementYoffset, elementWidth, elementHeight, elementWidth/4, elementHeight/4);
        g.fillRect(elementXoffset, elementYoffset, elementWidth, elementHeight);
		
		/*draw the image map*/
        int roX = elementXoffset + elementWidth;
        int roY = elementYoffset + elementHeight;
        imageMap.append("<area id=\"element" + getElementId() + "\" shape=\"rect\" coords=\"" + elementXoffset + ","
                + elementYoffset + "," + roX + "," + roY + "\" href=\"javascript:showElement(" + getElementId() + ")\" alt=\""
                + getElementId() + "\" />\n");
    }

    /**
     * @param graphicsCoordinates the graphicsCoordinates to set
     */
    public void setGraphicsCoordinates(GraphicsCoordinates graphicsCoordinates) {
        this.graphicsCoordinates = graphicsCoordinates;
    }

    /**
     * returns the graphics coordinates object for this element
     *
     * @return the graphicsCoordinates
     */
    public GraphicsCoordinates getGraphicsCoordinates() {
        return graphicsCoordinates;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * get the track-lane of this element. Can be seen as the sub-track number.
     *
     * @return the trackLane
     */
    protected int getTrackLane() {
        return trackLane;
    }

    /**
     * set the track-lane of this element. Can be seen as the sub-track number.
     *
     * @param trackLane the trackLane to set
     */
    protected void setTrackLane(int trackLane) {
        this.trackLane = trackLane;
    }

    /**
     * @return the elementId
     */
    public int getElementId() {
        return elementId;
    }

    /**
     * @param elementId the elementId to set
     */
    public void setElementId(int elementId) {
        this.elementId = elementId;
    }

    /**
     * @return the sequenceElementType
     */
    public SequenceElementType getSequenceElementType() {
        return sequenceElementType;
    }

    /**
     * @param sequenceElementType the sequenceElementType to set
     */
    public void setSequenceElementType(SequenceElementType sequenceElementType) {
        this.sequenceElementType = sequenceElementType;
    }

    /**
     * @return the complete
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * @param complete the complete to set
     */
    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    /**
     * @return the parentStart
     */
    public int getParentStart() {
        return parentStart;
    }

    /**
     * @param parentStart the parentStart to set
     */
    public void setParentStart(int parentStart) {
        this.parentStart = parentStart;
    }

    /**
     * @return the parentStop
     */
    public int getParentStop() {
        return parentStop;
    }

    /**
     * @param parentStop the parentStop to set
     */
    public void setParentStop(int parentStop) {
        this.parentStop = parentStop;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * implementing Comparable compareTo method
     */
    public int compareTo(SequenceTrackElement otherElement) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;
        if (this.parentStart == otherElement.getParentStart()) {
            return EQUAL;
        } else if (this.parentStart < otherElement.getParentStart()) {
            return BEFORE;
        } else {
            return AFTER;
        }
    }


    /**
     * convenience conversion method; create a SequenceTrackElement from a SequenceElement
     *
     * @param sequenceElement
     * @return sequenceTrackElement
     */
    public static SequenceTrackElement createSequenceTrackElement(SequenceElement sequenceElement) {
        if (sequenceElement instanceof SequenceHairpinElement)
            return createSequenceTrackElement((SequenceHairpinElement) sequenceElement);
        SequenceTrackElement ste = new SequenceTrackElement();
        ste.setElementId(sequenceElement.getAutoGeneratedElementId());
        ste.setName(sequenceElement.getName());
        ste.setSequenceElementType(sequenceElement.getSequenceElementType());
        SequenceCoordinates coords = sequenceElement.getSequenceCoordinates();
        ste.setComplete(coords.isComplete());

        ste.setParentStart(coords.getStart());
        ste.setParentStop(coords.getStop());

        //System.out.println( SequenceTrackElement.class.getSimpleName() + " track element created " + ste );

        return ste;
    }

    /**
     * convenience conversion method; create a SequenceTrackElement from an Alignment
     *
     * @return sequenceTrackElement
     */
    private static HairpinTrackElement createSequenceTrackElement(SequenceHairpinElement hairpin) {
        int startFw;
        int stopFw;
        int startRv;
        int stopRv;
        startFw = hairpin.getForwardStart(); //hairpin.getTopParent().getSequenceCoordinates().getStart();
        stopFw = hairpin.getForwardStop(); //getTopParent().getSequenceCoordinates().getStop();
        startRv = hairpin.getReverseStart(); //getBottomParent().getSequenceCoordinates().getStop();
        stopRv = hairpin.getReverseStop(); //getBottomParent().getSequenceCoordinates().getStart();

        HairpinTrackElement ste = new HairpinTrackElement(startFw, stopFw, startRv, stopRv);
        ste.setComplete(true);
        ste.setElementId(hairpin.getAutoGeneratedElementId());
        ste.setName("" + hairpin.getAutoGeneratedElementId());
        ste.setSequenceElementType(SequenceElementType.STEM_LOOP);


        ste.setParentStart(startFw);
        ste.setParentStop(stopRv);
        return ste;
    }


    /**
     * convenience conversion method; create a SequenceTrackElement from an Alignment
     *
     * @param hairpin
     * @return sequenceTrackElement
     */
    public static HairpinTrackElement createSequenceTrackElement(HairpinAlignment hairpin) {
        int startFw;
        int stopFw;
        int startRv;
        int stopRv;
        startFw = hairpin.getTopParent().getSequenceCoordinates().getStart();
        stopFw = hairpin.getTopParent().getSequenceCoordinates().getStop();
        startRv = hairpin.getBottomParent().getSequenceCoordinates().getStop();
        stopRv = hairpin.getBottomParent().getSequenceCoordinates().getStart();

        HairpinTrackElement ste = new HairpinTrackElement(startFw, stopFw, startRv, stopRv);
        ste.setComplete(true);
        ste.setElementId(hairpin.getAlignmentID());
        ste.setName("" + hairpin.getAlignmentID());
        ste.setSequenceElementType(SequenceElementType.STEM_LOOP);


        ste.setParentStart(startFw);
        ste.setParentStop(stopRv);
        return ste;
    }

    public String toString() {
        return this.getClass().getSimpleName() + "[type=" + this.getSequenceElementType() + " coordinates: " + this.getParentStart() + "-" + this.getParentStop() + "]";
    }

    /**
     * mark this element as selected (or not)
     *
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * return the selected status of this element
     *
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

}
