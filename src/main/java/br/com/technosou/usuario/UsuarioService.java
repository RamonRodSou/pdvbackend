package br.com.technosou.usuario;

import br.com.technosou.tenant.Tenant;
import br.com.technosou.usuario.dto.UsuarioProfileTenantDTO;
import br.com.technosou.usuario.dto.UsuarioRequest;
import br.com.technosou.usuario.dto.UsuarioResponse;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
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
        System.out.println("---------------------------------------------------");
        System.out.println("teste");

        Usuario usuario = new Usuario();
        usuario.id = request.id();
        usuario.tenantId = tenantId;
        usuario.nome = request.nome();
        usuario.email = request.email();
        usuario.role = request.role();
        usuario.ativo = request.ativo();
        usuario.dataCriacao = ZonedDateTime.now();

        System.out.println("---------------------------------------------------");
        System.out.println(usuario);

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

    public UUID buscarTenantPorUsuario(UUID usuarioId) {
        Usuario usuario = Usuario.findById(usuarioId);
        if (usuario == null) {
            throw new NotFoundException("Usuário " + usuarioId + " não encontrado na tabela de permissões (public.usuarios).");
        }
        return usuario.tenantId;
    }

    public UsuarioProfileTenantDTO obterPerfilUsuarioPorTenant(String supabaseId) {
        if (supabaseId == null) {
            throw new NotAuthorizedException("Token inválido.");
        }

        Usuario usuarioLogado = Usuario.findById(UUID.fromString(supabaseId));

        if (usuarioLogado == null || !usuarioLogado.ativo) {
            throw new ForbiddenException("Usuário inativo ou não encontrado.");
        }

        Tenant tenant = Tenant.findById(usuarioLogado.tenantId);
        if (tenant == null) {
            throw new NotFoundException("Tenant não encontrado.");
        }

        return new UsuarioProfileTenantDTO(tenant.id, tenant.slug, tenant.nome);
    }
}