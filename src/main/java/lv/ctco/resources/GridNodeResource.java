package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.beans.HubModel;
import lv.ctco.beans.Node;
import lv.ctco.beans.NodeModel;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;
import lv.ctco.helpers.ParamHelper;
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

    @POST
    @Timed
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public String startHub(NodeModel nodeModel) {
        if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
            return "No selenium jar found";
        } else {
            try {
                startCommand = "java -jar " + configuration.getSeleniumJarFileName() + ParamHelper.getNodeStartParameters(nodeModel);
                process = Runtime.getRuntime().exec(startCommand);

                hub.setStartCommand(startCommand);
                hub.setRunning(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
