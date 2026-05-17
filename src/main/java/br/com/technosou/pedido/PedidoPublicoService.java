package br.com.technosou.pedido;

import br.com.technosou.pedido.dto.ItemRequest;
import br.com.technosou.pedido.dto.PedidoRequest;
import br.com.technosou.produto.Produto;
import br.com.technosou.tenant.Tenant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;

@ApplicationScoped
public class PedidoPublicoService {

    @Transactional
    public Pedido criarPedido(PedidoRequest request, String slug) {
        UUID tenantId = buscarPorTenantId(slug);
        Pedido pedido = new Pedido();
        pedido.slug = slug;
        pedido.tenantId = tenantId;
        pedido.mesa = request.mesa();
        pedido.cliente = request.cliente();
        pedido.telefone = request.telefone();
        pedido.descricao = request.cliente();
        pedido.status = StatusPedido.PENDENTE;
        pedido.dataCriacao = ZonedDateTime.now();
        pedido.itens = new ArrayList<>();

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (ItemRequest itemReq : request.itens()) {
            Produto produto = Produto.findById(itemReq.produtoId());
            if (produto == null) throw new NotFoundException("Produto não encontrado: " + itemReq.produtoId());

            ItemPedido item = new ItemPedido();
            item.pedido = pedido;
            item.produtoId = produto.id;
            item.quantidade = itemReq.quantidade();
            item.precoUnitario = produto.preco;
            item.subtotal = produto.preco.multiply(BigDecimal.valueOf(itemReq.quantidade()));

            totalPedido = totalPedido.add(item.subtotal);
            pedido.itens.add(item);
        }

        pedido.total = totalPedido;
        pedido.persist();
        return pedido;
    }

    private UUID buscarPorTenantId(String slug) {
        Tenant tenant = Tenant.find("slug = ?1 and ativo = ?2", slug, true).firstResult();

        if (tenant == null) {
            throw new NotFoundException("Tenant não encontrado: " + slug);
        }

        return tenant.id;
    }
}
