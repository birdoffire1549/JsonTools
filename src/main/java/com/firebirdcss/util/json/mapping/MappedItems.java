package com.firebirdcss.util.json.mapping;

import java.util.ArrayList;

/**
 * This item maintains a list of {@link MappedItem}s and has methods to
 * simplify the process of working with the said MappedItems.
 * 
 * @author Scott Griffis
 *
 */
public class MappedItems {
	private ArrayList<MappedItem> mappedItems = new ArrayList<>();
	private MappedItem previousItem = null;
	
	/**
	 * CONSTRUCTOR: Used to initialize this class.
	 * 
	 * @param mappedItems - An {@link ArrayList} of {@link MappedItem}s.
	 */
	public MappedItems(ArrayList<MappedItem> mappedItems) {
		this.mappedItems.addAll(mappedItems);
	}
	
	/**
	 * Used to extract {@link MappedItems} that are encapsulated by 
	 * a specified mappedItem.
	 * 
	 * @param mappedItem - A mappedItem as {@link MappedItem}
	 * @return Returns the extracted {@link MappedItems}
	 */
	public MappedItems pullEncapsulatedItems(MappedItem mappedItem) {
		ArrayList<MappedItem> results = new ArrayList<>();
		for (MappedItem item : mappedItems) {
			if (item.getOpenIndex() >= mappedItem.getOpenIndex() && item.getOpenIndex() < mappedItem.getCloseIndex()) {
				results.add(item);
			} else if (item.getOpenIndex() > mappedItem.getCloseIndex()) { // Gone far enough...
				
				break;
			}
		}
		
		for (MappedItem item : results) {
			mappedItems.remove(item);
		}
		
		previousItem = null;
		
		return new MappedItems(results);
	}
	
	/**
	 * Used to see what the second item in the list is.
	 * 
	 * @return Returns the desired {@link MappedItem}
	 */
	public MappedItem peekSecondItem() {
		if (mappedItems.size() >= 2) {
			
			return mappedItems.get(1);
		}
		
		return null;
	}
	
	/**
	 * Fetches the first {@link MappedItem} in the list.
	 * 
	 * @return Returns the desired {@link MappedItem}
	 */
	public MappedItem getFirstItem() {
		if (mappedItems.size() > 0) {
			
			return mappedItems.get(0);
		}
		
		return null;
	}
	
	/**
	 * Removes and returns the first item in the list.
	 * 
	 * @return Returns the first {@link MappedItem} after removing 
	 * it from the list.
	 */
	public MappedItem removeFirstItem() {
		if (mappedItems.size() > 0) {
			previousItem = mappedItems.get(0);
			
			return mappedItems.remove(0);
		}
		
		return null;
	}
	
	/**
	 * Allows the recently removed item to be recalled and returned
	 * but it does not go back in the list.
	 * 
	 * @return Returns the recently removed {@link MappedItem}
	 */
	public MappedItem recallPreviousItem() {
		
		return previousItem;
	}
	
	/**
	 * Used to fetch a {@link MappedItem} by either its open or closed index.
	 * 
	 * @param index - The index as <code>int</code>
	 * @return Returns the matching {@link MappedItem} otherwise null if not found
	 */
	public MappedItem getItemByIndex(int index) {
		for (MappedItem item : mappedItems) {
			if (item.getOpenIndex() == index || item.getCloseIndex() == index) {
				
				return item;
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the {@link MappedItem} that comes after a specified index.
	 * 
	 * @param index - The specified index as <code>int</code>
	 * @return Returns the {@link MappedItem} or null
	 */
	public MappedItem getItemAfterIndex(int index) {
		for (MappedItem item : mappedItems) {
			if (item.getOpenIndex() > index) {
				
				return item;
			}
		}
		
		return null;
	}
	
	/**
	 * @return Returns the number of items in the list as <code>int</code>
	 * 
	 */
	public int size() {
		
		return this.mappedItems.size();
	}
}
