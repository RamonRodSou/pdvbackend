package br.com.technosou.produto;

import br.com.technosou.produto.dto.ProdutoPublicoResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/v1/api/public/produtos")
@RequestScoped
public class ProdutoPublicoResource {

    @Inject
    ProdutoPublicoService produtoService;

    @GET
    @Path("/{slug}")
    public Response listar(@PathParam("slug") String slug) {
        List<ProdutoPublicoResponse> produtos = produtoService.listarPorSlug(slug);
        return Response.ok(produtos).build();
    }
}