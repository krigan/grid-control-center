package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.views.GridControlView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class GridControlResource {

    private GridControlConfiguration configuration;

    public GridControlResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    public GridControlView showGridDashboard() {
        return new GridControlView(configuration);
    }

    @GET
    @Timed
    @Path("/register")
    public String registerNode(@QueryParam("host") String host,
                               @QueryParam("port") int port) {
        hub.addNode(new Node(host, port, false));
        return "Node registered";
    }
}
