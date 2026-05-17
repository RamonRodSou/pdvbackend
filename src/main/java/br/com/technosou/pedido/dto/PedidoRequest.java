package br.com.technosou.pedido.dto;

import java.util.List;

public record PedidoRequest(
        String mesa,
        String cliente,
        String slug,
        String telefone,
        String descricao,
        List<ItemRequest> itens
) {
}
