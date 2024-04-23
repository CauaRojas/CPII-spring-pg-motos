package br.com.fiap.motos.service;

import br.com.fiap.motos.dto.request.TipoDeVeiculoRequest;
import br.com.fiap.motos.dto.response.TipoVeiculoResponse;
import br.com.fiap.motos.entity.TipoVeiculo;
import br.com.fiap.motos.repository.TipoVeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class TipoVeiculoService implements ServiceDTO<TipoVeiculo, TipoDeVeiculoRequest, TipoVeiculoResponse>{
    @Autowired
    private TipoVeiculoRepository repo;

    @Override
    public Collection<TipoVeiculo> findAll(Example<TipoVeiculo> example) {
        return repo.findAll(example);
    }

    @Override
    public TipoVeiculo findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public TipoVeiculo save(TipoVeiculo e) {
        return repo.save(e);
    }

    @Override
    public TipoVeiculo toEntity(TipoDeVeiculoRequest dto) {
        return TipoVeiculo.builder()
                .nome(dto.nome())
                .build();
    }

    @Override
    public TipoVeiculoResponse toResponse(TipoVeiculo e) {
        return TipoVeiculoResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .build();
    }
}
