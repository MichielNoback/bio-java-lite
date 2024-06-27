package net.cellingo.sequence_tools.annotation;

import net.cellingo.sequence_tools.graphics.StyledTextElement;
import net.cellingo.sequence_tools.sequences.IllegalSequenceOperation;
import net.cellingo.sequence_tools.sequences.Sequence;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a base type sequence element with type specified in 
 * the sequenceElementType property (enum)
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceElement extends SequenceElementBase{
	private Sequence parentSequence;
	private Attributes attributes;
	private List<String> parentSequenceElementIds;

	/**
	 * bean constructor sets the element type to generic
	 */
	public SequenceElement(String id) {
		super(id);
	}
	
	/**
	 * returns the parent of this SequenceElement
	 */
	public Sequence getParentSequence(){
		return parentSequence;
	}
	
	/**
	 * sets the parent to this SequenceElement
	 * @param parent sequence
	 */
	public void setParentSequence(Sequence parent){
		this.parentSequence = parent;
	}

	public List<String> getParentSequenceElementIds() {
		return parentSequenceElementIds;
	}

	public void addParentSequenceElementId(String parentSequenceElementId) {
	    if (parentSequenceElementIds == null) {
            parentSequenceElementIds = new ArrayList<>();
        }
        parentSequenceElementIds.add(parentSequenceElementId);
	}

	/**
	 * get the annotation associated with this sequence element
	 * @return annotation
	 */
	public Attributes getAttributes() {
		if(attributes == null){
			attributes = new Attributes();
		}
		return attributes;
	}

	/**
	 * set the annotation associated with this sequence element
	 * @param attributes
	 */
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * add an annotation to this element
	 * @param type
	 * @param content
	 */
	public void addAttribute(String type, String content){
		if(attributes == null){
			attributes = new Attributes();
		}
		attributes.addAttribute(type, content);
	}
	
	/**
	 * get the sequence of this sequence element (ie the subsequence form the parent)
	 * @return StringBuilder of sequence element
	 */
	public Sequence getParentSequenceRegion() {
		if(parentSequence != null){
			try {
				return parentSequence.getSubSequence(this.getSequenceCoordinates());
			} catch (IllegalSequenceOperation e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * get the sequence of this sequence element (ie the subsequence form the parent)
	 * @param coordinates of the region to return
	 * @return StringBuilder of sequence element
	 */
	public Sequence getParentSequenceRegion(SequenceCoordinates coordinates ) {
		if(parentSequence != null){
			try {
				return parentSequence.getSubSequence( coordinates );
			} catch (IllegalSequenceOperation e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * returns a styled text representation for this SequenceElement
	 * @return styledElementsList
	 */
	public List<StyledTextElement> getStyledTextRepresentation( ){
		List<StyledTextElement> list = new ArrayList<StyledTextElement>();
		if( ! isComplex() ){
			list.add( new StyledTextElement( getParentSequenceRegion().getSequenceString(), Color.RED) );
		}
		else{
			SequenceCoordinates previousCoordinates = null;
			for( SequenceCoordinates c : getSequenceCoordinatesList() ){
				if( previousCoordinates != null ){
					StringBuilder spacer = new StringBuilder();
					for( int i=0; i<(c.getStart()-previousCoordinates.getStop()); i++){
						spacer.append(' ');
					}
					list.add( new StyledTextElement(spacer.toString(), Color.WHITE) );
				}
				list.add(new StyledTextElement( getParentSequenceRegion( c ).getSequenceString(), Color.RED));
				previousCoordinates = c;
			}
		}
		return list;
	}

	@Override
	public String getName() {
		if (getAttributes().containsAttribute("name")) {
			return getAttributes().getFirstAttributeOfType("name");
		} else {
			return super.getName();
		}

	}

	@Override
	public void setName(String name) {
		addAttribute("Name", name);
	}

    @Override
    public String toString() {
	    StringBuilder superStr = new StringBuilder(super.toString());
        superStr.append("  ");
        superStr.append(attributes);
        superStr.append(", Parents=");
        superStr.append(parentSequenceElementIds);
        return superStr.toString();
    }
}
