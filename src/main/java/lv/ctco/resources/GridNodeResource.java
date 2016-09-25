package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;
import org.apache.commons.lang3.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Optional;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/node")
@Produces(MediaType.TEXT_HTML)
public class GridNodeResource {

    private String startCommand;
    private Process process;
    private GridControlConfiguration configuration;
    private Node node;

    public GridNodeResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public String startNode(@QueryParam("params") Optional<String> params) {
        node = new Node();
        if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
            node.setRunning(false);
            return "Node not started. " + configuration.getSeleniumJarFileName() + " not found";
        } else {
            try {
                startCommand = "java -jar " + configuration.getSeleniumJarFileName() + " " ;
                process = Runtime.getRuntime().exec(startCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
            node.setRunning(true);
            node.setStartCommand(startCommand);
            hub.addNode(node);
            return "Node started with start command " + startCommand;
        }
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopNode() {
        if (process != null) {
            process.destroy();
            node.setRunning(false);
            hub.removeNode(node);
        }
        return "Node stopped";
    }

    @GET
    @Timed
    @Path("/closeBrowsers")
    public String closeBrowser(@QueryParam("browser") String browser) {
        switch (browser.toLowerCase()) {
            case "chrome": {
                killBrowser("Chrome");
                break;
            }
            case "firefox": {
                killBrowser("firefox");
                break;
            }
            case "ie": {
                killBrowser("ie");
                break;
            }
            default: {
                throw new IllegalStateException("Please set browser to close");
            }
        }
        return "Browser closed";
    }

    @GET
    @Timed
    @Path("/status")
    public String statusHub() {
        return new Gson().toJson(node);
    }

    private void killBrowser(String browser) {
        String os = configuration.getOs();
        if (os.equals("linux")) {
            try {
                // TODO verify kill command
                Runtime.getRuntime().exec("pkill " + browser);
                Runtime.getRuntime().exec("pkill " + browser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (os.equals("windows")) {
            // TODO implement me here
            throw new NotImplementedException("Not implemented yet");
        }
    }
}
