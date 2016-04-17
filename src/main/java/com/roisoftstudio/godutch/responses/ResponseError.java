package com.roisoftstudio.godutch.responses;

import javax.ws.rs.core.Response.Status;

public class ResponseError extends Response {

    public ResponseError() {
        super(Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), Status.INTERNAL_SERVER_ERROR);
    }

    public ResponseError(final String message) {
        super(message, Status.INTERNAL_SERVER_ERROR);
    }

    public ResponseError(final String message, final Status code) {
        super(message, code);
    }
}
