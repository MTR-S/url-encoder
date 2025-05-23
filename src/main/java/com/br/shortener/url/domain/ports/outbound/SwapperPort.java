package com.br.shortener.url.domain.ports.outbound;

public interface SwapperPort {
    String pickRandomNumbers(String stringWith22Characters, String seedInput, int howManyNumbers);
}
