package com.br.shortener.url.domain.ports.outbound;

public interface UrlEncoderPort {
    String encodeToUtf8(String url);
}
