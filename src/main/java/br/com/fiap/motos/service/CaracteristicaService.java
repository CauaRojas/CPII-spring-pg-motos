package br.com.fiap.motos.service;

import br.com.fiap.motos.dto.request.CaracteristicaRequest;
import br.com.fiap.motos.dto.response.CaracteristicaResponse;
import br.com.fiap.motos.entity.Caracteristica;
import br.com.fiap.motos.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CaracteristicaService implements ServiceDTO<Caracteristica, CaracteristicaRequest, CaracteristicaResponse>{

    @Autowired
    private CaracteristicaRepository repo;

    @Override
    public Collection<Caracteristica> findAll(Example<Caracteristica> example) {
        return repo.findAll(example);
    }

    @Override
    public Caracteristica findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Caracteristica save(Caracteristica e) {
        return repo.save(e);
    }

    @Override
    public Caracteristica toEntity(CaracteristicaRequest dto) {
        return Caracteristica.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .build();
    }

    @Override
    public CaracteristicaResponse toResponse(Caracteristica e) {
        return CaracteristicaResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .descricao(e.getDescricao())
                .build();
    }
}
