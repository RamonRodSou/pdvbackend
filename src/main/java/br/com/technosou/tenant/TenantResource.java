package br.com.technosou.tenant;

import br.com.technosou.core.CrudResource;
import br.com.technosou.tenant.dto.TenantRequest;
import br.com.technosou.tenant.dto.TenantResponse;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;
import java.util.UUID;

@Path("/v1/api/admin/tenants")
@RequestScoped
public class TenantResource implements CrudResource<TenantRequest> {

    @Inject
    TenantService tenantService;

    @ConfigProperty(name = "technosou.admin.token")
    String adminToken;

    @HeaderParam("X-Admin-Token")
    String tokenFornecido;

    @Override
    public Response listar() {
        validarAcessoAdmin();
        List<TenantResponse> lojas = tenantService.listarTodos();
        return Response.ok(lojas).build();
    }

    @Override
    public Response buscarPorId(UUID id) {
        validarAcessoAdmin();
        TenantResponse loja = tenantService.buscarPorId(id);
        return Response.ok(loja).build();
    }

    @Override
    public Response criar(TenantRequest request) {
        validarAcessoAdmin();
        TenantResponse criada = tenantService.criar(request);
        return Response.status(Response.Status.CREATED).entity(criada).build();
    }

    @Override
    public Response atualizar(UUID id, TenantRequest request) {
        validarAcessoAdmin();
        TenantResponse atualizada = tenantService.atualizar(id, request);
        return Response.ok(atualizada).build();
    }

    @Override
    public Response deletar(UUID id) {
        validarAcessoAdmin();
        tenantService.inativar(id);
        return Response.noContent().build();
    }

    private void validarAcessoAdmin() {
        if (tokenFornecido == null || tokenFornecido.isBlank()) {
            throw new WebApplicationException("Token administrativo ausente.", Response.Status.UNAUTHORIZED);
        }

        if (!adminToken.equals(tokenFornecido)) {
            throw new WebApplicationException("Acesso negado: Token administrativo inválido.", Response.Status.FORBIDDEN);
        }
    }
}