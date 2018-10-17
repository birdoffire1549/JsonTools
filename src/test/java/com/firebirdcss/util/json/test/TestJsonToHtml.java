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
	
	/**
	 * Test the basic functionality of the utilities method's ability to 
	 * translate JSON to HTML.
	 * 
	 */
	@Test
	public void testBasicConversion() {
		assertEquals(EXPECTED1, JsonUtilities.jsonToHtml(JSON_SAMPLE1));
		assertEquals(EXPECTED4, JsonUtilities.jsonToHtml(JSON_SAMPLE2));
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
				"  \"host_count\": 1,\n" + 
				"  \"digsig_result\": \"Unsigned\",\n" + 
				"  \"observed_filename\": [\n" + 
				"    \"c:\\\\users\\\\nu.asdjhfkj\\\\desktop\\\\training_work\\\\jwendjf.exe\"\n" + 
				"  ],\n" + 
				"  \"product_version\": \"(unknown)\",\n" + 
				"  \"alliance_link_virustotal\": \"https://www.virustotal.com/file/2f97a105321817aa10f42cfeb56538410fdecdb876913b5bfa260c5422e74ce7/analysis/1495439395/\",\n" + 
				"  \"orig_mod_len\": 200192,\n" + 
				"  \"is_executable_image\": true,\n" + 
				"  \"is_64bit\": false,\n" + 
				"  \"alliance_score_virustotal\": 44,\n" + 
				"  \"group\": [\n" + 
				"    \"Default Group\"\n" + 
				"  ],\n" + 
				"  \"alliance_score_iconmatching\": 80,\n" + 
				"  \"file_version\": \"(unknown)\",\n" + 
				"  \"alliance_data_iconmatching\": [\n" + 
				"    \"fileofficehigh\"\n" + 
				"  ],\n" + 
				"  \"company_name\": \"(unknown)\",\n" + 
				"  \"alliance_link_srsthreat\": \"https://services.bit9.com/Services/extinfo.aspx?ak=b8b4e631d4884ad1c56f50e4a5ee9279&sg=0313e1735f6cec221b1d686bd4de23ee&md5=55d4ef740d7c10d6a5e1be9b69fb0e15\",\n" + 
				"  \"alliance_updated_iconmatching\": \"2018-10-16T15:54:21Z\",\n" + 
				"  \"md5\": \"55D4EF7ABCDEF0D6A5E1BE9B69FB0E15\",\n" + 
				"  \"product_name\": \"(unknown)\",\n" + 
				"  \"alliance_data_virustotal\": [\n" + 
				"    \"55d4ef740d7c10d6a5a1b269fb0e15\"\n" + 
				"  ],\n" + 
				"  \"digsig_result_code\": \"2147800\",\n" + 
				"  \"timestamp\": \"2017-06-01T01:47:32.201Z\",\n" + 
				"  \"alliance_updated_virustotal\": \"2017-06-29T21:17:34Z\",\n" + 
				"  \"internal_name\": \"(unknown)\",\n" + 
				"  \"copied_mod_len\": 200192,\n" + 
				"  \"server_added_timestamp\": \"2017-06-01T01:47:32.201Z\",\n" + 
				"  \"facet_id\": 0,\n" + 
				"  \"alliance_data_srsthreat\": [\n" + 
				"    \"55d4ef740d7c10d69b69fb0e15\"\n" + 
				"  ],\n" + 
				"  \"icon\": \"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\\nZwAAAABJRU5ErkJggg==\\n\",\n" + 
				"  \"endpoint\": [\n" + 
				"    \"WIN-AG31V7235SQ|13\"\n" + 
				"  ],\n" + 
				"  \"alliance_link_iconmatching\": \"https://www.bit9.com/cbfeeds/iconmatching_feed.xhtml#file_office\",\n" + 
				"  \"watchlists\": [\n" + 
				"    {\n" + 
				"      \"wid\": \"367\",\n" + 
				"      \"value\": \"2018-10-04T06:10:03.473Z\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"wid\": \"9\",\n" + 
				"      \"value\": \"2017-06-29T21:30:03.993Z\"\n" + 
				"    },\n" + 
				"    {\n" + 
				"      \"wid\": \"6\",\n" + 
				"      \"value\": \"2017-06-01T01:50:02.67Z\"\n" + 
				"    }\n" + 
				"  ],\n" + 
				"  \"signed\": \"Unsigned\",\n" + 
				"  \"alliance_score_srsthreat\": 100,\n" + 
				"  \"original_filename\": \"(unknown)\",\n" + 
				"  \"cb_version\": 714,\n" + 
				"  \"os_type\": \"Windows\",\n" + 
				"  \"file_desc\": \"(unknown)\",\n" + 
				"  \"alliance_updated_srsthreat\": \"2017-06-01T02:17:08Z\",\n" + 
				"  \"last_seen\": \"2018-10-16T18:51:18.626Z\"\n" + 
				"}";
		
		
		System.out.println(JsonUtilities.jsonToHtml(json));
	}
}
