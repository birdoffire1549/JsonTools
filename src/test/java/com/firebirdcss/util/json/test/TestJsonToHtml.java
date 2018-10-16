package com.firebirdcss.util.json.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.firebirdcss.util.json_tools.JsonUtilities;

public class TestJsonToHtml {
	/* JSON Samples */
	private static final String JSON_SAMPLE1 = 
			"{ \n" + 
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
	private static final String JSON_SAMPLE2 = 
			"{\n" + 
			"	\"id\": \"0001\",\n" + 
			"	\"type\": \"donut\",\n" + 
			"	\"name\": \"Cake\",\n" + 
			"	\"ppu\": 0.55,\n" + 
			"	\"batters\":\n" + 
			"		{\n" + 
			"			\"batter\":\n" + 
			"				[\n" + 
			"					{ \"id\": \"1001\", \"type\": \"Regular\" },\n" + 
			"					{ \"id\": \"1002\", \"type\": \"Chocolate\" },\n" + 
			"					{ \"id\": \"1003\", \"type\": \"Blueberry\" },\n" + 
			"					{ \"id\": \"1004\", \"type\": \"Devil's Food\" }\n" + 
			"				]\n" + 
			"		},\n" + 
			"	\"topping\":\n" + 
			"		[\n" + 
			"			{ \"id\": \"5001\", \"type\": \"None\" },\n" + 
			"			{ \"id\": \"5002\", \"type\": \"Glazed\" },\n" + 
			"			{ \"id\": \"5005\", \"type\": \"Sugar\" },\n" + 
			"			{ \"id\": \"5007\", \"type\": \"Powdered Sugar\" },\n" + 
			"			{ \"id\": \"5006\", \"type\": \"Chocolate with Sprinkles\" },\n" + 
			"			{ \"id\": \"5003\", \"type\": \"Chocolate\" },\n" + 
			"			{ \"id\": \"5004\", \"type\": \"Maple\" }\n";
	private static final String JSON_SAMPLE3 = 
			"{\n" + 
			"      \"username\": \"CT-SOC\\\\m.shinbori\",\n" + 
			"      \"alert_type\": \"watchlist.hit.ingress.process\",\n" + 
			"      \"sensor_criticality\": 3.0,\n" + 
			"      \"modload_count\": 84,\n" + 
			"      \"report_score\": 1,\n" + 
			"      \"watchlist_id\": \"f435128d-f5c4-4fa1-9f1f-14d0051ff09c\",\n" + 
			"      \"sensor_id\": 11,\n" + 
			"      \"feed_name\": \"bit9endpointvisibility\",\n" + 
			"      \"created_time\": \"2017-05-14T18:15:46.083Z\",\n" + 
			"      \"report_ignored\": false,\n" + 
			"      \"ioc_type\": \"query\",\n" + 
			"      \"watchlist_name\": \"f435128d-f5c4-4fa1-9f1f-14d0051ff09c\",\n" + 
			"      \"ioc_confidence\": 0.5,\n" + 
			"      \"alert_severity\": 0.675,\n" + 
			"      \"crossproc_count\": 3,\n" + 
			"      \"group\": \"Default Group\",\n" + 
			"      \"hostname\": \"EP-TEST3\",\n" + 
			"      \"filemod_count\": 3,\n" + 
			"      \"comms_ip\": \"10.1.22.145\",\n" + 
			"      \"netconn_count\": 1,\n" + 
			"      \"interface_ip\": \"10.1.22.145\",\n" + 
			"      \"status\": \"Unresolved\",\n" + 
			"      \"process_path\": \"c:\\\\program files (x86)\\\\teraterm\\\\ttermpro.exe\",\n" + 
			"      \"description\": \"Unsigned file with network connections\",\n" + 
			"      \"process_name\": \"ttermpro.exe\",\n" + 
			"      \"process_unique_id\": \"0000000b-0000-2508-01d2-cd281b7d57fd-00000001\",\n" + 
			"      \"process_id\": \"0000000b-0000-2508-01d2-cd281b7d57fd\",\n" + 
			"      \"link\": \"https://www.carbonblack.com/cbfeeds/endpointvisibility_feed.xhtml#9\",\n" + 
			"      \"_version_\": 1567396458521427969,\n" + 
			"      \"regmod_count\": 0,\n" + 
			"      \"md5\": \"67e177ced5462a5c13a95d9f01f6a3e7\",\n" + 
			"      \"segment_id\": 1,\n" + 
			"      \"total_hosts\": 2,\n" + 
			"      \"feed_id\": 12,\n" + 
			"      \"ioc_value\": \"{\\\"index_type\\\": \\\"events\\\", \\\"search_query\\\": \\\"cb.urlver=1&q=(netconn_count%3A%5B1%20TO%20*%5D%20digsig_result%3Aunsigned)\\\"}\",\n" + 
			"      \"os_type\": \"windows\",\n" + 
			"      \"childproc_count\": 2,\n" + 
			"      \"unique_id\": \"f8866800-f931-40cc-a390-4c2d8d3b5d6e\",\n" + 
			"      \"feed_rating\": 3.0\n" + 
			"    }";
	/* STYLE Samples */
	private static final String STYLE_SAMPLE1 = 
			  "table, th, td {"
			+    "border: 1px solid black;"
			+ "}";
	
