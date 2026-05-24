package br.com.technosou.usuario;

import br.com.technosou.core.CrudResource;
import br.com.technosou.core.context.TenantContext;
import br.com.technosou.usuario.dto.UsuarioProfileTenantDTO;
import br.com.technosou.usuario.dto.UsuarioRequest;
import br.com.technosou.usuario.dto.UsuarioResponse;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/v1/api/usuarios")
@RequestScoped
@Authenticated
public class UsuarioResource implements CrudResource<UsuarioRequest> {

    @Inject
    UsuarioService usuarioService;

    @Inject
    JsonWebToken jwt;

    @Inject
    TenantContext tenantContext;

    @Override
    public Response listar() {
        List<UsuarioResponse> usuarios = usuarioService.listarPorTenant(tenantContext.getTenantId());
        return Response.ok(usuarios).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        UsuarioResponse usuario = usuarioService.buscarPorId(id, tenantContext.getTenantId());
        return Response.ok(usuario).build();
    }

    @Override
    public Response criar(UsuarioRequest request) {
        UsuarioResponse usuarioCriado = usuarioService.criarUsuario(request, tenantContext.getTenantId());
        return Response.status(Response.Status.CREATED).entity(usuarioCriado).build();
    }

    @Override
    public Response atualizar(UUID id, UsuarioRequest request) {
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarUsuario(id, request, tenantContext.getTenantId());
        return Response.ok(usuarioAtualizado).build();
    }

    @Override
    public Response deletar(UUID id) {
        usuarioService.inativarUsuario(id, tenantContext.getTenantId());
        return Response.noContent().build();
    }

    @GET
    @Path("/me")
    @Authenticated
    public Response obterMeuPerfil() {
        String id = jwt.getSubject();
        UsuarioProfileTenantDTO profile = usuarioService.obterPerfilUsuarioPorTenant(id);
        return Response.ok(profile).build();
    }
}