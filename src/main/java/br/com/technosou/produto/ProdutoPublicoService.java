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
        return response(produto);
    }

    private ProdutoPublicoResponse response (Produto produto) {
        return new ProdutoPublicoResponse(
                produto.id,
                produto.slug,
                produto.produto,
                produto.preco,
                produto.foto,
                produto.categoria,
                produto.descricao,
                produto.ativo
        );
    }
}
