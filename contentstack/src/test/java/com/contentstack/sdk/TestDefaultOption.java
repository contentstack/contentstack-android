package com.contentstack.sdk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Comprehensive tests for DefaultOption class to achieve 99%+ coverage.
 */
@RunWith(RobolectricTestRunner.class)
public class TestDefaultOption {

    private DefaultOption defaultOption;
    private Metadata metadata;

    @Before
    public void setUp() {
        defaultOption = new DefaultOption();
    }

    // ==================== renderOptions Tests ====================

    @Test
    public void testRenderOptionsBlock() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "Test Title");
        embeddedObject.put("_content_type_uid", "test_content_type");
        
        metadata = new Metadata("", "", "", "", "BLOCK", "", null);
        String result = defaultOption.renderOptions(embeddedObject, metadata);
        
        assertTrue(result.contains("<div><p>Test Title</p>"));
        assertTrue(result.contains("test_content_type"));
    }

    @Test
    public void testRenderOptionsInline() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "Inline Title");
        
        metadata = new Metadata("", "", "", "", "INLINE", "", null);
        String result = defaultOption.renderOptions(embeddedObject, metadata);
        
        assertEquals("<span>Inline Title</span>", result);
    }

    @Test
    public void testRenderOptionsLink() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "Link Title");
        embeddedObject.put("url", "https://example.com");
        
        metadata = new Metadata("", "", "", "", "LINK", "", null);
        String result = defaultOption.renderOptions(embeddedObject, metadata);
        
        assertEquals("<a href=\"https://example.com\">Link Title</a>", result);
    }

    @Test
    public void testRenderOptionsDisplay() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "Image Title");
        embeddedObject.put("url", "https://example.com/image.jpg");
        
        metadata = new Metadata("", "", "", "", "DISPLAY", "", null);
        String result = defaultOption.renderOptions(embeddedObject, metadata);
        
        assertTrue(result.contains("<img src=\"https://example.com/image.jpg\""));
        assertTrue(result.contains("alt=\"Image Title\""));
    }

    @Test
    public void testFindTitleOrUidWithTitle() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "My Title");
        embeddedObject.put("uid", "my_uid");
        
        String result = defaultOption.findTitleOrUid(embeddedObject);
        assertEquals("My Title", result);
    }

    @Test
    public void testFindTitleOrUidWithUidOnly() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("uid", "my_uid");
        
        String result = defaultOption.findTitleOrUid(embeddedObject);
        assertEquals("my_uid", result);
    }

    @Test
    public void testFindTitleOrUidWithEmptyTitle() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "");
        embeddedObject.put("uid", "my_uid");
        
        String result = defaultOption.findTitleOrUid(embeddedObject);
        assertEquals("my_uid", result);
    }

    @Test
    public void testFindTitleOrUidWithNull() {
        String result = defaultOption.findTitleOrUid(null);
        assertEquals("", result);
    }

    @Test
    public void testFindAssetTitleWithTitle() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "Asset Title");
        
        String result = defaultOption.findAssetTitle(embeddedObject);
        assertEquals("Asset Title", result);
    }

    @Test
    public void testFindAssetTitleWithFilename() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("filename", "image.jpg");
        
        String result = defaultOption.findAssetTitle(embeddedObject);
        assertEquals("image.jpg", result);
    }

    @Test
    public void testFindAssetTitleWithUid() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("uid", "asset_uid");
        
        String result = defaultOption.findAssetTitle(embeddedObject);
        assertEquals("asset_uid", result);
    }

    @Test
    public void testFindAssetTitlePriority() throws JSONException {
        JSONObject embeddedObject = new JSONObject();
        embeddedObject.put("title", "Title");
        embeddedObject.put("filename", "file.jpg");
        embeddedObject.put("uid", "uid123");
        
        String result = defaultOption.findAssetTitle(embeddedObject);
        assertEquals("Title", result); // Title has highest priority
    }

    // ==================== renderMark Tests ====================

    @Test
    public void testRenderMarkSuperscript() {
        String result = defaultOption.renderMark(MarkType.SUPERSCRIPT, "text");
        assertEquals("<sup>text</sup>", result);
    }

    @Test
    public void testRenderMarkSubscript() {
        String result = defaultOption.renderMark(MarkType.SUBSCRIPT, "text");
        assertEquals("<sub>text</sub>", result);
    }

    @Test
    public void testRenderMarkInlineCode() {
        String result = defaultOption.renderMark(MarkType.INLINECODE, "code");
        assertEquals("<span>code</span>", result);
    }

    @Test
    public void testRenderMarkStrikethrough() {
        String result = defaultOption.renderMark(MarkType.STRIKETHROUGH, "text");
        assertEquals("<strike>text</strike>", result);
    }

    @Test
    public void testRenderMarkUnderline() {
        String result = defaultOption.renderMark(MarkType.UNDERLINE, "text");
        assertEquals("<u>text</u>", result);
    }

    @Test
    public void testRenderMarkItalic() {
        String result = defaultOption.renderMark(MarkType.ITALIC, "text");
        assertEquals("<em>text</em>", result);
    }

    @Test
    public void testRenderMarkBold() {
        String result = defaultOption.renderMark(MarkType.BOLD, "text");
        assertEquals("<strong>text</strong>", result);
    }

    @Test
    public void testRenderMarkBreak() {
        String result = defaultOption.renderMark(MarkType.BREAK, "text\nmore");
        assertEquals("<br />textmore", result);
    }

    // ==================== renderNode Tests ====================

    @Test
    public void testRenderNodeParagraph() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        nodeObject.put("children", new JSONArray());
        
        NodeCallback callback = new NodeCallback() {
            @Override
            public String renderChildren(JSONArray children) {
                return "child content";
            }
        };
        
        String result = defaultOption.renderNode("p", nodeObject, callback);
        assertEquals("<p>child content</p>", result);
    }

    @Test
    public void testRenderNodeAnchor() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("href", "https://example.com");
        nodeObject.put("attrs", attrs);
        nodeObject.put("children", new JSONArray());
        
        NodeCallback callback = new NodeCallback() {
            @Override
            public String renderChildren(JSONArray children) {
                return "link text";
            }
        };
        
        String result = defaultOption.renderNode("a", nodeObject, callback);
        assertTrue(result.contains("<a"));
        assertTrue(result.contains("href="));
        assertTrue(result.contains("link text"));
    }

    @Test
    public void testRenderNodeImage() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("src", "https://example.com/image.jpg");
        nodeObject.put("attrs", attrs);
        nodeObject.put("children", new JSONArray());
        
        NodeCallback callback = new NodeCallback() {
            @Override
            public String renderChildren(JSONArray children) {
                return "";
            }
        };
        
        String result = defaultOption.renderNode("img", nodeObject, callback);
        assertTrue(result.contains("<img"));
        assertTrue(result.contains("src="));
    }

    @Test
    public void testRenderNodeImageWithAssetLink() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("asset-link", "https://cdn.example.com/asset.jpg");
        nodeObject.put("attrs", attrs);
        nodeObject.put("children", new JSONArray());
        
        NodeCallback callback = new NodeCallback() {
            @Override
            public String renderChildren(JSONArray children) {
                return "";
            }
        };
        
        String result = defaultOption.renderNode("img", nodeObject, callback);
        assertTrue(result.contains("asset.jpg"));
    }

    @Test
    public void testRenderNodeImageWithAssetLinkAndLink() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("asset-link", "https://cdn.example.com/asset.jpg");
        attrs.put("link", "https://example.com");
        nodeObject.put("attrs", attrs);
        nodeObject.put("children", new JSONArray());
        
        NodeCallback callback = new NodeCallback() {
            @Override
            public String renderChildren(JSONArray children) {
                return "";
            }
        };
        
        String result = defaultOption.renderNode("img", nodeObject, callback);
        assertTrue(result.contains("<a href="));
        assertTrue(result.contains("<img"));
    }

    @Test
    public void testRenderNodeAllHeadings() throws JSONException {
        NodeCallback callback = createSimpleCallback();
        JSONObject nodeObject = new JSONObject();
        nodeObject.put("children", new JSONArray());
        
        assertEquals("<h1>content</h1>", defaultOption.renderNode("h1", nodeObject, callback));
        assertEquals("<h2>content</h2>", defaultOption.renderNode("h2", nodeObject, callback));
        assertEquals("<h3>content</h3>", defaultOption.renderNode("h3", nodeObject, callback));
        assertEquals("<h4>content</h4>", defaultOption.renderNode("h4", nodeObject, callback));
        assertEquals("<h5>content</h5>", defaultOption.renderNode("h5", nodeObject, callback));
        assertEquals("<h6>content</h6>", defaultOption.renderNode("h6", nodeObject, callback));
    }

    @Test
    public void testRenderNodeLists() throws JSONException {
        NodeCallback callback = createSimpleCallback();
        JSONObject nodeObject = new JSONObject();
        nodeObject.put("children", new JSONArray());
        
        assertEquals("<ol>content</ol>", defaultOption.renderNode("ol", nodeObject, callback));
        assertEquals("<ul>content</ul>", defaultOption.renderNode("ul", nodeObject, callback));
        assertEquals("<li>content</li>", defaultOption.renderNode("li", nodeObject, callback));
    }

    @Test
    public void testRenderNodeTable() throws JSONException {
        NodeCallback callback = createSimpleCallback();
        JSONObject nodeObject = new JSONObject();
        nodeObject.put("children", new JSONArray());
        
        assertEquals("<table >content</table>", defaultOption.renderNode("table", nodeObject, callback));
        assertEquals("<thead >content</thead>", defaultOption.renderNode("thead", nodeObject, callback));
        assertEquals("<tbody>content</tbody>", defaultOption.renderNode("tbody", nodeObject, callback));
        assertEquals("<tfoot>content</tfoot>", defaultOption.renderNode("tfoot", nodeObject, callback));
        assertEquals("<tr>content</tr>", defaultOption.renderNode("tr", nodeObject, callback));
        assertEquals("<th>content</th>", defaultOption.renderNode("th", nodeObject, callback));
        assertEquals("<td>content</td>", defaultOption.renderNode("td", nodeObject, callback));
    }

    @Test
    public void testRenderNodeOtherElements() throws JSONException {
        NodeCallback callback = createSimpleCallback();
        JSONObject nodeObject = new JSONObject();
        nodeObject.put("children", new JSONArray());
        
        assertEquals("<hr />", defaultOption.renderNode("hr", nodeObject, callback));
        assertEquals("<blockquote>content</blockquote>", defaultOption.renderNode("blockquote", nodeObject, callback));
        assertEquals("<code>content</code>", defaultOption.renderNode("code", nodeObject, callback));
        assertEquals("", defaultOption.renderNode("reference", nodeObject, callback));
        assertEquals("<fragment>content</fragment>", defaultOption.renderNode("fragment", nodeObject, callback));
    }

    @Test
    public void testRenderNodeEmbed() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("src", "https://youtube.com/embed/xyz");
        nodeObject.put("attrs", attrs);
        nodeObject.put("children", new JSONArray());
        
        NodeCallback callback = createSimpleCallback();
        String result = defaultOption.renderNode("embed", nodeObject, callback);
        
        assertTrue(result.contains("<iframe"));
        assertTrue(result.contains("src="));
    }

    @Test
    public void testRenderNodeDefault() throws JSONException {
        NodeCallback callback = createSimpleCallback();
        JSONObject nodeObject = new JSONObject();
        nodeObject.put("children", new JSONArray());
        
        String result = defaultOption.renderNode("unknown_tag", nodeObject, callback);
        assertEquals("content", result);
    }

    // ==================== strAttrs Tests ====================

    @Test
    public void testStrAttrsEmpty() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        String result = defaultOption.strAttrs(nodeObject);
        assertEquals("", result);
    }

    @Test
    public void testStrAttrsSimple() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("id", "test-id");
        attrs.put("class", "test-class");
        nodeObject.put("attrs", attrs);
        
        String result = defaultOption.strAttrs(nodeObject);
        assertTrue(result.contains("id=\"test-id\""));
        assertTrue(result.contains("class=\"test-class\""));
    }

    @Test
    public void testStrAttrsWithStyle() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        JSONObject style = new JSONObject();
        style.put("color", "red");
        style.put("font-size", "14px");
        attrs.put("style", style);
        nodeObject.put("attrs", attrs);
        
        String result = defaultOption.strAttrs(nodeObject);
        assertTrue(result.contains("style="));
        assertTrue(result.contains("color: red"));
        assertTrue(result.contains("font-size: 14px"));
    }

    @Test
    public void testStrAttrsIgnoresSpecialKeys() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        attrs.put("href", "should-be-ignored");
        attrs.put("src", "should-be-ignored");
        attrs.put("asset-link", "should-be-ignored");
        attrs.put("url", "should-be-ignored");
        attrs.put("id", "should-be-included");
        nodeObject.put("attrs", attrs);
        
        String result = defaultOption.strAttrs(nodeObject);
        assertFalse(result.contains("href="));
        assertFalse(result.contains("src="));
        assertFalse(result.contains("asset-link="));
        assertFalse(result.contains("url="));
        assertTrue(result.contains("id=\"should-be-included\""));
    }

    @Test
    public void testStrAttrsEmptyAttrs() throws JSONException {
        JSONObject nodeObject = new JSONObject();
        JSONObject attrs = new JSONObject();
        nodeObject.put("attrs", attrs);
        
        String result = defaultOption.strAttrs(nodeObject);
        assertEquals("", result);
    }

    // ==================== Helper Methods ====================

    private NodeCallback createSimpleCallback() {
        return new NodeCallback() {
            @Override
            public String renderChildren(JSONArray children) {
                return "content";
            }
        };
    }
}

