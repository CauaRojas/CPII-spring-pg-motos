package br.com.fiap.motos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Year;

public record VeiculoRequest(
        @Positive(message = "O Id deve ser um número positivo")
        @NotNull(message = "O id é obrigatório")
        Double preco,
        @Size(min = 2, max = 255, message = "A quantidade de caracteres do modelo deve estar entre")
        @NotNull(message = "O modelo é campo obrigatório")
        String modelo,
        @NotNull(message = "O ano de fabricação é campo obrigatório")
        @DateTimeFormat(pattern = "yyyy")
        Year anoDeFabricacao,
        @NotNull(message = "O id é obrigatório")
        @Size(min = 1, max = 255, message = "A quantidade de caracteres do nome deve estar entre 3 - 255")
        String nome,
        @Valid
        @NotNull(message = "O tipo é campo obrigatório")
        AbstractRequest tipo,
        @Valid
        @NotNull(message = "O fabricante é campo obrigatório")
        AbstractRequest fabricante,
        @NotNull(message = "A cor é campo obrigatório")
        @Size(min = 2, max = 255, message = "A quantidade de caracteres da cor deve estar entre")
        String cor,
        @Size(min = 2, max = 255, message = "A quantidade de caracteres da palavra de efeito deve estar entre")
        @NotNull(message = "A palavra de efeito é campo obrigatório")
        String palavraDeEfeito,
        @Positive(message = "As cilindradas deve ser um número positivo")
        @NotNull(message = "As cilindradas é obrigatório")
        Short cilindradas
) {
}
