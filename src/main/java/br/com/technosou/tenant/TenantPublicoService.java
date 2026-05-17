package br.com.technosou.tenant;

import br.com.technosou.tenant.dto.TenantPublicoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TenantPublicoService {

    public TenantPublicoResponse buscarPorSlug(String slug) {
        Tenant tenant = Tenant.<Tenant>find("slug = ?1 and ativo = ?2", slug, true)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Loja não encontrada ou inativa."));
        return response(tenant);
    }

    private TenantPublicoResponse response (Tenant tenant) {
        return new TenantPublicoResponse(
                tenant.nome,
                tenant.slug,
                tenant.endereco,
                tenant.logo,
                tenant.corPrincipal,
                tenant.corSecundaria
        );
    }

}