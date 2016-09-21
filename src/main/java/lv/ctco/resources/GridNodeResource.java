package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.beans.Node;
import lv.ctco.helpers.FileUtilsHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/node")
@Produces(MediaType.TEXT_HTML)
public class GridNodeResource extends GridInfrastructure{

    private StringBuilder startCommand;

    @GET
    @Timed
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Node startNode(Node node) {
        if (!FileUtilsHelper.isFileExist(SELENIUM_SERVER_STANDALONE)) {
            node.setRunning(false);
            return node;
        } else {
            try {
                startCommand=new StringBuilder("java -jar").append(SELENIUM_SERVER_STANDALONE);

                if (node.getBrowsers().size()>0){
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
        // TODO implement me here
        return "Node stopped";
    }
}
