package com.firebirdcss.util.json_tools;

import java.util.ArrayList;

import com.firebirdcss.util.json_tools.mapping.CharType;
import com.firebirdcss.util.json_tools.mapping.MappedItem;
import com.firebirdcss.util.json_tools.mapping.MappedItems;

/**
 * This class contains utility methods which help do various things with JSON.
 * 
 * @author Scott Griffis
 *
 */
public class JsonUtilities {
	private static final String NON_SIG_CHARS = " \n\r\t";
	private static final String HTML_TEMPLATE = 
			  "<!DOCTYPE html>"
			+ "<html>"
			+ "<head>"
			+     "<title>${title}</title>"
			+     "<style>"
			+         "${style}"
			+     "</style>"
			+ "</head>"
			+ "<body>"
			+     "${body}"
			+ "</body>"
			+ "</html>";
	private static final String DEFAULT_HTML_STYLE = 
			  "table {"
			+     "width: 100%;"
			+     "table-layout: fixed;"
			+     "word-wrap: break-word;"
			+ "}"
			+ "table, th, td {"
			+     "border: 1px solid black;"
			+ "}"
			+ "td {"
			+     "padding: 5px;"
			+ "}"
			+ ".headings {"
			+     "font-weight:bold;"
			+ "}";
	private static final String DEFAULT_HTML_TITLE = "JSON to HTML";
	
	/**
	 * This method converts a given JSON {@link String} to an HTML Document.
	 * 
	 * @param json - The JSON to convert to HTML as {@link String}
	 * @return Returns the HTML rendered JSON as {@link String}
	 */
	public static String jsonToHtml(String json) {
		
		return jsonToHtml(DEFAULT_HTML_TITLE, DEFAULT_HTML_STYLE, json);
	}
	
	/**
	 * This method converts a given JSON {@link String} to an HTML Document.
	 * It also allows for a title to be given to the generated document as well
	 * as for style to be injected. <br>
	 * When injecting style you do not need to worry about the HTML style tags.
	 * Also, a class of "headings" is added to the 'td' tag of all the JSON key fields
	 * so that one may target that class with style.
	 * 
	 * @param title - The title of the JSON Document as {@link String}
	 * @param style - The internal style portion of the HTML file as {@link String}
	 * @param json - The JSON Document to convert to HTML as {@link String}
	 * @return Returns the rendered HTML as a {@link String}
	 */
	public static String jsonToHtml(String title, String style, String json) {
		MappedItems jsonMap = new MappedItems(mapJson(json));
		
		StringBuilder sb = new StringBuilder();
		CharType prevType = null;
		for (int i = 0; i < json.length(); i++) {
			char c = json.charAt(i);
			MappedItem item = jsonMap.getItemByIndex(i);
			MappedItem nextItem = jsonMap.getItemAfterIndex(i);
			
			if (item != null) {
				boolean isOpen = false;
				
				if (i == item.getOpenIndex()) {
					isOpen = true;
				}
				
				switch (item.getCharType()) {
					case COLON: // <-------------------------------------------------------{ ':'
						if (prevType != CharType.DOUBLE_QUOTE) { // Quotes end their own <td>...
							sb.append("</td>");
						}
						if (nextItem != null 
								&& nextItem.getCharType() != CharType.DOUBLE_QUOTE  // Quotes handle own </td>...
								&& nextItem.getCharType() != CharType.SQUARE_BRACKET // Square brackets handle own </td>...
						) {
							sb.append("<td>");
						}
						break;
					case COMMA: // <-------------------------------------------------------{ ','
						if (prevType == CharType.COLON) { // Square brackets handle their own </td>...
							sb.append("</td>");
						} 
						if (prevType != CharType.CURLY_BRACKET) {
							sb.append("</tr>");
						}
						break;
					case CURLY_BRACKET:
						if (isOpen) { // <-------------------------------------------------{ '{'
							sb.append("<table>");
						} else { // <------------------------------------------------------{ '}'
							if (prevType == CharType.COLON) {
								sb.append("</td>");
							}
							sb.append("</tr>");
							sb.append("</table>");
						}
						break;
					case DOUBLE_QUOTE: // <------------------------------------------------{ '"'
						if (isOpen) {
							if (prevType != CharType.COLON) { // Front Open...
								sb.append("<tr>");
								sb.append("<td class=\"headings\">");
							} else { // Back open...
								sb.append("<td>"); // Front and back open...
							}
							
						} else { // Front and back close...
							sb.append("</td>");
						}
						break;
					case SQUARE_BRACKET:
						if (isOpen) { // <-------------------------------------------------{ '['
							sb.append("<td>");
						} else { // <------------------------------------------------------{ ']'
							sb.append("</td>");
						}
						break;
				}
				
				prevType = item.getCharType();
				
				continue; // Skip this char...
			}
			
			if (NON_SIG_CHARS.indexOf(c) == -1) { // Char is significant...
				sb.append(c);
			}
		}
		
		/* Swap out the template variables for the desired content */
		String htmlDoc = HTML_TEMPLATE.replace("${body}", sb.toString());
		htmlDoc = htmlDoc.replace("${title}", title);
		htmlDoc = htmlDoc.replace("${style}", style);
		
		return htmlDoc;
	}
	
