package com.roisoftstudio.godutch.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.core.Response.Status;

@JsonIgnoreProperties(value = {"status", "message"})
public class ResponseToken extends Response {

    @JsonProperty
    private String token;

    public ResponseToken() { super(Status.OK.getReasonPhrase(), Status.OK); }

    public ResponseToken(final String message) {
        super();
        this.token = message;
    }

    public ResponseToken(final String message, final Status code) {
        super(message, code);
    }

    public String getToken() {
        return token;
    }
}
