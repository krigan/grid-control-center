package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.adapters.SqliteAdapter;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;
import lv.ctco.helpers.ProcessHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/hub")
@Produces(MediaType.TEXT_HTML)
public class GridHubResource {

    private static Process process;
    private GridControlConfiguration configuration;
    private String startCommand;
    private SqliteAdapter sqliteAdapter;

    public GridHubResource(GridControlConfiguration configuration) {
        sqliteAdapter = new SqliteAdapter();
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    public String startHub(@QueryParam("params") String params) {
        if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
            return "No selenium jar found";
        } else {
            if (params == null) {
                params = "";
            }
            startCommand = params;
            try {
                process = Runtime.getRuntime().exec(startCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (ProcessHelper.isProcessRunning(process)) {
                hub.setStartCommand(startCommand);
                hub.setRunning(true);
                sqliteAdapter.updateHub(hub);
                sqliteAdapter.addInfoMessage("Hub started with params: " + hub.getStartCommand());
            } else {
                sqliteAdapter.addErrorMessage("Unable to start hub with params: " + hub.getStartCommand());
                throw new IllegalStateException("Unable to start hub");
            }
            return "Hub started with start command " + startCommand;
        }
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopHub() {
        if (process != null) {
            process.destroy();
            if (!ProcessHelper.isProcessRunning(process)) {
                hub.setRunning(false);
                sqliteAdapter.updateHub(hub);
                sqliteAdapter.addInfoMessage("Hub stopped");
            } else {
                sqliteAdapter.addErrorMessage("Unable to stop hub");
                throw new IllegalStateException("Unable to stop hub");
            }
        }
        return "Hub stopped";
    }

    @GET
    @Timed
    @Path("/status")
    public String statusHub() {
        if (process != null) {
            if (process.isAlive()) {
                return new Gson().toJson(hub);
            } else {
                return "Hub process was stopped, please start selenium node";
            }
        }
        return "Hub wasn't started yet";
    }

    @GET
    @Timed
    @Path("/startNode")
    public String startNode(@QueryParam("host") String host, @QueryParam("port") int port, @QueryParam("params") String params) {
        Node node = new Node(host, port, false);
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://" + host + ":" + port + "/node/start");
        target
                .queryParam("params", params)
                .request()
                .get();
        node.setRunning(true);
        node.setStartCommand(params);
        hub.removeNode(hub.getNodeList().stream().filter(n -> n.getHost().equals(node.getHost()) && n.getPort() == node.getPort()).findFirst().get());
        hub.addNode(node);
        sqliteAdapter.updateNode(node);
        return "Node started";
    }

    @GET
    @Timed
    @Path("/stopNode")
    public String stopNode(@QueryParam("host") String host, @QueryParam("port") int port) {
        Node node = hub
                .getNodeList()
                .stream()
                .filter(n -> n.getHost().equals(host) && n.getPort() == port)
                .findFirst()
                .get();
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://" + host + ":" + port + "/node/stop");
        target
                .request()
                .get();
        node.setRunning(false);
        sqliteAdapter.updateNode(node);
        return "Node stopped";
    }
}

