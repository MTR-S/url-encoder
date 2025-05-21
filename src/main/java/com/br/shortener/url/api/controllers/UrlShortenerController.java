package com.br.shortener.url.api.controllers;

import com.br.shortener.url.api.dto.ApiErrorResponse;

import com.br.shortener.url.domain.services.UrlShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api")
public class UrlShortenerController {

    UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @GetMapping("/test")
    public ResponseEntity<Void> getTest() {
        System.out.println(urlShortenerService.generateShortUrl("https://www.youtube.com/"));

        /*Fluxo de encriptação da url*/

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Authenticate user and return token")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/shorten")
    public ResponseEntity<Void> postShortenUrl() {

        /*Fluxo de encriptação da url*/

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Authenticate user and return token")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping("/links")
    public ResponseEntity<Void> getAllShortenUrls() {

        /*Busca no banco ou na nuvem as urls pelos códigos específicos*/

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Authenticate user and return token")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @DeleteMapping("/links/{shortCode}")
    public ResponseEntity<Void> deleteShortenUrl(@PathVariable String shortCode) {

        /*Fluxo de encriptação da url*/

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Authenticate user and return token")
    //@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping("/accessdata/{shortCode}")
    public ResponseEntity<Void> shortenUrl(@PathVariable String shortCode) {

        /*Fluxo de encriptação da url*/

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
