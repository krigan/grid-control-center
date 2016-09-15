package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.configuration.GridControlConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/node")
@Produces(MediaType.TEXT_HTML)
public class GridNodeResource {

    private GridControlConfiguration configuration;

    public GridNodeResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    public String startNode() {

        return "Node started";
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopNode() {
        // TODO implement me here
        return "Node stopped";
    }
}
