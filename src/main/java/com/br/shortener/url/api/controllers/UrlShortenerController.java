package com.br.shortener.url.api.controllers;

import com.br.shortener.url.api.dto.ApiErrorResponse;

import com.br.shortener.url.api.dto.ShortenUrlRequest;
import com.br.shortener.url.domain.ports.outbound.StorageUrlPort;
import com.br.shortener.url.domain.services.UrlShortenerService;
import com.br.shortener.url.api.dto.ShortenUrlResponse;
import com.br.shortener.url.domain.ports.outbound.StorageUrlPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    UrlShortenerService urlShortenerService;
    StorageUrlPort urlStorage;


    public UrlShortenerController(UrlShortenerService urlShortenerService,
                                  StorageUrlPort urlStorage
                                  ) {
        this.urlShortenerService = urlShortenerService;
        this.urlStorage = urlStorage;

    }

    @Operation(summary = "Create a shorten url for a given long url")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> postShortenUrl(@RequestBody ShortenUrlRequest request) {
        String longUrl = request.getUrl();
        String shortCode = urlShortenerService.generateShortUrl(longUrl);
        // optionalExpirationTime Regex = Xd, Xm and Xs (X is a time quantifier, ex: 10d = 10 days)
        Long optionalExpirationTime = urlShortenerService.calculateExpirationTime(request.getExpirationTime());

        urlStorage.saveUrl(shortCode, longUrl, optionalExpirationTime);

        ShortenUrlResponse response = new ShortenUrlResponse("URL criada com sucesso", shortCode);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Redirect to the original long url by passing a short code of the original url")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortenUrlResponse> redirect(@PathVariable String shortCode) {
        String longUrl = urlStorage.getUrl(shortCode);

        if (longUrl == null || longUrl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShortenUrlResponse response = new ShortenUrlResponse("URL encurtada identificada", longUrl);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).body(response);
    }
}
