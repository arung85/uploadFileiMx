package com.imatrix.files.upload.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class IMatrixRestApiCaller {
	public static void main(String[] args) throws IOException {

		if (args[0].equals("UPLOAD_FILE")) {
			System.out.println("Calling " + args[0] + " method.");
			UPLOAD_FILE(args);
		} else if (args[0].equals("MY_OTHER_METHOD")) {
			// Do other main stuff, or print error message
		} else {
			// Do other main stuff, or print error message
		}
		System.out.println("Completed " + args[0] + " method process.");
	}

	public static String UPLOAD_FILE(String[] args) throws IOException {
		System.out.println("inside : UPLOAD_FILE method: "+args);
		String retunStr = null;
		Properties prop = new Properties();
		try {
			// load a properties file from class path, inside static method
			prop.load(
					IMatrixRestApiCaller.class.getClassLoader().getResourceAsStream("IMatrixRestApiCaller.properties"));

			// get the property value and print it out
			System.out.println("URL from PROP File "+prop.getProperty("url"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream("IMatrixRestApiCaller.properties");
		// String url = "http://localhost:8090/uploadFileParam";
		String url = "http://" + args[1] + "/uploadFileParam";
		File file = new File(args[10]);

		System.out.println("URL from string arg "+url);
		
		// Create connection
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Add request header
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");

		// Create request body
		String requestBody = "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"reference\"\r\n\r\n" + args[2] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"partyName\"\r\n\r\n" + args[3] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"fileDescription\"\r\n\r\n" + args[4] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"voucherType\"\r\n\r\n" + args[5] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"orderNo\"\r\n\r\n" + args[6] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"voucherNo\"\r\n\r\n" + args[7] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"comGuid\"\r\n\r\n" + args[8] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"guid\"\r\n\r\n" + args[9] + "\r\n"
				+ "------WebKitFormBoundary7MA4YWxkTrZu0gW\r\n"
				+ "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n"
				+ "Content-Type: " + file.toURI().toURL().openConnection().getContentType() + "\r\n\r\n";
		String requestBodyEnd = "\r\n------WebKitFormBoundary7MA4YWxkTrZu0gW--";

		// Write request body
		con.setDoOutput(true);
		OutputStream outputStream = con.getOutputStream();
		outputStream.write(requestBody.getBytes());

		FileInputStream inputStream = new FileInputStream(file);
		byte[] buffer = new byte[4096];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}

		outputStream.write(requestBodyEnd.getBytes());

		// Get response
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Print response
		System.out.println(response.toString());
		retunStr = response.toString();
		return retunStr;
	}
}