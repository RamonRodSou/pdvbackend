package br.com.technosou.produto.dto;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

public record ProdutoResponse(
        UUID id,
        String produto,
        BigDecimal preco,
        String foto,
        boolean ativo,
        ZonedDateTime dataCriacao
) {
}
