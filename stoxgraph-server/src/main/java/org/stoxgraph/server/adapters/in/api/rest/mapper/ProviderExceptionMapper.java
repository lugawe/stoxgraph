package org.stoxgraph.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.stoxgraph.core.exception.ProviderException;

@Provider
@ApplicationScoped
public class ProviderExceptionMapper implements ExceptionMapper<ProviderException> {

    @Inject
    public ProviderExceptionMapper() {}

    @Override
    public Response toResponse(ProviderException exception) {
        return Response.status(502).build();
    }
}
