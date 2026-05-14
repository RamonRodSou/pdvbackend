package br.com.technosou.tenant.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

public record TenantResponse(
        UUID id,
        String nome,
        String slug,
        String endereco,
        boolean ativo,
        ZonedDateTime dataCriacao
) {
}
