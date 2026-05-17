package br.com.technosou.pedido;

import br.com.technosou.core.context.TenantContext;
import br.com.technosou.pedido.dto.PedidoRequest;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/api/public/pedido")
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class PedidoPublicoResource {

    @Inject
    PedidoPublicoService pedidoService;

    @Inject
    TenantContext tenantContext;

    @POST
    @Path("/{slug}")
    @Transactional
    public Response criar(@PathParam("slug") String slug, PedidoRequest request) {
        Pedido pedido = pedidoService.criarPedido(request, slug);
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }
}