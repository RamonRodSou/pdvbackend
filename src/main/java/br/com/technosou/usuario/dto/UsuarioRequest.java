package br.com.technosou.usuario.dto;

import br.com.technosou.usuario.Role;

import java.util.UUID;

public record UsuarioRequest(
        UUID id,
        String nome,
        String email,
        Role role,
        boolean ativo
) {
}
