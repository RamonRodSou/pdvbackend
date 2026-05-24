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
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PedidoService {

    public List<Pedido> listarPorTenant(UUID tenantId) {
        return Pedido.find("tenantId = ?1 order by dataCriacao desc", tenantId).list();
    }

    @Transactional
    public Pedido criarPedido(PedidoRequest request, UUID tenantId) {

        Pedido pedido = new Pedido();
        pedido.tenantId = tenantId;

        if (request.slug() != null && !request.slug().isBlank()) {
            pedido.slug = request.slug();
        } else {
            Tenant tenant = Tenant.findById(tenantId);
            pedido.slug = tenant != null ? tenant.slug : null;
        }

        pedido.tenantId = tenantId;
        pedido.slug = request.slug();
        pedido.descricao = request.descricao();
        pedido.telefone = request.telefone();
        pedido.mesa = request.mesa();
        pedido.cliente = request.cliente();
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

    @Transactional
    public Pedido atualizarPedido(UUID id, PedidoRequest request, UUID tenantId) {
        Pedido pedido = buscarPorId(id, tenantId);

        pedido.mesa = request.mesa();
        pedido.cliente = request.cliente();

        return pedido;
    }

    @Transactional
    public Pedido atualizarStatus(UUID id, StatusPedido novoStatus, UUID tenantId) {
        Pedido pedido = buscarPorId(id, tenantId);
        pedido.status = novoStatus;
        return pedido;
    }

    @Transactional
    public void deletarPedido(UUID id, UUID tenantId) {
        Pedido pedido = buscarPorId(id, tenantId);
        pedido.delete();
    }

    public Pedido buscarPorId(UUID id, UUID tenantId) {
        return (Pedido) Pedido.find("id = ?1 and tenantId = ?2", id, tenantId)
                .firstResultOptional()
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado nesta loja."));
    }
}