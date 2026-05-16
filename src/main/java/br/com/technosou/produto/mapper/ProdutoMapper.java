package br.com.technosou.produto.mapper;

import br.com.technosou.produto.Produto;
import br.com.technosou.produto.dto.ProdutoResponse;

public class ProdutoMapper {

    static public ProdutoResponse toResponse(Produto produto) {
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
