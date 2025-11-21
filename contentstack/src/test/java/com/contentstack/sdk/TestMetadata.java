package com.contentstack.sdk;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.jar.Attributes;

import static org.junit.Assert.*;

/**
 * Comprehensive unit tests for Metadata class.
 */
@RunWith(RobolectricTestRunner.class)
public class TestMetadata {

    // ========== CONSTRUCTOR TESTS ==========

    @Test
    public void testConstructorWithAllParameters() {
        Attributes attrs = new Attributes();
        Metadata metadata = new Metadata("Sample text", "entry", "uid123", 
            "content_type_uid", "block", "<div>HTML</div>", attrs);
        
        assertNotNull(metadata);
        assertEquals("Sample text", metadata.getText());
        assertEquals("entry", metadata.getItemType());
        assertEquals("uid123", metadata.getItemUid());
        assertEquals("content_type_uid", metadata.getContentTypeUid());
        assertEquals(StyleType.BLOCK, metadata.getStyleType());
        assertEquals("<div>HTML</div>", metadata.getOuterHTML());
        assertNotNull(metadata.getAttributes());
    }

    @Test
    public void testConstructorWithBlockStyleType() {
        Attributes attrs = new Attributes();
        Metadata metadata = new Metadata("text", "asset", "asset_uid", 
            "asset", "block", "<p>content</p>", attrs);
        
        assertEquals(StyleType.BLOCK, metadata.getStyleType());
    }

    @Test
    public void testConstructorWithInlineStyleType() {
        Attributes attrs = new Attributes();
        Metadata metadata = new Metadata("text", "entry", "entry_uid", 
            "blog", "inline", "<span>text</span>", attrs);
        
        assertEquals(StyleType.INLINE, metadata.getStyleType());
    }

    @Test
    public void testConstructorWithLinkStyleType() {
        Attributes attrs = new Attributes();
        Metadata metadata = new Metadata("Link text", "entry", "entry_123", 
            "page", "link", "<a href='#'>Link</a>", attrs);
        
        assertEquals(StyleType.LINK, metadata.getStyleType());
    }

    @Test
    public void testConstructorWithDisplayStyleType() {
        Attributes attrs = new Attributes();
        Metadata metadata = new Metadata("Display", "asset", "img_uid", 
            "asset", "display", "<img src='#'/>", attrs);
        
        assertEquals(StyleType.DISPLAY, metadata.getStyleType());
    }

    @Test
    public void testConstructorWithDownloadStyleType() {
        Attributes attrs = new Attributes();
        Metadata metadata = new Metadata("Download", "asset", "file_uid", 
            "asset", "download", "<a download>File</a>", attrs);
        
        assertEquals(StyleType.DOWNLOAD, metadata.getStyleType());
    }

    // ========== GETTER TESTS ==========

    @Test
    public void testGetText() {
        Metadata metadata = new Metadata("Test text content", "entry", "uid", 
            "ct_uid", "block", "<div/>", new Attributes());
        
        assertEquals("Test text content", metadata.getText());
    }

    @Test
    public void testGetItemType() {
        Metadata metadata = new Metadata("text", "asset", "uid", 
            "ct_uid", "block", "<div/>", new Attributes());
        
        assertEquals("asset", metadata.getItemType());
    }

    @Test
    public void testGetItemUid() {
        Metadata metadata = new Metadata("text", "entry", "unique_id_123", 
            "ct_uid", "block", "<div/>", new Attributes());
        
        assertEquals("unique_id_123", metadata.getItemUid());
    }

    @Test
    public void testGetContentTypeUid() {
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "blog_post", "block", "<div/>", new Attributes());
        
        assertEquals("blog_post", metadata.getContentTypeUid());
    }

    @Test
    public void testGetStyleType() {
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "inline", "<span/>", new Attributes());
        
        assertEquals(StyleType.INLINE, metadata.getStyleType());
    }

    @Test
    public void testGetOuterHTML() {
        String html = "<div class='content'>Hello World</div>";
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "block", html, new Attributes());
        
        assertEquals(html, metadata.getOuterHTML());
    }

    @Test
    public void testGetAttributes() {
        Attributes attrs = new Attributes();
        attrs.putValue("key", "value");
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "block", "<div/>", attrs);
        
        assertNotNull(metadata.getAttributes());
        assertEquals(attrs, metadata.getAttributes());
    }

    // ========== TO STRING TESTS ==========

    @Test
    public void testToString() {
        Metadata metadata = new Metadata("Sample", "entry", "uid123", 
            "blog", "block", "<div/>", new Attributes());
        
        String toString = metadata.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Sample"));
        assertTrue(toString.contains("entry"));
        assertTrue(toString.contains("uid123"));
        assertTrue(toString.contains("blog"));
        assertTrue(toString.contains("BLOCK"));
    }

    @Test
    public void testToStringContainsAllFields() {
        Metadata metadata = new Metadata("Text", "asset", "asset_uid", 
            "asset", "inline", "<span>HTML</span>", new Attributes());
        
        String toString = metadata.toString();
        assertTrue(toString.contains("text='Text'"));
        assertTrue(toString.contains("type='asset'"));
        assertTrue(toString.contains("uid='asset_uid'"));
        assertTrue(toString.contains("contentTypeUid='asset'"));
        assertTrue(toString.contains("sysStyleType=INLINE"));
    }

    // ========== STYLE TYPE CONVERSION TESTS ==========

    @Test
    public void testStyleTypeConversionWithLowerCase() {
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "block", "<div/>", new Attributes());
        
        assertEquals(StyleType.BLOCK, metadata.getStyleType());
    }

    @Test
    public void testStyleTypeConversionWithUpperCase() {
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "INLINE", "<span/>", new Attributes());
        
        assertEquals(StyleType.INLINE, metadata.getStyleType());
    }

    @Test
    public void testStyleTypeConversionWithMixedCase() {
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "LiNk", "<a/>", new Attributes());
        
        assertEquals(StyleType.LINK, metadata.getStyleType());
    }

    // ========== EDGE CASE TESTS ==========

    @Test
    public void testWithEmptyText() {
        Metadata metadata = new Metadata("", "entry", "uid", 
            "ct_uid", "block", "<div/>", new Attributes());
        
        assertEquals("", metadata.getText());
    }

    @Test
    public void testWithEmptyHtml() {
        Metadata metadata = new Metadata("text", "entry", "uid", 
            "ct_uid", "block", "", new Attributes());
        
        assertEquals("", metadata.getOuterHTML());
    }

    @Test
    public void testWithComplexHtml() {
        String complexHtml = "<div class='wrapper'><p>Text</p><img src='url'/></div>";
        Metadata metadata = new Metadata("text", "asset", "uid", 
            "asset", "display", complexHtml, new Attributes());
        
        assertEquals(complexHtml, metadata.getOuterHTML());
    }

    // ========== MULTIPLE INSTANCE TESTS ==========

    @Test
    public void testMultipleInstancesAreIndependent() {
        Metadata m1 = new Metadata("Text 1", "entry", "uid1", 
            "ct1", "block", "<div/>", new Attributes());
        Metadata m2 = new Metadata("Text 2", "asset", "uid2", 
            "ct2", "inline", "<span/>", new Attributes());
        
        assertNotEquals(m1.getText(), m2.getText());
        assertNotEquals(m1.getItemType(), m2.getItemType());
        assertNotEquals(m1.getItemUid(), m2.getItemUid());
        assertNotEquals(m1.getContentTypeUid(), m2.getContentTypeUid());
        assertNotEquals(m1.getStyleType(), m2.getStyleType());
    }
}

