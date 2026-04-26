package br.com.technosou.pedido;

import br.com.technosou.core.CrudResource;
import br.com.technosou.pedido.dto.PedidoRequest;
import br.com.technosou.usuario.Usuario;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.UUID;

@Path("/v1/api/pedido")
@RequestScoped
@Authenticated
public class PedidoResource implements CrudResource<PedidoRequest> {

    @Inject
    PedidoService pedidoService;

    @Inject
    JsonWebToken jwt;

    @Override
    public Response listar() {
        return Response.ok(pedidoService.listarPorTenant(getTenantIdLogado())).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        Pedido pedido = pedidoService.buscarPorId(id, getTenantIdLogado());
        return Response.ok(pedido).build();
    }

    @Override
    public Response criar(PedidoRequest request) {
        Pedido pedido = pedidoService.criarPedido(request, getTenantIdLogado());
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }

    @Override
    public Response atualizar(UUID id, PedidoRequest request) {
        Pedido pedido = pedidoService.atualizarPedido(id, request, getTenantIdLogado());
        return Response.ok(pedido).build();
    }

    @Override
    public Response deletar(UUID id) {
        pedidoService.deletarPedido(id, getTenantIdLogado());
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}/status")
    public Response mudarStatus(@PathParam("id") UUID id, @QueryParam("novoStatus") StatusPedido novoStatus) {
        Pedido pedido = pedidoService.atualizarStatus(id, novoStatus, getTenantIdLogado());
        return Response.ok(pedido).build();
    }

    private UUID getTenantIdLogado() {
        String supabaseId = jwt.getSubject();
        Usuario usuario = Usuario.findById(UUID.fromString(supabaseId));

        if(usuario == null || !usuario.ativo) {
            throw new NotAuthorizedException("Usuário não vinculado ou inativo.");
        }

        return usuario.tenantId;
    }
}