	/* EXPECTED Results */
	private static final String EXPECTED1 = "<!DOCTYPE html><html><head><title>JSON to HTML</title><style>table, th, td {border: 1px solid black;}tr:hover {background-color: #f5f5f5;}td {padding: 5px;}table {width: 100%;}.headings {font-weight:bold;}</style></head><body><table><tr><td class=\"headings\">first_name</td><td>Sammy</td></tr><tr><td class=\"headings\">last_name</td><td>Shark</td></tr><tr><td class=\"headings\">location</td><td>Ocean</td></tr><tr><td class=\"headings\">websites</td><td><table><tr><td class=\"headings\">description</td><td>work</td></tr><tr><td class=\"headings\">URL</td><td>https://www.digitalocean.com/</td></tr></table><table><tr><td class=\"headings\">desciption</td><td>tutorials</td></tr><tr><td class=\"headings\">URL</td><td>https://www.digitalocean.com/community/tutorials</td></tr></table></td></tr><tr><td class=\"headings\">social_media</td><td><table><tr><td class=\"headings\">description</td><td>twitter</td></tr><tr><td class=\"headings\">link</td><td>https://twitter.com/digitalocean</td></tr></table><table><tr><td class=\"headings\">description</td><td>facebook</td></tr><tr><td class=\"headings\">link</td><td>https://www.facebook.com/DigitalOceanCloudHosting</td></tr></table><table><tr><td class=\"headings\">description</td><td>github</td></tr><tr><td class=\"headings\">link</td><td>https://github.com/digitalocean</td></tr></table></td></tr></table></body></html>";
	private static final String EXPECTED2 = "<!DOCTYPE html><html><head><title>Title</title><style></style></head><body><table><tr><td class=\"headings\">first_name</td><td>Sammy</td></tr><tr><td class=\"headings\">last_name</td><td>Shark</td></tr><tr><td class=\"headings\">location</td><td>Ocean</td></tr><tr><td class=\"headings\">websites</td><td><table><tr><td class=\"headings\">description</td><td>work</td></tr><tr><td class=\"headings\">URL</td><td>https://www.digitalocean.com/</td></tr></table><table><tr><td class=\"headings\">desciption</td><td>tutorials</td></tr><tr><td class=\"headings\">URL</td><td>https://www.digitalocean.com/community/tutorials</td></tr></table></td></tr><tr><td class=\"headings\">social_media</td><td><table><tr><td class=\"headings\">description</td><td>twitter</td></tr><tr><td class=\"headings\">link</td><td>https://twitter.com/digitalocean</td></tr></table><table><tr><td class=\"headings\">description</td><td>facebook</td></tr><tr><td class=\"headings\">link</td><td>https://www.facebook.com/DigitalOceanCloudHosting</td></tr></table><table><tr><td class=\"headings\">description</td><td>github</td></tr><tr><td class=\"headings\">link</td><td>https://github.com/digitalocean</td></tr></table></td></tr></table></body></html>";
	private static final String EXPECTED3 = "<!DOCTYPE html><html><head><title></title><style>table, th, td {border: 1px solid black;}</style></head><body><table><tr><td class=\"headings\">first_name</td><td>Sammy</td></tr><tr><td class=\"headings\">last_name</td><td>Shark</td></tr><tr><td class=\"headings\">location</td><td>Ocean</td></tr><tr><td class=\"headings\">websites</td><td><table><tr><td class=\"headings\">description</td><td>work</td></tr><tr><td class=\"headings\">URL</td><td>https://www.digitalocean.com/</td></tr></table><table><tr><td class=\"headings\">desciption</td><td>tutorials</td></tr><tr><td class=\"headings\">URL</td><td>https://www.digitalocean.com/community/tutorials</td></tr></table></td></tr><tr><td class=\"headings\">social_media</td><td><table><tr><td class=\"headings\">description</td><td>twitter</td></tr><tr><td class=\"headings\">link</td><td>https://twitter.com/digitalocean</td></tr></table><table><tr><td class=\"headings\">description</td><td>facebook</td></tr><tr><td class=\"headings\">link</td><td>https://www.facebook.com/DigitalOceanCloudHosting</td></tr></table><table><tr><td class=\"headings\">description</td><td>github</td></tr><tr><td class=\"headings\">link</td><td>https://github.com/digitalocean</td></tr></table></td></tr></table></body></html>";
	private static final String EXPECTED4 = "<!DOCTYPE html><html><head><title>JSON to HTML</title><style>table, th, td {border: 1px solid black;}tr:hover {background-color: #f5f5f5;}td {padding: 5px;}table {width: 100%;}.headings {font-weight:bold;}</style></head><body><table><tr><td class=\"headings\">id</td><td>0001</td></tr><tr><td class=\"headings\">type</td><td>donut</td></tr><tr><td class=\"headings\">name</td><td>Cake</td></tr><tr><td class=\"headings\">ppu</td><td>0.55</td></tr><tr><td class=\"headings\">batters</td><td><table><tr><td class=\"headings\">batter</td><td><table><tr><td class=\"headings\">id</td><td>1001</td></tr><tr><td class=\"headings\">type</td><td>Regular</td></tr></table><table><tr><td class=\"headings\">id</td><td>1002</td></tr><tr><td class=\"headings\">type</td><td>Chocolate</td></tr></table><table><tr><td class=\"headings\">id</td><td>1003</td></tr><tr><td class=\"headings\">type</td><td>Blueberry</td></tr></table><table><tr><td class=\"headings\">id</td><td>1004</td></tr><tr><td class=\"headings\">type</td><td>Devil'sFood</td></tr></table></td></tr></table><tr><td class=\"headings\">topping</td><td><table><tr><td class=\"headings\">id</td><td>5001</td></tr><tr><td class=\"headings\">type</td><td>None</td></tr></table><table><tr><td class=\"headings\">id</td><td>5002</td></tr><tr><td class=\"headings\">type</td><td>Glazed</td></tr></table><table><tr><td class=\"headings\">id</td><td>5005</td></tr><tr><td class=\"headings\">type</td><td>Sugar</td></tr></table><table><tr><td class=\"headings\">id</td><td>5007</td></tr><tr><td class=\"headings\">type</td><td>PowderedSugar</td></tr></table><table><tr><td class=\"headings\">id</td><td>5006</td></tr><tr><td class=\"headings\">type</td><td>ChocolatewithSprinkles</td></tr></table><table><tr><td class=\"headings\">id</td><td>5003</td></tr><tr><td class=\"headings\">type</td><td>Chocolate</td></tr></table><table><tr><td class=\"headings\">id</td><td>5004</td></tr><tr><td class=\"headings\">type</td><td>Maple</td></tr></table></body></html>";
	private static final String EXPECTED5 = "<!DOCTYPE html><html><head><title>JSON to HTML</title><style>table, th, td {border: 1px solid black;}tr:hover {background-color: #f5f5f5;}td {padding: 5px;}table {width: 100%;}.headings {font-weight:bold;}</style></head><body><table><tr><td class=\"headings\">username</td><td>CT-SOC\\\\m.shinbori</td></tr><tr><td class=\"headings\">alert_type</td><td>watchlist.hit.ingress.process</td></tr><tr><td class=\"headings\">sensor_criticality</td><td>3.0</td></tr><tr><td class=\"headings\">modload_count</td><td>84</td></tr><tr><td class=\"headings\">report_score</td><td>1</td></tr><tr><td class=\"headings\">watchlist_id</td><td>f435128d-f5c4-4fa1-9f1f-14d0051ff09c</td></tr><tr><td class=\"headings\">sensor_id</td><td>11</td></tr><tr><td class=\"headings\">feed_name</td><td>bit9endpointvisibility</td></tr><tr><td class=\"headings\">created_time</td><td>2017-05-14T18:15:46.083Z</td></tr><tr><td class=\"headings\">report_ignored</td><td>false</td></tr><tr><td class=\"headings\">ioc_type</td><td>query</td></tr><tr><td class=\"headings\">watchlist_name</td><td>f435128d-f5c4-4fa1-9f1f-14d0051ff09c</td></tr><tr><td class=\"headings\">ioc_confidence</td><td>0.5</td></tr><tr><td class=\"headings\">alert_severity</td><td>0.675</td></tr><tr><td class=\"headings\">crossproc_count</td><td>3</td></tr><tr><td class=\"headings\">group</td><td>DefaultGroup</td></tr><tr><td class=\"headings\">hostname</td><td>EP-TEST3</td></tr><tr><td class=\"headings\">filemod_count</td><td>3</td></tr><tr><td class=\"headings\">comms_ip</td><td>10.1.22.145</td></tr><tr><td class=\"headings\">netconn_count</td><td>1</td></tr><tr><td class=\"headings\">interface_ip</td><td>10.1.22.145</td></tr><tr><td class=\"headings\">status</td><td>Unresolved</td></tr><tr><td class=\"headings\">process_path</td><td>c:\\\\programfiles(x86)\\\\teraterm\\\\ttermpro.exe</td></tr><tr><td class=\"headings\">description</td><td>Unsignedfilewithnetworkconnections</td></tr><tr><td class=\"headings\">process_name</td><td>ttermpro.exe</td></tr><tr><td class=\"headings\">process_unique_id</td><td>0000000b-0000-2508-01d2-cd281b7d57fd-00000001</td></tr><tr><td class=\"headings\">process_id</td><td>0000000b-0000-2508-01d2-cd281b7d57fd</td></tr><tr><td class=\"headings\">link</td><td>https://www.carbonblack.com/cbfeeds/endpointvisibility_feed.xhtml#9</td></tr><tr><td class=\"headings\">_version_</td><td>1567396458521427969</td></tr><tr><td class=\"headings\">regmod_count</td><td>0</td></tr><tr><td class=\"headings\">md5</td><td>67e177ced5462a5c13a95d9f01f6a3e7</td></tr><tr><td class=\"headings\">segment_id</td><td>1</td></tr><tr><td class=\"headings\">total_hosts</td><td>2</td></tr><tr><td class=\"headings\">feed_id</td><td>12</td></tr><tr><td class=\"headings\">ioc_value</td><td>{\\\"index_type\\\":\\\"events\\\",\\\"search_query\\\":\\\"cb.urlver=1&q=(netconn_count%3A%5B1%20TO%20*%5D%20digsig_result%3Aunsigned)\\\"}</td></tr><tr><td class=\"headings\">os_type</td><td>windows</td></tr><tr><td class=\"headings\">childproc_count</td><td>2</td></tr><tr><td class=\"headings\">unique_id</td><td>f8866800-f931-40cc-a390-4c2d8d3b5d6e</td></tr><tr><td class=\"headings\">feed_rating</td><td>3.0</td></tr></table></body></html>";
	/**
	 * Test the basic functionality of the utilities method's ability to 
	 * translate JSON to HTML.
	 * 
	 */
	@Test
	public void testBasicConversion() {
		assertEquals(EXPECTED1, JsonUtilities.jsonToHtml(JSON_SAMPLE1));
		assertEquals(EXPECTED4, JsonUtilities.jsonToHtml(JSON_SAMPLE2));
		assertEquals(EXPECTED5, JsonUtilities.jsonToHtml(JSON_SAMPLE3));
	}
	
