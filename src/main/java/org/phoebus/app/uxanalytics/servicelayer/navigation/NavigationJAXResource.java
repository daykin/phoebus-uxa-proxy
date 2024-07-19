package org.phoebus.app.uxanalytics.servicelayer.navigation;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/navigation")
public class NavigationJAXResource {

    @Inject
    GraphDatabase graphDatabase;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recordNavigation(NavigationBean navigationBean){
        graphDatabase.recordNavigation(navigationBean);
        return Response.status(Response.Status.CREATED).build();
    }

}
