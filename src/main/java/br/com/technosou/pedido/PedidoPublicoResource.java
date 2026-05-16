package br.com.technosou.pedido;

import br.com.technosou.core.context.TenantContext;
import br.com.technosou.pedido.dto.PedidoRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/api/public/lojas/{slug}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class PedidoPublicoResource {

    @Inject
    PedidoPublicoService pedidoService;

    @Inject
    TenantContext tenantContext;

    public Response criar(PedidoRequest request) {
        Pedido pedido = pedidoService.criarPedido(request, tenantContext.getTenantSlug());
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }
}