package br.com.technosou.produto;

import br.com.technosou.produto.dto.ProdutoRequest;
import br.com.technosou.produto.dto.ProdutoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoService {

    public List<ProdutoResponse> listarPorTenant(UUID tenantId) {
        return Produto.listByTenant(tenantId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProdutoResponse buscarPorId(UUID id, UUID tenantId) {
        Produto produto = Produto.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        return mapToResponse(produto);
    }

    @Transactional
    public ProdutoResponse criarProduto(ProdutoRequest request, UUID tenantId) {
        Produto produto = new Produto();
        produto.tenantId = tenantId;
        produto.produto = request.produto();
        produto.preco = request.preco();
        produto.foto = request.foto();
        produto.ativo = request.ativo();
        produto.dataCriacao = ZonedDateTime.now();
        produto.persist();
        return mapToResponse(produto);
    }

    @Transactional
    public ProdutoResponse atualizarProduto(UUID id, ProdutoRequest request, UUID tenantId) {
        Produto produto = Produto.findByIdAndTenant(id, tenantId)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        produto.produto = request.produto();
        produto.preco = request.preco();
        produto.foto = request.foto();
        produto.ativo = request.ativo();
        return mapToResponse(produto);
    }

    @Transactional
    public void deletarProduto(UUID id, UUID tenantId) {
        boolean deletado = Produto.deleteByIdAndTenant(id, tenantId);
        if (!deletado) {
            throw new NotFoundException("Produto não encontrado ou já foi removido.");
        }
    }

    private ProdutoResponse mapToResponse(Produto produto) {
        return new ProdutoResponse(
                produto.id,
                produto.produto,
                produto.preco,
                produto.foto,
                produto.ativo,
                produto.dataCriacao
        );
    }
}
