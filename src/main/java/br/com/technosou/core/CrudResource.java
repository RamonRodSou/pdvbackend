package br.com.technosou.core;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.UUID;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CrudResource<REQ> {

    @GET
    Response listar();

    @GET
    @Path("/{id}")
    Response buscarPorId(@PathParam("id") UUID id);

    @POST
    Response criar(REQ request);

    @PUT
    @Path("/{id}")
    Response atualizar(@PathParam("id") UUID id, REQ request);

    @DELETE
    @Path("/{id}")
    Response deletar(@PathParam("id") UUID id);
}