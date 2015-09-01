package com.capgemini.starterkit.bookclient.getData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONObject;

import com.capgemini.starterkit.bookclient.data.BookVO;
import com.capgemini.starterkit.bookclient.jsonConverter.Converter;
import com.capgemini.starterkit.bookclient.staticFields.StaticFields;

public class DataProviderImpl {

	private static HttpURLConnection conn;
	private static BufferedReader br;

	public static List<BookVO> getBooksByTitle(String prefix)
			throws JsonParseException, JsonMappingException, IOException {
		String jsonData = getJSON_DataFromRestPoint(StaticFields.URL + prefix);
		if (jsonData != null) {
			return Converter.convertJSON2Book(jsonData);
		}
		return null;
	}

	private static String getJSON_DataFromRestPoint(String query) throws IOException {
		try {
			URL url = new URL(query);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() != 200) {
				return null;
			}
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String jsonData = br.readLine();
			conn.disconnect();
			return jsonData;
		} catch (IOException e) {
			return null;
		}
	}

	public static BookVO saveBook(BookVO bookVO) throws Exception {

		URL myurl = new URL(StaticFields.saveBookURL);
		HttpURLConnection con = (HttpURLConnection) myurl.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);

		con.setRequestProperty("Content-Type", "application/json; charset=utf8");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Method", "POST");
		OutputStream os = con.getOutputStream();
		os.write(generateJSON(bookVO).toString().getBytes("UTF-8"));
		InputStream is = new BufferedInputStream(con.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		os.close();

		int HttpResult = con.getResponseCode();
		if (HttpResult == HttpURLConnection.HTTP_OK) {
			return Converter.convertJSON2BookVO(br.readLine());
		} else {
			return null;
		}

	}

	public static JSONObject generateJSON(BookVO bookVO) throws MalformedURLException {

		JSONObject reqparam = new JSONObject();
		reqparam.put("id", "");
		reqparam.put("title", bookVO.getTitle());
		reqparam.put("authors", bookVO.getAuthors());
		return reqparam;

	}

}
