package com.firebirdcss.util.json.mapping;

/**
 * This class stores information related to a MappedItem.
 * <p>
 * As of creating this the only items being mapped are un-quoted/un-escaped characters
 * of type {@link CharType}.
 * 
 * @author Scott Griffis
 *
 */
public class MappedItem {
	private final CharType charType;
	private final int openIndex;
	private int closeIndex = -1;
	
	/**
	 * CONSTRUCTOR: Used to instantiate the object with some required 
	 * values. 
	 *
	 * @param charType - The type of character being mapped as {@link CharType}
	 * @param openIndex - The index of the opening are starting character of a paired 
	 * or non-paired character as <code>int</code>
	 */
	public MappedItem(CharType charType, int openIndex) {
		this.charType = charType;
		this.openIndex = openIndex;
	}
	
	/**
	 * GETTER: Gets the Character Type of this MappendItem.
	 * 
	 * @return Returns the character type as {@link CharType}
	 */
	public CharType getCharType() {
		
		return this.charType;
	}
	
	/**
	 * GETTER: Gets the open index location of the initial character of 
	 * the represented CharType.
	 * 
	 * @return Returns the open index as <code>int</code>
	 */
	public int getOpenIndex() {
		
		return this.openIndex;
	}
	
	/**
	 * GETTER: Gets the close index location of the second character of
	 * the represented CharType.
	 * 
	 * @return Returns the close index as <code>int</code>
	 */
	public int getCloseIndex() {
		
		return this.closeIndex;
	}
	
	/**
	 * SETTER: Sets the close index for the second character of
	 * the represented pair.
	 * 
	 * @param closeIndex - The close index as <code>int</code>
	 */
	public void setCloseIndex(int closeIndex) {
		this.closeIndex = closeIndex;
	}
	
	/**
	 * Used to determine if the represented Char has found its closing pair.
	 * 
	 * @return Returns true if the MappedItem has an index for its matching
	 * pair or if the Character Type doesn't require a matching pair, otherwise
	 * returns false as <code>boolean</code>
	 */
	public boolean isClosed() {
		boolean result = false;
		switch (this.charType) {
			case CURLY_BRACKET:
			case DOUBLE_QUOTE:
			case SQUARE_BRACKET:
				if (this.closeIndex != -1) {
					result = true;
				}
				break;
			default:
				result = true;
				break;
		}
		
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "[" + charType.name() + "," + openIndex + "," + closeIndex + "]"; 
	}
}
