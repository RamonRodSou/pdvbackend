package br.com.technosou.usuario;

import br.com.technosou.core.CrudResource;
import br.com.technosou.usuario.dto.UsuarioRequest;
import br.com.technosou.usuario.dto.UsuarioResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/v1/api/usuarios")
@RequestScoped
public class UsuarioResource implements CrudResource<UsuarioRequest> {

    @Inject
    UsuarioService usuarioService;

    @HeaderParam("X-Tenant-ID")
    UUID tenantId;

    @Override
    public Response listar() {
        validarTenant();
        List<UsuarioResponse> usuarios = usuarioService.listarPorTenant(tenantId);
        return Response.ok(usuarios).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        validarTenant();
        UsuarioResponse usuario = usuarioService.buscarPorId(id, tenantId);
        return Response.ok(usuario).build();
    }

    @Override
    public Response criar(UsuarioRequest request) {
        validarTenant();
        UsuarioResponse usuarioCriado = usuarioService.criarUsuario(request, tenantId);
        return Response.status(Response.Status.CREATED).entity(usuarioCriado).build();
    }

    @Override
    public Response atualizar(UUID id, UsuarioRequest request) {
        validarTenant();
        UsuarioResponse usuarioAtualizado = usuarioService.atualizarUsuario(id, request, tenantId);
        return Response.ok(usuarioAtualizado).build();
    }

    @Override
    public Response deletar(UUID id) {
        validarTenant();
        usuarioService.inativarUsuario(id, tenantId);
        return Response.noContent().build();
    }

    private void validarTenant() {
        if (tenantId == null) {
            throw new BadRequestException("O cabeçalho X-Tenant-ID é obrigatório");
        }
    }
}