package br.com.technosou.usuario.dto;

import br.com.technosou.usuario.Role;

import java.time.ZonedDateTime;
import java.util.UUID;

public record UsuarioResponse(
        UUID id,
        String nome,
        String email,
        Role role,
        boolean ativo,
        ZonedDateTime dataCriacao
) {
}
