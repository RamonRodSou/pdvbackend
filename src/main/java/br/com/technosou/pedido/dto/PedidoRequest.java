package br.com.technosou.pedido.dto;

import java.util.List;

public record PedidoRequest(
        String mesa,
        String cliente,
        List<ItemRequest> itens
) {
}
