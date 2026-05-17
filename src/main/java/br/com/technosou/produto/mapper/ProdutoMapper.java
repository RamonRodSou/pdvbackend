package br.com.technosou.produto.mapper;

import br.com.technosou.produto.Produto;
import br.com.technosou.produto.dto.ProdutoResponse;

public class ProdutoMapper {

    static public ProdutoResponse toResponse(Produto produto) {
        return new ProdutoResponse(
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
