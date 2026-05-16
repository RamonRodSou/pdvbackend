package br.com.technosou.tenant.mapper;

import br.com.technosou.tenant.Tenant;
import br.com.technosou.tenant.dto.TenantResponse;

public class TenantMapper {
    static public TenantResponse toResponse(Tenant tenant) {
        return new TenantResponse(
                tenant.id, tenant.nome, tenant.slug,
                tenant.endereco, tenant.ativo, tenant.dataCriacao
        );
    }
}
