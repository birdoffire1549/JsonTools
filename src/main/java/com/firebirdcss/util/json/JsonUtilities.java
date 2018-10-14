package com.firebirdcss.util.json;

import java.util.ArrayList;

import com.firebirdcss.util.json.mapping.CharType;
import com.firebirdcss.util.json.mapping.MappedItem;
import com.firebirdcss.util.json.mapping.MappedItems;

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
			+ "    <title>${title}</title>"
			+ "    <style>"
			+ "        ${style}"
			+ "    </style>"
			+ "</head>"
			+ "<body>"
			+ "    ${body}"
			+ "</body>"
			+ "</html>";
	private static final String DEFAULT_HTML_STYLE = 
			  "table, th, td {"
			+ "    border: 1px solid black;"
			+ "    padding: 5px;"
			+ "}"
			+ "table {"
			+ "    width: 100%;"
			+ "}";
	
	/**
	 * This method converts a given JSON {@link String} to an HTML Document.
	 * 
	 * @param json - The JSON to convert to HTML as {@link String}
	 * @return Returns the HTML rendered JSON as {@link String}
	 */
	public static String jsonToHtml(String json) {
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
					case COLON:
						if (prevType != CharType.DOUBLE_QUOTE) {
							sb.append("</td>");
						}
						if (nextItem != null && nextItem.getCharType() != CharType.DOUBLE_QUOTE) {
							sb.append("<td>");
						}
						break;
					case COMMA:
						if (prevType != null) {
							if (prevType == CharType.COLON 
									|| prevType == CharType.SQUARE_BRACKET
							) {
								sb.append("</td>");
							}
							if (prevType == CharType.SQUARE_BRACKET) {
								sb.append("</tr>");
							}
						}
						break;
					case CURLY_BRACKET:
						if (isOpen) {
							sb.append("<table>");
						} else {
							if (prevType == CharType.COLON || prevType == CharType.DOUBLE_QUOTE) {
								sb.append("</tr>");
							}
							sb.append("</table>");
						}
						break;
					case DOUBLE_QUOTE:
						if (isOpen) {
							if (prevType != null) {
								if (prevType != CharType.COLON) {
									sb.append("<tr>");
								}
							}
							sb.append("<td>");
						} else {
							sb.append("</td>");
							if (nextItem != null && nextItem.getCharType() != CharType.COLON) {
								sb.append("</tr>");
							}
						}
						break;
					case SQUARE_BRACKET:
						if (isOpen) {
							sb.append("<table>");
						} else {
							sb.append("</table>");
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
		htmlDoc = htmlDoc.replace("${title}", "UNKNOWN");
		htmlDoc = htmlDoc.replace("${style}", DEFAULT_HTML_STYLE);
		
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
	
	/**
	 * TODO: Remove this method when finished with this code.
	 * This stuff should be replaced with actual JUNIT tests.
	 * 
	 * @param args - Not used.
	 */
	public static void main(String[] args) {
		String json = "{ \n" + 
				"  \"first_name\" : \"Sammy\",\n" + 
				"  \"last_name\" : \"Shark\",\n" + 
				"  \"location\" : \"Ocean\",\n" + 
				"  \"websites\" : [ \n" + 
				"    {\n" + 
				"      \"description\" : \"work\",\n" + 
				"      \"URL\" : \"https://www.digitalocean.com/\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"desciption\" : \"tutorials\",\n" + 
				"      \"URL\" : \"https://www.digitalocean.com/community/tutorials\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"social_media\" : [\n" + 
				"    {\n" + 
				"      \"description\" : \"twitter\",\n" + 
				"      \"link\" : \"https://twitter.com/digitalocean\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"description\" : \"facebook\",\n" + 
				"      \"link\" : \"https://www.facebook.com/DigitalOceanCloudHosting\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"description\" : \"github\",\n" + 
				"      \"link\" : \"https://github.com/digitalocean\"\n" + 
				"    }\n" + 
				"  ]\n" + 
				"}";
		
		System.out.println(jsonToHtml(json));
	}
}