	/**
	 * Test the ability to inject a HTML Document title into the generated HTML code.
	 * 
	 */
	@Test
	public void testInjectTitle() {
		assertEquals(EXPECTED2, JsonUtilities.jsonToHtml("Title", "", JSON_SAMPLE1));
	}
	
	/**
	 * Test the ability to inject CSS Style code into the generated HTML Code.
	 * 
	 */
	@Test
	public void testInjectStyle() {
		assertEquals(EXPECTED3, JsonUtilities.jsonToHtml("", STYLE_SAMPLE1, JSON_SAMPLE1));
	}
	
	/**
	 * This method is used to manually test changes to the JSON in a way that makes it possible
	 * to get an example of the output HTML string that will be generated.
	 * 
	 * @param args - Not used.
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String json = 
				"{\n" + 
				"      \"username\": \"CT-SOC\\\\m.shinbori\",\n" + 
				"      \"alert_type\": \"watchlist.hit.ingress.process\",\n" + 
				"      \"sensor_criticality\": 3.0,\n" + 
				"      \"modload_count\": 84,\n" + 
				"      \"report_score\": 1,\n" + 
				"      \"watchlist_id\": \"f435128d-f5c4-4fa1-9f1f-14d0051ff09c\",\n" + 
				"      \"sensor_id\": 11,\n" + 
				"      \"feed_name\": \"bit9endpointvisibility\",\n" + 
				"      \"created_time\": \"2017-05-14T18:15:46.083Z\",\n" + 
				"      \"report_ignored\": false,\n" + 
				"      \"ioc_type\": \"query\",\n" + 
				"      \"watchlist_name\": \"f435128d-f5c4-4fa1-9f1f-14d0051ff09c\",\n" + 
				"      \"ioc_confidence\": 0.5,\n" + 
				"      \"alert_severity\": 0.675,\n" + 
				"      \"crossproc_count\": 3,\n" + 
				"      \"group\": \"Default Group\",\n" + 
				"      \"hostname\": \"EP-TEST3\",\n" + 
				"      \"filemod_count\": 3,\n" + 
				"      \"comms_ip\": \"10.1.22.145\",\n" + 
				"      \"netconn_count\": 1,\n" + 
				"      \"interface_ip\": \"10.1.22.145\",\n" + 
				"      \"status\": \"Unresolved\",\n" + 
				"      \"process_path\": \"c:\\\\program files (x86)\\\\teraterm\\\\ttermpro.exe\",\n" + 
				"      \"description\": \"Unsigned file with network connections\",\n" + 
				"      \"process_name\": \"ttermpro.exe\",\n" + 
				"      \"process_unique_id\": \"0000000b-0000-2508-01d2-cd281b7d57fd-00000001\",\n" + 
				"      \"process_id\": \"0000000b-0000-2508-01d2-cd281b7d57fd\",\n" + 
				"      \"link\": \"https://www.carbonblack.com/cbfeeds/endpointvisibility_feed.xhtml#9\",\n" + 
				"      \"_version_\": 1567396458521427969,\n" + 
				"      \"regmod_count\": 0,\n" + 
				"      \"md5\": \"67e177ced5462a5c13a95d9f01f6a3e7\",\n" + 
				"      \"segment_id\": 1,\n" + 
				"      \"total_hosts\": 2,\n" + 
				"      \"feed_id\": 12,\n" + 
				"      \"ioc_value\": \"{\\\"index_type\\\": \\\"events\\\", \\\"search_query\\\": \\\"cb.urlver=1&q=(netconn_count%3A%5B1%20TO%20*%5D%20digsig_result%3Aunsigned)\\\"}\",\n" + 
				"      \"os_type\": \"windows\",\n" + 
				"      \"childproc_count\": 2,\n" + 
				"      \"unique_id\": \"f8866800-f931-40cc-a390-4c2d8d3b5d6e\",\n" + 
				"      \"feed_rating\": 3.0\n" + 
				"    }";
		String style = 
				 "table, th, td {"
				+    "border: 1px solid black;"
				+ "}";
		
		System.out.println(JsonUtilities.jsonToHtml("Title", "", JSON_SAMPLE1));
	}
}
