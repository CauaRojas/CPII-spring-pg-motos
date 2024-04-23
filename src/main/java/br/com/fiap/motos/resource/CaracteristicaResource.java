package br.com.fiap.motos.resource;

import br.com.fiap.motos.dto.request.CaracteristicaRequest;
import br.com.fiap.motos.dto.response.CaracteristicaResponse;
import br.com.fiap.motos.entity.Caracteristica;
import br.com.fiap.motos.service.CaracteristicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(value = "/caracteristicas")
public class CaracteristicaResource implements ResourceDTO<Caracteristica, CaracteristicaRequest, CaracteristicaResponse>{

    @Autowired
    private CaracteristicaService service;

    @GetMapping
    public ResponseEntity<Collection<CaracteristicaResponse>> findAll(
            @RequestParam(name="nome", required = false) String nome,
            @RequestParam(name="descricao", required = false) String descricao,
            @RequestParam(name="veiculo.id", required = false) Long veiculoId
    ) {
        var caracteristica = Caracteristica.builder()
                .nome(nome)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();
        Example<Caracteristica> example = Example.of( caracteristica , matcher );

        var encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.noContent().build();

        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();

        return ResponseEntity.ok( resposta );
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CaracteristicaResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    public ResponseEntity<CaracteristicaResponse> save(@RequestBody @Valid CaracteristicaRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }
}
