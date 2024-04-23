package br.com.fiap.motos.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FabricanteRequest(
        @Size(min = 2, max = 255, message = "A quantidade de caracteres do nome deve estar entre")
        @NotNull(message = "O nome é campo obrigatório")
        String nome,
        @Size(min = 2, max = 255, message = "A quantidade de caracteres do nome deve estar entre")
        @NotNull(message = "O nome é campo obrigatório")
        String nomeFantasia
) {
}
