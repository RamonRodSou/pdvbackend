package br.com.technosou.produto;

import br.com.technosou.core.CrudResource;
import br.com.technosou.produto.dto.ProdutoRequest;
import br.com.technosou.produto.dto.ProdutoResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/v1/api/produtos")
@RequestScoped
public class ProdutoResource implements CrudResource<ProdutoRequest> {

    @Inject
    ProdutoService produtoService;

    @HeaderParam("X-Tenant-ID")
    UUID tenantId;

    @Override
    public Response listar() {
        validarTenant();
        List<ProdutoResponse> produtos = produtoService.listarPorTenant(tenantId);
        return Response.ok(produtos).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        validarTenant();
        ProdutoResponse produto = produtoService.buscarPorId(id, tenantId);
        return Response.ok(produto).build();
    }

    @Override
    public Response criar(ProdutoRequest request) {
        validarTenant();
        ProdutoResponse produtoCriado = produtoService.criarProduto(request, tenantId);
        return Response.status(Response.Status.CREATED).entity(produtoCriado).build();
    }

    @Override
    public Response atualizar(UUID id, ProdutoRequest request) {
        validarTenant();
        ProdutoResponse produtoAtualizado = produtoService.atualizarProduto(id, request, tenantId);
        return Response.ok(produtoAtualizado).build();
    }

    @Override
    public Response deletar(UUID id) {
        validarTenant();
        produtoService.deletarProduto(id, tenantId);
        return Response.noContent().build();
    }

    private void validarTenant() {
        if (tenantId == null) {
            throw new BadRequestException("O cabeçalho X-Tenant-ID é obrigatório");
        }
    }
}