package org.spariharconsultinginc.DesignPatterns.DataLoads;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	//Get Jackson Library dependencies
	//Build utility to scan Json file to HashMap
	public List<HashMap<String, String>> getJsonData(String jsonFilePath) throws IOException{
		
		//Convert Json file content into JSON string
		
		String jsonContent = FileUtils.readFileToString(new File(jsonFilePath)
				,StandardCharsets.UTF_8);
		
		ObjectMapper mapper = new ObjectMapper();
		//Type Reference class is used to marshall or unmarshall the data into respective formats
		//Here we are Unmarshalling data from Json into list of HashMap 
		List<HashMap<String,String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String,String>>>() {});
		
		//For every index in the JSON file, it will create a HashMap. and all HashMaps will be
		//put into a list and we can access content of JSON file using list index
		return data;
		
		
	}

}
