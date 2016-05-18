package com.roisoftstudio.godutch.responses;

import javax.ws.rs.core.Response.Status;

public class ResponseSucceed extends Response {

    public ResponseSucceed() {
        super(Status.OK.getReasonPhrase(), Status.OK);
    }

    public ResponseSucceed(final String message) {
        super(message, Status.OK);
    }

    public ResponseSucceed(final String message, final Status code) {
        super(message, code);
    }
}
