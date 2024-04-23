package br.com.fiap.motos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CaracteristicaRequest(
        @Valid @NotNull(message = "Veículo é obrigatório")
        AbstractRequest veiculo,
        @NotNull(message = "Nome é obrigatório")
        @Size(min = 2, max = 255, message = "A quantidade de caracteres do nome deve estar entre")
        String nome,
        @NotNull(message = "Descrição é obrigatório")
        @Size(min = 2, max = 255, message = "A quantidade de caracteres da descrição deve estar entre")
        String descricao
) {
}
