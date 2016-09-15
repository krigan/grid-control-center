package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.views.GridControlView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
