package org.phoebus.app.uxanalytics.servicelayer.clicktracking;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/click")
public class ClickJAXResource {
    
    @Inject
    ClickTrackingDatabase clickTrackingDatabase;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response recordClick(ClickBean clickBean){
        clickTrackingDatabase.recordClick(clickBean);
        return Response.status(Response.Status.CREATED).build();
    }
    
}
