package br.com.technosou.pedido.dto;

import java.util.UUID;

public record ItemRequest(
        UUID produtoId,
        Integer quantidade
) {
}
