package com.contentstack.sdk;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for NodeToHTML class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestNodeToHTML {

    private Option mockOption = new Option() {
        @Override
        public String renderOptions(JSONObject embeddedObject, Metadata metadata) {
            return "rendered";
        }

        @Override
        public String renderMark(MarkType markType, String renderText) {
            switch (markType) {
                case BOLD: return "<strong>" + renderText + "</strong>";
                case ITALIC: return "<em>" + renderText + "</em>";
                case UNDERLINE: return "<u>" + renderText + "</u>";
                case STRIKETHROUGH: return "<strike>" + renderText + "</strike>";
                case INLINECODE: return "<code>" + renderText + "</code>";
                case SUBSCRIPT: return "<sub>" + renderText + "</sub>";
                case SUPERSCRIPT: return "<sup>" + renderText + "</sup>";
                case BREAK: return renderText + "<br />";
                default: return renderText;
            }
        }

        @Override
        public String renderNode(String nodeType, JSONObject nodeObject, NodeCallback callback) {
            return "<node/>";
        }
    };

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testPrivateConstructorThrowsException() {
        try {
            java.lang.reflect.Constructor<NodeToHTML> constructor = 
                NodeToHTML.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertNotNull(e);
            assertTrue(e.getCause() instanceof IllegalStateException);
            assertEquals(ErrorMessages.NODE_TO_HTML_INSTANTIATION, e.getCause().getMessage());
        }
    }

    // ========== TEXT NODE TO HTML TESTS ==========

    @Test
    public void testTextNodeToHTMLWithPlainText() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Hello World");
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertNotNull(result);
        assertEquals("Hello World", result);
    }

    @Test
    public void testTextNodeToHTMLWithNewlineReplacement() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Line 1\nLine 2\nLine 3");
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<br />"));
        assertFalse(result.contains("\n"));
    }

    @Test
    public void testTextNodeToHTMLWithBold() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Bold text");
        node.put("bold", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<strong>"));
        assertTrue(result.contains("</strong>"));
    }

    @Test
    public void testTextNodeToHTMLWithItalic() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Italic text");
        node.put("italic", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<em>"));
        assertTrue(result.contains("</em>"));
    }

    @Test
    public void testTextNodeToHTMLWithUnderline() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Underlined text");
        node.put("underline", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<u>"));
        assertTrue(result.contains("</u>"));
    }

    @Test
    public void testTextNodeToHTMLWithStrikethrough() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Strikethrough text");
        node.put("strikethrough", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<strike>"));
        assertTrue(result.contains("</strike>"));
    }

    @Test
    public void testTextNodeToHTMLWithInlineCode() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "code");
        node.put("inlineCode", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<code>"));
        assertTrue(result.contains("</code>"));
    }

    @Test
    public void testTextNodeToHTMLWithSubscript() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "subscript");
        node.put("subscript", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<sub>"));
        assertTrue(result.contains("</sub>"));
    }

    @Test
    public void testTextNodeToHTMLWithSuperscript() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "superscript");
        node.put("superscript", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<sup>"));
        assertTrue(result.contains("</sup>"));
    }

    @Test
    public void testTextNodeToHTMLWithBreak() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "text");
        node.put("break", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<br />"));
    }

    @Test
    public void testTextNodeToHTMLWithBreakAlreadyPresent() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "text with\nlinebreak");
        node.put("break", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        // Should not add extra break if already has <br />
        assertTrue(result.contains("<br />"));
    }

    // ========== COMBINED FORMATTING TESTS ==========

    @Test
    public void testTextNodeToHTMLWithBoldAndItalic() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Bold and Italic");
        node.put("bold", true);
        node.put("italic", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<strong>"));
        assertTrue(result.contains("<em>"));
    }

    @Test
    public void testTextNodeToHTMLWithAllFormatting() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Formatted");
        node.put("bold", true);
        node.put("italic", true);
        node.put("underline", true);
        node.put("strikethrough", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<strong>"));
        assertTrue(result.contains("<em>"));
        assertTrue(result.contains("<u>"));
        assertTrue(result.contains("<strike>"));
    }

    @Test
    public void testTextNodeToHTMLWithCodeAndSubscript() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "code_text");
        node.put("inlineCode", true);
        node.put("subscript", true);
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<code>"));
        assertTrue(result.contains("<sub>"));
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testTextNodeToHTMLWithEmptyText() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "");
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertEquals("", result);
    }

    @Test
    public void testTextNodeToHTMLWithWhitespace() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "   ");
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertEquals("   ", result);
    }

    @Test
    public void testTextNodeToHTMLWithMultipleNewlines() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Line 1\n\n\nLine 2");
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        int brCount = result.split("<br />").length - 1;
        assertTrue(brCount >= 3);
    }

    @Test
    public void testTextNodeToHTMLWithSpecialCharacters() throws JSONException {
        JSONObject node = new JSONObject();
        node.put("text", "Special: <>&\"'");
        
        String result = NodeToHTML.textNodeToHTML(node, mockOption);
        
        assertTrue(result.contains("<>&\"'"));
    }

    // ========== MARK TYPE ENUM TESTS ==========

    @Test
    public void testMarkTypeEnumValues() {
        MarkType[] markTypes = MarkType.values();
        assertNotNull(markTypes);
        assertEquals(8, markTypes.length);
    }

    @Test
    public void testMarkTypeEnumContainsAllTypes() {
        assertNotNull(MarkType.BOLD);
        assertNotNull(MarkType.ITALIC);
        assertNotNull(MarkType.UNDERLINE);
        assertNotNull(MarkType.STRIKETHROUGH);
        assertNotNull(MarkType.INLINECODE);
        assertNotNull(MarkType.SUBSCRIPT);
        assertNotNull(MarkType.SUPERSCRIPT);
        assertNotNull(MarkType.BREAK);
    }

    @Test
    public void testMarkTypeValueOf() {
        assertEquals(MarkType.BOLD, MarkType.valueOf("BOLD"));
        assertEquals(MarkType.ITALIC, MarkType.valueOf("ITALIC"));
        assertEquals(MarkType.UNDERLINE, MarkType.valueOf("UNDERLINE"));
        assertEquals(MarkType.STRIKETHROUGH, MarkType.valueOf("STRIKETHROUGH"));
        assertEquals(MarkType.INLINECODE, MarkType.valueOf("INLINECODE"));
        assertEquals(MarkType.SUBSCRIPT, MarkType.valueOf("SUBSCRIPT"));
        assertEquals(MarkType.SUPERSCRIPT, MarkType.valueOf("SUPERSCRIPT"));
        assertEquals(MarkType.BREAK, MarkType.valueOf("BREAK"));
    }

    // ========== STYLE TYPE ENUM TESTS ==========

    @Test
    public void testStyleTypeEnumValues() {
        StyleType[] styleTypes = StyleType.values();
        assertNotNull(styleTypes);
        assertEquals(5, styleTypes.length);
    }

    @Test
    public void testStyleTypeEnumContainsAllTypes() {
        assertNotNull(StyleType.BLOCK);
        assertNotNull(StyleType.INLINE);
        assertNotNull(StyleType.LINK);
        assertNotNull(StyleType.DISPLAY);
        assertNotNull(StyleType.DOWNLOAD);
    }

    @Test
    public void testStyleTypeValueOf() {
        assertEquals(StyleType.BLOCK, StyleType.valueOf("BLOCK"));
        assertEquals(StyleType.INLINE, StyleType.valueOf("INLINE"));
        assertEquals(StyleType.LINK, StyleType.valueOf("LINK"));
        assertEquals(StyleType.DISPLAY, StyleType.valueOf("DISPLAY"));
        assertEquals(StyleType.DOWNLOAD, StyleType.valueOf("DOWNLOAD"));
    }
}

