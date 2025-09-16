package com.br.shortener.url.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortenUrlResponse {
    private String message;
    private String url;

    public ShortenUrlResponse(String message, String url) {
        this.message = message;
        this.url = url;
    }
}
