package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/hub")
@Produces(MediaType.TEXT_HTML)
public class GridHubResource {

    private static final String SELENIUM_SERVER_STANDALONE = "selenium-server-standalone-2.52.0.jar";
    String startCommand;
    Process process;
    private GridControlConfiguration configuration;

    public GridHubResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    public String startHub(@QueryParam("params") String params) {
        if (!FileUtilsHelper.isFileExist(SELENIUM_SERVER_STANDALONE)) {
            return "No selenium jar found";
        } else {
            if (params == null) {
                params = "";
            }
            try {
                startCommand = "java -jar " + SELENIUM_SERVER_STANDALONE + " " + params;
                process = Runtime.getRuntime().exec(startCommand);
                hub.setStartCommand(startCommand);
                hub.setRunning(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Hub started";
        }
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopHub() {
        if (process!=null) {
            process.destroy();
            hub.setRunning(false);
        }
        return "Hub stopped";
    }

    @GET
    @Timed
    @Path("/restart")
    public String restartHub() {
        process.destroy();
        hub.setRunning(true);
        try {
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
}

