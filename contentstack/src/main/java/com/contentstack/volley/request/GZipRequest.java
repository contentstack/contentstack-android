package com.contentstack.volley.request;

import com.contentstack.volley.NetworkResponse;
import com.contentstack.volley.Response;
import com.contentstack.volley.error.ParseError;
import com.contentstack.volley.toolbox.HttpHeaderParser;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class GZipRequest extends StringRequest {

	public GZipRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		super(method, url, listener, errorListener);
	}

	public GZipRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
		super(url, listener, errorListener);
	}

	// parse the gzip response using a GZIPInputStream
	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		String output = "";
		try {
			GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
			InputStreamReader reader = new InputStreamReader(gStream);
			BufferedReader in = new BufferedReader(reader);
			String read;
			while ((read = in.readLine()) != null) {
				output += read;
			}
			reader.close();
			in.close();
			gStream.close();
		} catch (IOException e) {
			return Response.error(new ParseError());
		}
		return Response.success(output, HttpHeaderParser.parseCacheHeaders(response));
	}
}