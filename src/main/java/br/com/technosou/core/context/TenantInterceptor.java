package br.com.technosou.core.context;

import br.com.technosou.usuario.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;
import java.util.Optional;
import java.util.UUID;

@Provider
public class TenantInterceptor implements ContainerRequestFilter {

    private static final Logger LOG = Logger.getLogger(TenantContext.class);
    private static final String TENANT_HEADER = "X-Tenant-ID";

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TenantContext tenantContext;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Optional<UUID> tenantId = resolveTenantId(requestContext);
        tenantId.ifPresent(tenantContext::setTenantId);
    }

    private Optional<UUID> resolveTenantId(ContainerRequestContext context) {
        String headerId = context.getHeaderString(TENANT_HEADER);
        if (headerId != null && !headerId.isBlank()) {
            return Optional.of(UUID.fromString(headerId));
        }

        return resolveFromUserSession();
    }

    private Optional<UUID> resolveFromUserSession() {
        return Optional.ofNullable(jwt.getSubject())
                .flatMap(userId -> {
                    try {
                        return Optional.of(usuarioService.buscarTenantPorUsuario(UUID.fromString(userId)));
                    } catch (NotFoundException e) {
                        LOG.debugv("Contexto ignorado: Usuário {0} ainda não cadastrado no banco.", userId);
                        return Optional.empty();
                    }
                });
    }
}