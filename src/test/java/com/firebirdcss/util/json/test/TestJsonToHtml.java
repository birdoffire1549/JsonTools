package com.firebirdcss.util.json.test;

import org.junit.Test;

import com.firebirdcss.util.json.JsonUtilities;

public class TestJsonToHtml {
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
	
	
	@Test
	public void test() {
		JsonUtilities.jsonToHtml(JSON_SAMPLE1);
	}

}
