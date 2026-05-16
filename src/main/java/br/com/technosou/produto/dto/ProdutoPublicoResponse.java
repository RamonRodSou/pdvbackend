package br.com.technosou.produto.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ProdutoPublicoResponse(
        String slug,
        String produto,
        BigDecimal preco,
        String foto
) {
}
