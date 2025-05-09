package com.br.shortener.url.ports.outbound;

public interface EncrypterPort {
    String encrypt(String toBeEncrypted);
}
