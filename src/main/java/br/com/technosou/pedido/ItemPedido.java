package br.com.technosou.pedido;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "itens_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "pedido_id")
    public Pedido pedido;

    @Column(name = "produto_id", nullable = false)
    public UUID produtoId;

    public Integer quantidade;

    @Column(name = "preco_unitario")
    public BigDecimal precoUnitario;

    public BigDecimal subtotal;
}
