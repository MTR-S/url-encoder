package com.br.shortener.url.domain.ports.outbound;

public interface StorageUrlPort {
    String getUrl(String shortCode);
    void saveUrl(String shortCode, String longUrl, Long expirationTime);
}
