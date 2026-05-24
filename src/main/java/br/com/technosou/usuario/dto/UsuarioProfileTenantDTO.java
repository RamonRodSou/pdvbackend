package br.com.technosou.usuario.dto;

import java.util.UUID;

public record UsuarioProfileTenantDTO(UUID tenantId, String slug, String nome) {
}
