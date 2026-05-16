package br.com.technosou.tenant;

import br.com.technosou.tenant.dto.TenantRequest;
import br.com.technosou.tenant.dto.TenantResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static br.com.technosou.tenant.mapper.TenantMapper.toResponse;

@ApplicationScoped
public class TenantService {

    public List<TenantResponse> listarTodos() {
        return Tenant.<Tenant>listAll().stream()
                .map(t -> toResponse(t))
                .collect(Collectors.toList());
    }

    public TenantResponse buscarPorId(UUID id) {
        Tenant tenant = Tenant.<Tenant>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Loja não encontrada."));
        return toResponse(tenant);
    }

    @Transactional
    public TenantResponse criar(TenantRequest request) {
        if (Tenant.count("slug", request.slug()) > 0) {
            throw new IllegalArgumentException("Slug já cadastrado.");
        }

        Tenant tenant = new Tenant();
        tenant.nome = request.nome();
        tenant.slug = request.slug();
        tenant.endereco = request.endereco();
        tenant.ativo = true;
        tenant.dataCriacao = ZonedDateTime.now();

        tenant.persist();
        return toResponse(tenant);
    }

    @Transactional
    public TenantResponse atualizar(UUID id, TenantRequest request) {
        Tenant tenant = Tenant.<Tenant>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Loja não encontrada."));

        tenant.nome = request.nome();
        tenant.slug = request.slug();
        tenant.endereco = request.endereco();
        tenant.ativo = request.ativo();

        return toResponse(tenant);
    }

    @Transactional
    public void inativar(UUID id) {
        Tenant tenant = Tenant.<Tenant>findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Loja não encontrada."));

        tenant.ativo = false;
    }
}