	/**
	 * This method scans the given JSON string and maps out
	 * the location of important characters to aid in further 
	 * processing of the JSON in the future.
	 * 
	 * @param json - The JSON to be scanned and mapped as {@link String}
	 */
	public static ArrayList<MappedItem> mapJson(String json) {
		ArrayList<MappedItem> mappedItems = new ArrayList<>();
		boolean inQuotes = false;
		
		for (int index = 0; index < json.length(); index ++) {
			char c = json.charAt(index);
			if (c == '\\') { // Needs handled first...
				if (Character.toLowerCase(json.charAt(index + 1)) == 'u') {
					index += 3; // Jump past Unicode, next cycle adds 4th jump.
				} else {
					index ++; // Jump past escaped char, next cycle adds 2nd jump.
				}
				
				continue;
			} else if (c == '"') { // This needs second highest priority...
				inQuotes = !inQuotes;
				if (inQuotes) {
					MappedItem mappedItem = new MappedItem(CharType.DOUBLE_QUOTE, index);
					mappedItems.add(mappedItem);
				} else {
					int lastIndex = getLastUnclosedIndex(mappedItems, CharType.DOUBLE_QUOTE);
					if (lastIndex != -1) {
						mappedItems.get(lastIndex).setCloseIndex(index);
					}
				}
			} else {
				if (!inQuotes) {
					if (c == '{') {
						MappedItem mappedItem = new MappedItem(CharType.CURLY_BRACKET, index);
						mappedItems.add(mappedItem);
					} else if (c == '}') {
						int lastIndex = getLastUnclosedIndex(mappedItems,CharType.CURLY_BRACKET);
						if (lastIndex != -1) {
							mappedItems.get(lastIndex).setCloseIndex(index);
						}
					} else if (c == '[') {
						MappedItem mappedItem = new MappedItem(CharType.SQUARE_BRACKET, index);
						mappedItems.add(mappedItem);
					} else if (c == ']') {
						int lastIndex = getLastUnclosedIndex(mappedItems,CharType.SQUARE_BRACKET);
						if (lastIndex != -1) {
							mappedItems.get(lastIndex).setCloseIndex(index);
						}
					} else if (c == ':' ) {
						MappedItem mappedItem = new MappedItem(CharType.COLON, index);
						mappedItems.add(mappedItem);
					} else if (c == ',') {
						MappedItem mappedItem = new MappedItem(CharType.COMMA, index);
						mappedItems.add(mappedItem);
					}
				}
			}
		}
		
		return mappedItems;
	}
	
	/**
	 * PRIVATE METHOD: Used to fetch the last unclosed par of a specific charType.
	 * 
	 * @param charType - The charType as {@link CharType}
	 * @return Returns the index location of the last unclosed pair of a particular charType, as <code>int</code>
	 * 
	 */
	private static int getLastUnclosedIndex(ArrayList<MappedItem> mappedItems, CharType charType) {
		for (int p = (mappedItems.size() - 1); p >= 0; p --) {
			MappedItem item = mappedItems.get(p);
			if (item != null && item.getCharType() == charType && !item.isClosed()) {
				
				return p;
			}
		}
		
		return -1;
	}
}
