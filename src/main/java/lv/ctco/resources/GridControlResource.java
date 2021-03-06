package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.adapters.SqliteAdapter;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.views.GridControlView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class GridControlResource {

    private GridControlConfiguration configuration;
    private SqliteAdapter sqliteAdapter;

    public GridControlResource(GridControlConfiguration configuration) {
        sqliteAdapter = new SqliteAdapter();
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
        Node node = new Node(host, port, false);
        hub.addNode(node);
        sqliteAdapter.addNode(node);
        return "Node registered";
    }

    @GET
    @Path("/healthCheck")
    public boolean pingHost(@QueryParam("host") String host,
                           @QueryParam("port") int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
