package com.br.shortener.url.domain.ports.outbound;

import com.br.shortener.url.api.dto.ShortCodeInfoResponse;

public interface StorageUrlPort {
    String getUrl(String shortCode);
    void saveUrl(String shortCode, String longUrl, Long expirationTime);
    ShortCodeInfoResponse getShortUrlInfo(String shortCode);
}
