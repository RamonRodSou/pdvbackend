package br.com.technosou.tenant;

import br.com.technosou.tenant.dto.TenantPublicoResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/api/public/tenant")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
public class TenantPublicoResource {

    @Inject
    TenantPublicoService tenantPublicoService;

    @GET
    @Path("/{slug}")
    public Response buscarPorId(@PathParam("slug") String slug) {
        TenantPublicoResponse loja = tenantPublicoService.buscarPorSlug(slug);
        return Response.ok(loja).build();
    }
}
