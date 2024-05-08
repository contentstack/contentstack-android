package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface Option {
    String renderOptions(JSONObject embeddedObject, Metadata metadata);
    String renderMark(MarkType markType, String renderText);
    String renderNode(String nodeType, JSONObject nodeObject, NodeCallback callback);
}

interface NodeCallback {

    /**
     * The function takes a JSONArray of nodes and returns a string representation of their children.
     *
     * @param nodeJsonArray The `nodeJsonArray` parameter is a JSONArray object that contains a
     *                      collection of JSON objects representing nodes. Each JSON object represents a node and contains
     *                      information about the node's properties and children.
     * @return The method is returning a String.
     */
    String renderChildren(JSONArray nodeJsonArray);
}