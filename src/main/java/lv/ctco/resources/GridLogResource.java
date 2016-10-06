package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.adapters.SqliteAdapter;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.views.GridLogsView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/logs")
@Produces(MediaType.TEXT_HTML)
public class GridLogResource {

    private GridControlConfiguration configuration;
    private SqliteAdapter sqliteAdapter;

    public GridLogResource(GridControlConfiguration configuration) {
        sqliteAdapter = new SqliteAdapter();
        this.configuration = configuration;
    }

    @GET
    @Timed
    public GridLogsView showGridDashboard() {
        return new GridLogsView(configuration, sqliteAdapter.getLogs());
    }

    @POST
    @Timed
    @Path("/info")
    public void addInfoLog(@QueryParam("text") String message) {
        sqliteAdapter.addInfoMessage(message);
    }

    @POST
    @Timed
    @Path("/error")
    public void addErrorLog(@QueryParam("text") String message) {
        sqliteAdapter.addInfoMessage(message);
    }
}
