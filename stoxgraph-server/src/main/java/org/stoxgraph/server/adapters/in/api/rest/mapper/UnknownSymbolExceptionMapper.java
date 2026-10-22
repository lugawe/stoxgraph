package org.stoxgraph.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.stoxgraph.core.exception.UnknownSymbolException;

@Provider
@ApplicationScoped
public class UnknownSymbolExceptionMapper implements ExceptionMapper<UnknownSymbolException> {

    @Inject
    public UnknownSymbolExceptionMapper() {}

    @Override
    public Response toResponse(UnknownSymbolException exception) {
        return Response.status(404).build();
    }
}
