package com.br.shortener.url.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortCodeInfoResponse {
    private String message;
    private String url;
    private String shortCode;
    private Long numberOfAccesses;
    private Integer expirationTime;


    public ShortCodeInfoResponse(String url, String shortCode, Long numberOfAccesses, Integer expirationTime) {
        this.url = url;
        this.shortCode = shortCode;
        this.numberOfAccesses = numberOfAccesses;
        this.expirationTime = expirationTime;
    }
}
