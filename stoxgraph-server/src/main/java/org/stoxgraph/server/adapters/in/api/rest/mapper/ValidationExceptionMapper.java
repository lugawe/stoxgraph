package org.stoxgraph.server.adapters.in.api.rest.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import org.stoxgraph.core.exception.ValidationException;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Inject
    public ValidationExceptionMapper() {}

    @Override
    public Response toResponse(ValidationException exception) {
        return Response.status(400).build();
    }
}
