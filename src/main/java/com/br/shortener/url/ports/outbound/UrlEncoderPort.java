package com.br.shortener.url.ports.outbound;

public interface UrlEncoderPort {
    String encodeToUtf8(String url);
}
