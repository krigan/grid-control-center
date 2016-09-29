package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;

import javax.annotation.Nullable;
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

    private GridControlConfiguration configuration;
    private String startCommand;
    private Process process;

    public GridHubResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    public String startHub(@QueryParam("params") String params) throws IOException {
        if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
            return "No selenium jar found";
        } else {
            if (params == null) {
                params = "";
            }
            startCommand = params;
            process = Runtime.getRuntime().exec(startCommand);
            hub.setStartCommand(startCommand);
            hub.setRunning(true);
            return "Hub started with start command " + startCommand;
        }
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopHub() {
        if (process != null) {
            process.destroy();
            hub.setRunning(false);
        }
        return "Hub stopped";
    }

    @GET
    @Timed
    @Path("/restart")
    public String restartHub(@Nullable @QueryParam("params") String params) {
        process.destroy();
        hub.setRunning(false);
        try {
            startCommand = params;
            process = Runtime.getRuntime().exec(startCommand);
            hub.setRunning(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Hub restarted";
    }

    @GET
    @Timed
    @Path("/status")
    public String statusHub() {
        return new Gson().toJson(hub);
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
//        hub.removeNode(hub
//                .getNodeList()
//                .stream()
//                .filter(n -> n.getHost().equals(node.getHost()) && n.getPort() == node.getPort())
//                .findFirst()
//                .get());
//        hub.addNode(node);
        return "Node started";
    }
}

