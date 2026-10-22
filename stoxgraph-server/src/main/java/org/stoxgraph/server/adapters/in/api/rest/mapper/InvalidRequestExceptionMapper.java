package org.stoxgraph.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.stoxgraph.core.exception.InvalidRequestException;

@Provider
@ApplicationScoped
public class InvalidRequestExceptionMapper implements ExceptionMapper<InvalidRequestException> {

    @Inject
    public InvalidRequestExceptionMapper() {}

    @Override
    public Response toResponse(InvalidRequestException exception) {
        return Response.status(400).build();
    }
}
