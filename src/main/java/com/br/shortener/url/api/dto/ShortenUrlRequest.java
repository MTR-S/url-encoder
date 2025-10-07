package com.br.shortener.url.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenUrlRequest {
    private String url;
    private String expirationTime;

    public ShortenUrlRequest(String url, String expirationTime) {
        this.url = url;
        this.expirationTime = expirationTime;
    }
}
