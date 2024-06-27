package net.cellingo.sequence_tools.graphics;

import java.awt.Color;

public class StyledTextElement {
	private String text;
	private Color color;
	
	public StyledTextElement( String text, Color color ){
		this.text = text;
		this.color = color;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
	
	
	
}
