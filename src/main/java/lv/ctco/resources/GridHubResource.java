package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.beans.HubModel;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;
import lv.ctco.helpers.ParamHelper;

import javax.ws.rs.*;
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

    @POST
    @Timed
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public String startHub(HubModel hubModel) {
        if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
            return "No selenium jar found";
        } else {
            try {
                startCommand = "java -jar " + configuration.getSeleniumJarFileName() + ParamHelper.getHubStartParameters(hubModel);
                process = Runtime.getRuntime().exec(startCommand);

                hub.setStartCommand(startCommand);
                hub.setRunning(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Hub started with start command " + startCommand;
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

