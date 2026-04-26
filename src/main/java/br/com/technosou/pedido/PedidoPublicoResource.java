package br.com.technosou.pedido;

import br.com.technosou.pedido.dto.PedidoRequest;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/v1/api/public/lojas/{tenantId}/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoPublicoResource {

    @Inject
    PedidoService pedidoService;

    @POST
    @PermitAll
    public Response criarPedidoCliente(
            @PathParam("tenantId") UUID tenantId,
            PedidoRequest request) {

        Pedido novo = pedidoService.criarPedido(request, tenantId);

        return Response.status(Response.Status.CREATED).entity(novo).build();
    }
}