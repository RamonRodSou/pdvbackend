package br.com.technosou.tenant;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tenants")
public class Tenant extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public String nome;

    @Column(nullable = false)
    public String slug;

    public String endereco;

    public boolean ativo = true;

    @Column(name = "data_criacao", updatable = false)
    public ZonedDateTime dataCriacao;

    public static List<Tenant> listAllActive() {
        return list("ativo", true);
    }
}
