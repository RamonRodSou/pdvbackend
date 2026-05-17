package br.com.technosou.pedido;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "tenant_id", nullable = false)
    public UUID tenantId;

    public String slug;

    @Column(nullable = false)
    public BigDecimal total;

    @Enumerated(EnumType.STRING)
    public StatusPedido status;

    public String mesa;

    public String cliente;

    @Column(length = 11 )
    public String telefone;

    @Column(length = 255)
    public String descricao;

    @Column(name = "data_criacao")
    public ZonedDateTime dataCriacao;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ItemPedido> itens;
}
