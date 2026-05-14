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

    @Column(nullable = false)
    public BigDecimal total;

    @Enumerated(EnumType.STRING)
    public StatusPedido status;

    public String mesa;

    public String cliente;

    @Column(name = "data_criacao")
    public ZonedDateTime dataCriacao;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ItemPedido> itens;

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", total=" + total +
                ", status=" + status +
                ", mesa='" + mesa + '\'' +
                ", cliente='" + cliente + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
