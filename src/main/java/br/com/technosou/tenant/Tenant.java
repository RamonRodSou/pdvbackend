package br.com.technosou.tenant;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "tenants")
public class Tenant extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(nullable = false)
    String nome;

    @Column(nullable = false)
    String slug;

    public String endereco;

    public boolean ativo = true;

    @Column(name = "data_criacao", updatable = false)
    public ZonedDateTime dataCriacao;

    public static java.util.List<Tenant> listAllActive() {
        return list("ativo", true);
    }
}
