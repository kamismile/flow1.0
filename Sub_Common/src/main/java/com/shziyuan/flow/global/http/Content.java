package com.shziyuan.flow.global.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;

public class Content {

    public static final Content NO_CONTENT = new Content(new byte[] {}, ContentType.DEFAULT_BINARY,0);

    private final byte[] raw;
    private final ContentType type;
    
    private int httpStatusCode;

    public Content(final byte[] raw, final ContentType type,int httpStatusCode) {
        super();
        this.raw = raw;
        this.type = type;
        this.httpStatusCode = httpStatusCode;
    }

    public ContentType getType() {
        return this.type;
    }

    public byte[] asBytes() {
        return this.raw.clone();
    }

    public String asString() {
        Charset charset = this.type.getCharset();
        if (charset == null) {
            charset = HTTP.DEF_CONTENT_CHARSET;
        }
        try {
            return new String(this.raw, charset.name());
        } catch (final UnsupportedEncodingException ex) {
            return new String(this.raw);
        }
    }
    public String asString(Charset charset) {
        return new String(this.raw, charset);
    }

    public InputStream asStream() {
        return new ByteArrayInputStream(this.raw);
    }

    @Override
    public String toString() {
        return asString();
    }

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

}
