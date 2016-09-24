package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;
import org.apache.commons.lang3.NotImplementedException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/node")
@Produces(MediaType.TEXT_HTML)
public class GridNodeResource {

    private StringBuilder startCommand;
    private Process process;
    private GridControlConfiguration configuration;

    public GridNodeResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Node startNode(Node node) {
        if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
            node.setRunning(false);
            return node;
        } else {
            try {
                startCommand = new StringBuilder("java -jar").append(configuration.getSeleniumJarFileName());

                if (node.getBrowsers().size() > 0) {
                    node.getBrowsers().forEach(browser -> startCommand.append(" -browser browserName=").append(browser.getName()));
                }
                startCommand.append(" -hub ").append(node.getHub().getUrl().toString());
                process = Runtime.getRuntime().exec(startCommand.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            node.setRunning(true);
            return node;
        }
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopNode() {
        if (process != null) {
            process.destroy();
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
