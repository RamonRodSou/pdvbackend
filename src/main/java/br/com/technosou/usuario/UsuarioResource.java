package br.com.technosou.usuario;

import br.com.technosou.core.CrudResource;
import br.com.technosou.usuario.dto.UsuarioRequest;
import br.com.technosou.usuario.dto.UsuarioResponse;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.UUID;

@Path("/v1/api/usuarios")
@RequestScoped
@Authenticated
public class UsuarioResource implements CrudResource<UsuarioRequest> {

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    private UUID getTenantIdLogado() {
        String supabaseId = jwt.getSubject();

        if (supabaseId == null) {
            throw new NotAuthorizedException("Token inválido ou sem identificação do usuário.");
        }

        Usuario usuarioLogado = Usuario.findById(UUID.fromString(supabaseId));

        if (usuarioLogado == null || !usuarioLogado.ativo) {
            throw new ForbiddenException("Usuário não encontrado ou inativo no sistema.");
        }

        return usuarioLogado.tenantId;
    }

    @Override
    public Response listar() {
        UUID tenantId = getTenantIdLogado();
        List<UsuarioResponse> usuarios = usuarioService.listarPorTenant(tenantId);
        return Response.ok(usuarios).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        UUID tenantId = getTenantIdLogado();
        UsuarioResponse usuario = usuarioService.buscarPorId(id, tenantId);
        return Response.ok(usuario).build();
    }

    @Override
    public Response criar(UsuarioRequest request) {
        UUID tenantId = getTenantIdLogado();

        UsuarioResponse usuarioCriado = usuarioService.criarUsuario(request, tenantId);
        return Response.status(Response.Status.CREATED).entity(usuarioCriado).build();
    }

    @Override
    public Response atualizar(UUID id, UsuarioRequest request) {
        UUID tenantId = getTenantIdLogado();
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarUsuario(id, request, tenantId);
        return Response.ok(usuarioAtualizado).build();
    }

    @Override
    public Response deletar(UUID id) {
        UUID tenantId = getTenantIdLogado();
        usuarioService.inativarUsuario(id, tenantId);
        return Response.noContent().build();
    }
}