package com.contentstack.sdk;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class DefaultOption implements Option {
    @Override
    public String renderOptions(JSONObject embeddedObject, Metadata metadata) {
        switch (metadata.getStyleType()) {
            case BLOCK:
                return "<div><p>" + findTitleOrUid(embeddedObject) + "</p><div><p>Content type: <span>" + embeddedObject.optString("_content_type_uid") + "</span></p></div>";
            case INLINE:
                return "<span>" + findTitleOrUid(embeddedObject) + "</span>";
            case LINK:
                return "<a href=\"" + embeddedObject.optString("url") + "\">" + findTitleOrUid(embeddedObject) + "</a>";
            case DISPLAY:
                return "<img src=\"" + embeddedObject.optString("url") + "\" alt=\"" + findAssetTitle(embeddedObject) + "\" />";
            default:
                return "";
        }
    }

    @Override
    public String renderMark(MarkType markType, String text) {
        switch (markType) {
            case SUPERSCRIPT:
                return "<sup>" + text + "</sup>";
            case SUBSCRIPT:
                return "<sub>" + text + "</sub>";
            case INLINECODE:
                return "<span>" + text + "</span>";
            case STRIKETHROUGH:
                return "<strike>" + text + "</strike>";
            case UNDERLINE:
                return "<u>" + text + "</u>";
            case ITALIC:
                return "<em>" + text + "</em>";
            case BOLD:
                return "<strong>" + text + "</strong>";
            case BREAK:
                return "<br />" + text.replace("\n", "");
            default:
                return text;
        }
    }

    private String escapeInjectHtml(JSONObject nodeObj, String nodeType) {
        String injectedHtml = getNodeStr(nodeObj, nodeType);
        return TextUtils.htmlEncode(injectedHtml);
    }

    @Override
    public String renderNode(String nodeType, JSONObject nodeObject, NodeCallback callback) {
        String strAttrs = strAttrs(nodeObject);
        String children = callback.renderChildren(nodeObject.optJSONArray("children"));
        switch (nodeType) {
            case "p":
                return "<p" + strAttrs + ">" + children + "</p>";
            case "a":
                return "<a" + strAttrs + " href=\"" + escapeInjectHtml(nodeObject, "href") + "\">" + children + "</a>";
            case "img":
                String assetLink = getNodeStr(nodeObject, "asset-link");
                if (!assetLink.isEmpty()) {
                    JSONObject attrs = nodeObject.optJSONObject("attrs");
                    if (attrs.has("link")) {
                        return "<a href=\"" + escapeInjectHtml(nodeObject, "link") + "\" >" + "<img" + strAttrs + " src=\"" + escapeInjectHtml(nodeObject, "asset-link") + "\" />" + children + "</a>";
                    }
                    return "<img" + strAttrs + " src=\"" + escapeInjectHtml(nodeObject, "asset-link") + "\" />" + children;
                }
                return "<img" + strAttrs + " src=\"" + escapeInjectHtml(nodeObject, "src") + "\" />" + children;
            case "embed":
                return "<iframe" + strAttrs + " src=\"" + escapeInjectHtml(nodeObject, "src") + "\"" + children + "</iframe>";
            case "h1":
                return "<h1" + strAttrs + ">" + children + "</h1>";
            case "h2":
                return "<h2" + strAttrs + ">" + children + "</h2>";
            case "h3":
                return "<h3" + strAttrs + ">" + children + "</h3>";
            case "h4":
                return "<h4" + strAttrs + ">" + children + "</h4>";
            case "h5":
                return "<h5" + strAttrs + ">" + children + "</h5>";
            case "h6":
                return "<h6" + strAttrs + ">" + children + "</h6>";
            case "ol":
                return "<ol" + strAttrs + ">" + children + "</ol>";
            case "ul":
                return "<ul" + strAttrs + ">" + children + "</ul>";
            case "li":
                return "<li" + strAttrs + ">" + children + "</li>";
            case "hr":
                return "<hr" + strAttrs + " />";
            case "table":
                return "<table " + strAttrs + ">" + children + "</table>";
            case "thead":
                return "<thead " + strAttrs + ">" + children + "</thead>";
            case "tbody":
                return "<tbody" + strAttrs + ">" + children + "</tbody>";
            case "tfoot":
                return "<tfoot" + strAttrs + ">" + children + "</tfoot>";
            case "tr":
                return "<tr" + strAttrs + ">" + children + "</tr>";
            case "th":
                return "<th" + strAttrs + ">" + children + "</th>";
            case "td":
                return "<td" + strAttrs + ">" + children + "</td>";
            case "blockquote":
                return "<blockquote" + strAttrs + ">" + children + "</blockquote>";
            case "code":
                return "<code" + strAttrs + ">" + children + "</code>";
            case "reference":
                return "";
            case "fragment":
                return "<fragment" + strAttrs + ">" + children + "</fragment>";
            default:
                return children;
        }
    }

    String strAttrs(JSONObject nodeObject) {
        StringBuilder result = new StringBuilder();
        if (nodeObject.has("attrs")) {
            JSONObject attrsObject = nodeObject.optJSONObject("attrs");
            if (attrsObject != null && attrsObject.length() > 0) {
                for (Iterator<String> it = attrsObject.keys(); it.hasNext(); ) {
                    String key = it.next();
                    Object objValue = attrsObject.opt(key);
                    String value = objValue.toString();
                    // If style is available, do styling calculations
                    if (Objects.equals(key, "style")) {
                        String resultStyle = stringifyStyles(attrsObject.optJSONObject("style"));
                        result.append(" ").append(key).append("=\"").append(resultStyle).append("\"");
                    } else {
                        String[] ignoreKeys = {"href", "asset-link", "src", "url"};
                        ArrayList<String> ignoreKeysList = new ArrayList<>(Arrays.asList(ignoreKeys));
                        if (!ignoreKeysList.contains(key)) {
                            result.append(" ").append(key).append("=\"").append(value).append("\"");
                        }
                    }
                }
            }
        }
        return result.toString();
    }

    private String stringifyStyles(JSONObject style) {
        Map<String, String> styleMap = new HashMap<>();

        // Convert JSONObject to a Map
        Iterator<String> keys = style.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String value = null;
            try {
                value = style.getString(key);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            styleMap.put(key, value);
        }

        StringBuilder styleString = new StringBuilder();

        for (Map.Entry<String, String> entry : styleMap.entrySet()) {
            String property = entry.getKey();
            String value = entry.getValue();

            styleString.append(property).append(": ").append(value).append("; ");
        }

        return styleString.toString();
    }

    private String getNodeStr(JSONObject nodeObject, String key) {
        String herf = nodeObject.optJSONObject("attrs").optString(key); // key might be [href/src]
        if (herf == null || herf.isEmpty()) {
            herf = nodeObject.optJSONObject("attrs").optString("url");
        }
        return herf;
    }

    protected String findTitleOrUid(JSONObject embeddedObject) {
        String _title = "";
        if (embeddedObject != null) {
            if (embeddedObject.has("title") && !embeddedObject.optString("title").isEmpty()) {
                _title = embeddedObject.optString("title");
            } else if (embeddedObject.has("uid")) {
                _title = embeddedObject.optString("uid");
            }
        }
        return _title;
    }

    protected String findAssetTitle(JSONObject embeddedObject) {
        String _title = "";
        if (embeddedObject != null) {
            if (embeddedObject.has("title") && !embeddedObject.optString("title").isEmpty()) {
                _title = embeddedObject.optString("title");
            } else if (embeddedObject.has("filename")) {
                _title = embeddedObject.optString("filename");
            } else if (embeddedObject.has("uid")) {
                _title = embeddedObject.optString("uid");
            }
        }
        return _title;
    }
}
