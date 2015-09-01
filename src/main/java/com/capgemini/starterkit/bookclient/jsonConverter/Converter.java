package com.capgemini.starterkit.bookclient.jsonConverter;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.capgemini.starterkit.bookclient.data.BookVO;

public class Converter {
	/*
	 * REV: convertJSON2BookList? nazwa metody jest troche mylaca
	 */
	public static List<BookVO> convertJSON2Book(String jsonString) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(jsonString, new TypeReference<List<BookVO>>(){});
	}

	public static BookVO convertJSON2BookVO(String jsonString) throws JsonParseException, JsonMappingException, IOException{
		return new ObjectMapper().readValue(jsonString, BookVO.class);
	}
}
