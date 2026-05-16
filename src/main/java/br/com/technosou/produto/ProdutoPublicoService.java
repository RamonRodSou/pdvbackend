package br.com.technosou.produto;

import br.com.technosou.produto.dto.ProdutoPublicoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProdutoPublicoService {

    public List<ProdutoPublicoResponse> listarPorSlug(String slug) {
        return Produto.listBySlug(slug).stream()
                .map(p -> response(p))
                .collect(Collectors.toList());
    }

    public ProdutoPublicoResponse buscarPorId(UUID id, String slug) {
        Produto produto = Produto.findByIdAndSlug(id, slug)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
        return new ProdutoPublicoResponse(slug, produto.produto, produto.preco, produto.foto);
    }

    private ProdutoPublicoResponse response (Produto produto) {
        return new ProdutoPublicoResponse(produto.slug, produto.produto, produto.preco, produto.foto);
    }
}
