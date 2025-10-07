package com.br.shortener.url.infrastructure.adapters;

import com.br.shortener.url.domain.ports.outbound.UrlEncoderPort;
import com.br.shortener.url.exceptions.UnsupportedEncodingException;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// Verificar se o try catch esta bem posicionado (Olhar clean code)

@Component
public class UrlEncoderAdapter implements UrlEncoderPort {

    @Override
    public String encodeToUtf8(String url) {
        try{
            String urlDecoded = URLDecoder.decode(url, StandardCharsets.UTF_8);

            if(urlDecoded.equals(url)){
                return URLEncoder.encode(url, StandardCharsets.UTF_8);
            }

            return url;
        }catch(UnsupportedEncodingException e){
            throw new UnsupportedEncodingException("Error encoding URL to UTF-8", e);
        }
    }
}
