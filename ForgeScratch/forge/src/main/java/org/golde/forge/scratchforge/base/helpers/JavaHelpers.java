package org.golde.forge.scratchforge.base.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class JavaHelpers {

	public static Field getField(Class<?> clazz, String fieldName) throws RuntimeException{
		Class<?> tmpClass = clazz;
		do {
			try {
				Field f = tmpClass.getDeclaredField(fieldName);
				return f;
			} catch (NoSuchFieldException e) {
				tmpClass = tmpClass.getSuperclass();
			}
		} while (tmpClass != null);

		throw new RuntimeException("Field '" + fieldName + "' not found on class " + clazz);
	}

	public static String joinStrings(List<String> list, String conjunction, int iequals)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i = iequals; i < list.size(); ++i)
		{
			String item = list.get(i);

			if (first)
				first = false;
			else
				sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}

	public static String makeJavaId(String name) {
		String result = "";
		for (int i = 0; i < name.length(); ++i) {
			char c = name.charAt(i);
			if (isJavaId(c)) {
				result = result + c;
			}
			else {
				result = result + "_";
			}
		}

		return result.toLowerCase();
	}

	public static boolean isJavaId(char c) {
		if (c >= 'A' && c <= 'Z')
			return true;
		else if (c >= 'a' && c <= 'z') 
			return true;
		else if (c >= '0' && c <= '9') 
			return true;

		return false;
	}

	public static int hexToMinecraftColor(String hex) {
		hex = hex.replace("#", "");
		return (int) Long.parseLong(hex, 16);
	}

	public static RequestResponce sendRequest(String ip, String args, String getOrPost) {
		try {
			if(getOrPost.equals("GET")) {
				return sendGetRequest(ip);
			}
			else {
				return sendPostRequest(ip, args);
			}
		}
		catch(Exception e) {
			PLog.error(e, "Failed to send HTTP Request");
			return new RequestResponce(-1, "Failed to send HTTP Request! See logger for details!");
		}
	}

	private static RequestResponce sendPostRequest(String url, String urlParameters) throws Exception{
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		if(urlParameters != null) {
			wr.writeBytes(urlParameters);
		}
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		//PLog.info("\nSending 'POST' request to URL : " + url);
		//PLog.info("Post parameters : " + urlParameters);
		//PLog.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return new RequestResponce(responseCode, response.toString());
	}

	private static RequestResponce sendGetRequest(String url) throws Exception{

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		//PLog.info("\nSending 'GET' request to URL : " + url);
		//PLog.info("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return new RequestResponce(responseCode, response.toString());
	}

}
