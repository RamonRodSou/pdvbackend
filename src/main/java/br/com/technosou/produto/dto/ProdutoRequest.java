package br.com.technosou.produto.dto;

import br.com.technosou.produto.Categoria;

import java.math.BigDecimal;

public record ProdutoRequest(
        String slug,
        String produto,
        BigDecimal preco,
        String foto,
        Categoria categoria,
        String descricao,
        boolean ativo
) {
}
