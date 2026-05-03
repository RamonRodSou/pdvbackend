package br.com.technosou.produto;

import br.com.technosou.core.CrudResource;
import br.com.technosou.core.context.TenantContext; // Nosso novo "baú" de ID
import br.com.technosou.produto.dto.ProdutoRequest;
import br.com.technosou.produto.dto.ProdutoResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/v1/api/produtos")
@RequestScoped
public class ProdutoResource implements CrudResource<ProdutoRequest> {

    @Inject
    ProdutoService produtoService;

    @Inject
    TenantContext tenantContext;

    @Override
    public Response listar() {
        List<ProdutoResponse> produtos = produtoService.listarPorTenant(tenantContext.getTenantId());
        return Response.ok(produtos).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        ProdutoResponse produto = produtoService.buscarPorId(id, tenantContext.getTenantId());
        return Response.ok(produto).build();
    }

    @Override
    public Response criar(ProdutoRequest request) {
        ProdutoResponse produtoCriado = produtoService.criarProduto(request, tenantContext.getTenantId());
        return Response.status(Response.Status.CREATED).entity(produtoCriado).build();
    }

    @Override
    public Response atualizar(UUID id, ProdutoRequest request) {
        ProdutoResponse produtoAtualizado = produtoService.atualizarProduto(id, request, tenantContext.getTenantId());
        return Response.ok(produtoAtualizado).build();
    }

    @Override
    public Response deletar(UUID id) {
        produtoService.deletarProduto(id, tenantContext.getTenantId());
        return Response.noContent().build();
    }
}