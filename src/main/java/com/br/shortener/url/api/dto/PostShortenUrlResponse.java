package com.br.shortener.url.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostShortenUrlResponse {
    private String message;
    private String url;

    public PostShortenUrlResponse(String message, String url) {
        this.message = message;
        this.url = url;
    }
}
