package br.com.technosou.produto.dto;

import br.com.technosou.produto.Categoria;

import java.math.BigDecimal;
import java.util.UUID;

public record ProdutoPublicoResponse(
        UUID id,
        String slug,
        String produto,
        BigDecimal preco,
        String foto,
        Categoria categoria,
        String descricao,
        Boolean ativo
) {
}
