package br.com.fiap.motos.service;

import br.com.fiap.motos.dto.request.VeiculoRequest;
import br.com.fiap.motos.dto.response.VeiculoResponse;
import br.com.fiap.motos.entity.Veiculo;
import br.com.fiap.motos.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class VeiculoService implements ServiceDTO<Veiculo, VeiculoRequest, VeiculoResponse>{

    @Autowired
    private VeiculoRepository repo;

    @Autowired
    private TipoVeiculoService tipoVeiculoService;

    @Autowired
    private FabricanteService fabricanteService;

    @Override
    public Collection<Veiculo> findAll(Example<Veiculo> example) {
        return repo.findAll(example);
    }

    @Override
    public Veiculo findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Veiculo save(Veiculo e) {
        return repo.save(e);
    }

    @Override
    public Veiculo toEntity(VeiculoRequest dto) {

        var tipoVeiculo = tipoVeiculoService.findById(dto.tipo().id());
        var fabricante = fabricanteService.findById(dto.fabricante().id());

        return Veiculo.builder()
                .nome(dto.nome())
                .cor(dto.cor())
                .anoDeFabricacao(dto.anoDeFabricacao())
                .palavraDeEfeito(dto.palavraDeEfeito())
                .tipo(tipoVeiculo)
                .fabricante(fabricante)
                .modelo(dto.modelo())
                .cilindradas(dto.cilindradas())
                .preco(dto.preco())
                .build();


    }

    @Override
    public VeiculoResponse toResponse(Veiculo e) {

        var tipoVeiculo = tipoVeiculoService.toResponse(e.getTipo());
        var fabricante = fabricanteService.toResponse(e.getFabricante());

        return VeiculoResponse.builder()
                .id(e.getId())
                .nome(e.getNome())
                .cor(e.getCor())
                .tipo(tipoVeiculo)
                .fabricante(fabricante)
                .anoDeFabricacao(e.getAnoDeFabricacao())
                .palavraDeEfeito(e.getPalavraDeEfeito())
                .modelo(e.getModelo())
                .cilindradas(e.getCilindradas())
                .preco(e.getPreco())
                .build();
    }
}
