package jsonConverter.test;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

import com.capgemini.starterkit.bookclient.jsonConverter.Converter;

public class ConverterTest {

	@Test
	public void shouldConvertSipleJSON() throws JsonParseException, JsonMappingException, IOException{
		String testJSON = "[{\"id\":1,\"title\":\"Pierwsza ksiazka\",\"authors\":\"jan sasa\"},{\"id\":1,\"title\":\"Pierwsza ksiazka\",\"authors\":\"jan sasa\"}]";
		System.out.print(Converter.convertJSON2Book(testJSON).size());
	}
}
