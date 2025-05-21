package com.br.shortener.url.domain.ports.outbound;

public interface EncrypterPort {
    String encrypt(String toBeEncrypted);
}
