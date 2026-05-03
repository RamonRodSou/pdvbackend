package br.com.technosou.usuario;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
public class Usuario extends PanacheEntityBase {
    @Id
    public UUID id;

    @Column(name = "tenant_id", nullable = false)
    public UUID tenantId;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Role role;

    public boolean ativo = true;

    @Column(name = "data_criacao", updatable = false)
    public ZonedDateTime dataCriacao;

    public static List<Usuario> listByTenant(UUID tenantId) {
        return find("tenantId", tenantId).list();
    }

    public static Optional<Usuario> findByIdAndTenant(UUID id, UUID tenantId) {
        return find("id = ?1 and tenantId = ?2", id, tenantId).firstResultOptional();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", ativo=" + ativo +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
