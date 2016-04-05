package com.roisoftstudio.godutch.info.paths;

import com.google.inject.Inject;
import com.roisoftstudio.godutch.info.services.InfoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class InfoPath {

    @Inject
    private InfoService infoService;

    @GET
    public Response getInfo() {
        return Response.ok(infoService.getInfo()).build();

    }
}
