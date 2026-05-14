package br.com.technosou.produto.dto;

import java.math.BigDecimal;

public record ProdutoRequest(
        String produto,
        BigDecimal preco,
        String foto,
        boolean ativo
) {
}
