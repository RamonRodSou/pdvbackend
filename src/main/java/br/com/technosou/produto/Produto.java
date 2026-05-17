package br.com.technosou.produto;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "produtos")
public class Produto extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(name = "tenant_id", nullable = false)
    public UUID tenantId;

    @Column(name = "slug", nullable = false)
    public String slug;

    @Column(nullable = false)
    public String produto;

    @Column(nullable = false)
    public BigDecimal preco;

    public String foto;

    @Column(nullable = false)
    public Categoria categoria;

    @Column(length = 255)
    public String descricao;

    public boolean ativo = true;

    @Column(name = "data_criacao", updatable = false)
    public ZonedDateTime dataCriacao;

    public static List<Produto> listByTenant(UUID tenantId) {
        return find("tenantId", tenantId).list();
    }

    public static List<Produto> listBySlug(String slug) {
        return find("slug", slug).list();
    }

    public static Optional<Produto> findByIdAndTenant(UUID id, UUID tenantId) {
        return find("id = ?1 and tenantId = ?2", id, tenantId).firstResultOptional();
    }

    public static Optional<Produto> findByIdAndSlug(UUID id, String slug) {
        return find("id = ?1 and slug = ?2", id, slug).firstResultOptional();
    }

    public static boolean deleteByIdAndTenant(UUID id, UUID tenantId) {
        return delete("id = ?1 and tenantId = ?2", id, tenantId) > 0;
    }
}
