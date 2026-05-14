package br.com.technosou.tenant.dto;

public record TenantRequest(
        String nome,
        String slug,
        String endereco,
        boolean ativo
) {
}
