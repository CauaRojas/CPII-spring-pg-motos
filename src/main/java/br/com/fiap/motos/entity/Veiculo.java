package br.com.fiap.motos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "TB_CP2_VEICULO")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_VEICULO")
    @SequenceGenerator(name = "SQ_VEICULO", sequenceName = "SQ_VEICULO", allocationSize = 1)
    @Column(name = "ID_VEICULO")
    private Long id;

    @Column(name = "NM_VEICULO")
    private String nome;

    @Column(name = "DT_FABRICACAO")
    private Year anoDeFabricacao;

    @Column(name = "DS_COR")
    private String cor;

    @Column(name = "VL_VEICULO")
    private Double preco;

    @Column(name = "NR_CILINDRADAS_MODELO")
    private Short cilindradas;

    @Column(name = "DS_MODELO")
    private String modelo;

    //15 digitos
    @Column(name = "DS_EFEITO")
    private String palavraDeEfeito;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_FABRICANTE", referencedColumnName = "ID_FABRICANTE",foreignKey = @ForeignKey(name = "FK_VEICULO_FABRICANTE"))
    private Fabricante fabricante;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "ID_TIPO_VEICULO",referencedColumnName = "ID_TIPO_VEICULO" ,foreignKey = @ForeignKey(name = "FK_VEICULO_TIPO_VEICULO"))
    private TipoVeiculo tipo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "TB_CP2_VEICULO_ACESSORIO",
            joinColumns = @JoinColumn(name = "ID_VEICULO",
                    referencedColumnName = "ID_VEICULO",
                    foreignKey = @ForeignKey(name = "FK_VEICULO_ACESSORIO")),
            inverseJoinColumns = @JoinColumn(name = "ID_ACESSORIO",
                    referencedColumnName = "ID_ACESSORIO",
                    foreignKey = @ForeignKey(name = "FK_ACESSORIO_VEICULO"))
    )
    private Set<Acessorio> acessorios = new LinkedHashSet<>();

}
