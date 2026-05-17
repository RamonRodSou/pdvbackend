package br.com.technosou.tenant.dto;

public record TenantPublicoResponse(
        String nome,
        String slug,
        String endereco,
        String logo,
        String corPrincipal,
        String corSecundaria
) {
}
