package br.com.technosou.usuario;

import br.com.technosou.usuario.dto.UsuarioRequest;
import br.com.technosou.usuario.dto.UsuarioResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class UsuarioService {

    public List<UsuarioResponse> listarPorTenant(UUID tenantId) {
        return Usuario.listByTenant(tenantId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponse buscarPorId(UUID id, UUID tenantId) {
        Usuario usuario = Usuario.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado para esta loja."));
        return mapToResponse(usuario);
    }

    @Transactional
    public UsuarioResponse criarUsuario(UsuarioRequest request, UUID tenantId) {
        Usuario usuario = new Usuario();
        usuario.id = request.id();
        usuario.tenantId = tenantId;
        usuario.nome = request.nome();
        usuario.email = request.email();
        usuario.role = request.role(); // Tipagem forte entrando em ação
        usuario.ativo = request.ativo();
        usuario.dataCriacao = ZonedDateTime.now();

        usuario.persist();
        return mapToResponse(usuario);
    }

    @Transactional
    public UsuarioResponse atualizarUsuario(UUID id, UsuarioRequest request, UUID tenantId) {
        Usuario usuario = Usuario.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado para esta loja."));

        usuario.nome = request.nome();
        usuario.role = request.role();
        usuario.ativo = request.ativo();

        return mapToResponse(usuario);
    }

    @Transactional
    public void inativarUsuario(UUID id, UUID tenantId) {
        Usuario usuario = Usuario.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado para esta loja."));

        usuario.ativo = false;
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.id, usuario.nome, usuario.email,
                usuario.role, usuario.ativo, usuario.dataCriacao
        );
    }
}