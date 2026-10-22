package org.stoxgraph.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.stoxgraph.core.exception.MarketDataException;

@Provider
@ApplicationScoped
public class MarketDataExceptionMapper implements ExceptionMapper<MarketDataException> {

    @Inject
    public MarketDataExceptionMapper() {}

    @Override
    public Response toResponse(MarketDataException exception) {
        return Response.status(502).build();
    }
}
