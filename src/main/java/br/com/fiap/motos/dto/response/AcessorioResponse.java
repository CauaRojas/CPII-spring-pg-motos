package br.com.fiap.motos.dto.response;

import lombok.Builder;

@Builder
public record AcessorioResponse(
        Long id,
        String nome,
        Double preco
) {
}
