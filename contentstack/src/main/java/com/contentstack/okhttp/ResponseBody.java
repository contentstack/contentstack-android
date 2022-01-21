/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.contentstack.okhttp;

import com.contentstack.okhttp.internal.Util;
import com.contentstack.okio.BufferedSource;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import static com.contentstack.okhttp.internal.Util.UTF_8;

public abstract class ResponseBody implements Closeable {
  /** Multiple calls to {@link #charStream()} must return the same instance. */
  private Reader reader;

  public abstract MediaType contentType();

  /**
   * Returns the number of bytes in that will returned by {@link #bytes}, or
   * {@link #byteStream}, or -1 if unknown.
   */
  public abstract long contentLength();

  public final InputStream byteStream() {
    return source().inputStream();
  }

  public abstract BufferedSource source();

  public final byte[] bytes() throws IOException {
    long contentLength = contentLength();
    if (contentLength > Integer.MAX_VALUE) {
      throw new IOException("Cannot buffer entire body for content length: " + contentLength);
    }

    BufferedSource source = source();
    byte[] bytes;
    try {
      bytes = source.readByteArray();
    } finally {
      Util.closeQuietly(source);
    }
    if (contentLength != -1 && contentLength != bytes.length) {
      throw new IOException("Content-Length and stream length disagree");
    }
    return bytes;
  }

  /**
   * Returns the response as a character stream decoded with the charset
   * of the Content-Type header. If that header is either absent or lacks a
   * charset, this will attempt to decode the response body as UTF-8.
   */
  public final Reader charStream() {
    Reader r = reader;
    return r != null ? r : (reader = new InputStreamReader(byteStream(), charset()));
  }

  /**
   * Returns the response as a string decoded with the charset of the
   * Content-Type header. If that header is either absent or lacks a charset,
   * this will attempt to decode the response body as UTF-8.
   */
  public final String string() throws IOException {
    return new String(bytes(), charset().name());
  }

  private Charset charset() {
    MediaType contentType = contentType();
    return contentType != null ? contentType.charset(UTF_8) : UTF_8;
  }

  @Override public void close() throws IOException {
    source().close();
  }
}
