package com.firebirdcss.util.json_tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebirdcss.util.json_tools.mapping.CharType;
import com.firebirdcss.util.json_tools.mapping.MappedItem;

/**
 * This class contains utility methods which help do various things with JSON.
 * 
 * @author Scott Griffis
 *
 */
public class JsonUtilities {
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
			  "table, tr, td {"
			+     "border: 1px solid black;"
			+     "border-style: solid;"
			+ "}"
			+ "div {"
			+     "padding: 0px 5px;"
			+ "}"
			+ "table {"
			+     "width: 100%;"
			+ "}"
			+ ".key {"
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
		StringBuilder sb = new StringBuilder();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode rootNode = mapper.readValue(json, JsonNode.class);
			
			if (rootNode != null) {
				if (rootNode.isObject()) { // <-------------------{Object
					sb.append(htmlConvertJsonObject(rootNode));
				} else if (rootNode.isArray()) { // <-------------{Array
					sb.append(htmlConvertJsonArray(rootNode));
				} else { // <-------------------------------------{Value
					sb
						.append("<table>")
							.append("<tr>")
								.append("<div class=\"value\">")
									.append(rootNode.asText())
								.append("</div>")
							.append("</tr>")
						.append("</table>")
					;
				}

			}
		} catch (Exception e) {
			// TODO: Will decide what to do here later...
		}
		
		/* Swap out the template variables for the desired content */
		String htmlDoc = HTML_TEMPLATE.replace("${title}", title);
		htmlDoc = htmlDoc.replace("${style}", style);
		htmlDoc = htmlDoc.replace("${body}", sb.toString());
		
		return htmlDoc;
	}
	
	/**
	 * PRIVATE: 
	 * 
	 * @param objectNode
	 * @return
	 */
	private static String htmlConvertJsonObject(JsonNode objectNode) {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, JsonNode>> entries = objectNode.fields();
		
		sb.append("<table>");
		while (entries.hasNext()) {
			Entry<String, JsonNode> entry = entries.next();
			sb
				.append("<tr>")
					.append("<td>")
						.append("<div class=\"key\">")
							.append(entry.getKey())
						.append("</div>")
					.append("</td>")
					.append("<td>")
			;
			if (entry.getValue().isObject()) { // <---------------{Object
				sb.append(htmlConvertJsonObject(entry.getValue()));
			} else if (entry.getValue().isArray()) { // <---------{Array
				sb.append(htmlConvertJsonArray(entry.getValue()));
			} else { // <-----------------------------------------{Value
				sb
					.append("<div class=\"value\">")
						.append(entry.getValue().asText())
					.append("</div>")
				;
			}
			sb.append("</td>").append("</tr>");
		}
		sb.append("</table>");
		
		return sb.toString();
	}
	
	/**
	 * PRIVATE: 
	 * 
	 * @param arrayNode
	 * @return
	 */
	private static String htmlConvertJsonArray(JsonNode arrayNode) {
		StringBuilder sb = new StringBuilder();
		Iterator<JsonNode> elements = arrayNode.elements();
		
		sb.append("<table>");
		while (elements.hasNext()) {
			JsonNode value = elements.next();
			sb.append("<tr><td>");
			if (value.isObject()) {
				sb.append(htmlConvertJsonObject(value));
			} else if (value.isArray()) {
				sb.append(htmlConvertJsonArray(value));
			} else {
				sb
					.append("<div class=\"value\">")
						.append(value.asText())
					.append("</div>")
				;
			}
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		
		return sb.toString();
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
		String style = DEFAULT_HTML_STYLE;
		
		System.out.println(jsonToHtml("", style, json));
	}
}
