package br.com.fiap.motos.resource;

import br.com.fiap.motos.dto.request.AcessorioRequest;
import br.com.fiap.motos.dto.request.VeiculoRequest;
import br.com.fiap.motos.dto.response.AcessorioResponse;
import br.com.fiap.motos.dto.response.VeiculoResponse;
import br.com.fiap.motos.entity.Acessorio;
import br.com.fiap.motos.entity.Veiculo;
import br.com.fiap.motos.repository.FabricanteRepository;
import br.com.fiap.motos.repository.TipoVeiculoRepository;
import br.com.fiap.motos.service.AcessorioService;
import br.com.fiap.motos.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Year;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoResource implements ResourceDTO<Veiculo, VeiculoRequest, VeiculoResponse> {
    @Autowired
    VeiculoService service;
    @Autowired
    AcessorioService acessorioService;
    @Autowired
    TipoVeiculoRepository tipoVeiculoRepository;
    @Autowired
    FabricanteRepository fabricanteRepository;

    @GetMapping
    public ResponseEntity<Collection<VeiculoResponse>> findAll (
            @RequestParam(name="preco", required = false) Double preco,
            @RequestParam(name="modelo", required = false) String modelo,
            @RequestParam(name="anoDeFabricacao", required = false) Year anoDeFabricacao,
            @RequestParam(name="nome", required = false) String nome,
            @RequestParam(name="tipo.id", required = false) Long idTipo,
            @RequestParam(name = "fabricante.id", required = false) Long idFabricante,
            @RequestParam(name="cor", required = false) String cor,
            @RequestParam(name="cilindradas", required = false) Short cilindradas
            ){
        var fabricante =  Objects.nonNull( idFabricante ) ? fabricanteRepository
                .findById( idFabricante )
                .orElse( null ) : null;

        var tipo =  Objects.nonNull( idTipo ) ? tipoVeiculoRepository
                .findById( idTipo )
                .orElse( null ) : null;

        Veiculo veiculo = Veiculo.builder()
                .cor(cor)
                .nome(nome)
                .preco(preco)
                .tipo(tipo)
                .fabricante(fabricante)
                .cilindradas(cilindradas)
                .modelo(modelo)
                .anoDeFabricacao(anoDeFabricacao)
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();
        Example<Veiculo> example = Example.of( veiculo , matcher );

        var encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.noContent().build();

        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();

        return ResponseEntity.ok( resposta );
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoResponse> findById(@PathVariable Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<VeiculoResponse> save(@RequestBody @Valid VeiculoRequest r) {
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

    @Transactional
    @PostMapping(value="/{id}/acessorios")
    public ResponseEntity<VeiculoResponse> saveAcessorio(@PathVariable Long id, @RequestBody @Valid AcessorioRequest r){
        var veiculo = service.findById( id );
        if (veiculo == null) return ResponseEntity.notFound().build();

        var acessorio = acessorioService.toEntity( r );
        var acessorios = veiculo.getAcessorios();
        acessorios.add( acessorio );
        veiculo.setAcessorios(acessorios);

        var saved = service.save( veiculo );
        var resposta = service.toResponse( saved );


        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }

    @Transactional
    @GetMapping(value="/{id}/acessorios")
    public ResponseEntity<Collection<AcessorioResponse>> findAcessorios(@PathVariable Long id)
            {
        var veiculo = service.findById( id );
        if (veiculo == null) return ResponseEntity.notFound().build();

        var acessorios = veiculo.getAcessorios();
        var resposta = acessorios.stream()
                .map( acessorioService::toResponse )
                .toList();

        if(resposta.isEmpty()) return ResponseEntity.noContent().build();


        return ResponseEntity.ok( resposta );
    }

}
