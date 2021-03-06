package com.roisoftstudio.godutch.responses;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response.Status;

public abstract class Response {

    @JsonProperty
    protected Status status;

    @JsonProperty
    protected String message;

    public Response () {
        this.status = Status.NO_CONTENT;
        this.message = Status.NO_CONTENT.getReasonPhrase();
    }

    public Response(final String message, final Status code) {
        this.status = code;
        this.message = message;
    }

}
