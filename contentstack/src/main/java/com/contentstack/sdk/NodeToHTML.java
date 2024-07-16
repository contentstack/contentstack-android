package com.contentstack.sdk;

import org.json.JSONObject;

public class NodeToHTML {
    private NodeToHTML() {
        throw new IllegalStateException("Could not create instance of NodeToHTML");
    }
    public static String textNodeToHTML(JSONObject nodeText, Option renderOption) {
        String text = nodeText.optString("text");
        text = text.replace("\n", "<br />");
        if (nodeText.has("superscript")) {
            text = renderOption.renderMark(MarkType.SUPERSCRIPT, text);
        }
        if (nodeText.has("subscript")) {
            text = renderOption.renderMark(MarkType.SUBSCRIPT, text);
        }
        if (nodeText.has("inlineCode")) {
            text = renderOption.renderMark(MarkType.INLINECODE, text);
        }
        if (nodeText.has("strikethrough")) {
            text = renderOption.renderMark(MarkType.STRIKETHROUGH, text);
        }
        if (nodeText.has("underline")) {
            text = renderOption.renderMark(MarkType.UNDERLINE, text);
        }
        if (nodeText.has("italic")) {
            text = renderOption.renderMark(MarkType.ITALIC, text);
        }
        if (nodeText.has("bold")) {
            text = renderOption.renderMark(MarkType.BOLD, text);
        }
        if (nodeText.has("break")) {
            if (!text.contains("<br />")) {
                text = renderOption.renderMark(MarkType.BREAK, text);
            }
            // text = renderOption.renderMark(MarkType.BREAK, text);
        }
        return text;
    }
}

enum MarkType {
    BOLD, ITALIC, UNDERLINE, STRIKETHROUGH, INLINECODE, SUBSCRIPT, SUPERSCRIPT, BREAK,
}
