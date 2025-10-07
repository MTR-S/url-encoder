package com.br.shortener.url.api.controllers;

import com.br.shortener.url.api.dto.ApiErrorResponse;

import com.br.shortener.url.domain.services.S3Service;
import com.br.shortener.url.domain.services.UrlShortenerService;
import com.br.shortener.url.api.dto.ShortenUrlResponse;
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
    S3Service s3Service;

    public UrlShortenerController(UrlShortenerService urlShortenerService,
                                  S3Service s3Service) {
        this.urlShortenerService = urlShortenerService;
        this.s3Service = s3Service;
    }

    @Operation(summary = "Create a shorten url for a given long url")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> postShortenUrl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("url");
        String shortCode = urlShortenerService.generateShortUrl(longUrl);

        s3Service.saveUrl(shortCode, longUrl);

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
        String longUrl = s3Service.getUrl(shortCode);

        if (longUrl == null || longUrl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        ShortenUrlResponse response = new ShortenUrlResponse("URL encurtada identificada", longUrl);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).body(response);
    }
}
