package com.builtio.contentstack;

import com.builtio.volley.NetworkResponse;
import com.builtio.volley.Response;
import com.builtio.volley.error.ParseError;
import com.builtio.volley.request.JsonObjectRequest;
import com.builtio.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


class JSONUTF8Request extends JsonObjectRequest {

    private static final String TAG = "JSONUTF8Request";

    protected JSONUTF8Request(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, "UTF-8");
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseIgnoreCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }



}
