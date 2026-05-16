package br.com.technosou.core.context;

import jakarta.enterprise.context.RequestScoped;

import java.util.UUID;

@RequestScoped
public class TenantContext {
    private UUID tenantId;
    private String tenantSlug;

    public UUID getTenantId() {
        return tenantId;
    }

    public String getTenantSlug() {
        return tenantSlug;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
