package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.gson.Gson;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;
import lv.ctco.helpers.FileUtilsHelper;
import lv.ctco.helpers.HostHelper;

import javax.annotation.Nullable;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static lv.ctco.configuration.GridControlMain.hub;

@Path("/node")
@Produces(MediaType.TEXT_HTML)
public class GridNodeResource {

    private Process process;
    private GridControlConfiguration configuration;
    private Node node;

    public GridNodeResource(GridControlConfiguration configuration) {
        this.configuration = configuration;
    }

    @GET
    @Timed
    @Path("/start")
    public String startNode(@QueryParam("host") String host, @QueryParam("port") int port, @QueryParam("params") String params) {
        String mode = configuration.getMode();
        node = new Node(HostHelper.getHostName(), HostHelper.getPort(configuration), false);
        if (mode.equals("node")) {
            if (!FileUtilsHelper.isFileExist(configuration.getSeleniumJarFileName())) {
                return "Node not started. " + configuration.getSeleniumJarFileName() + " not found";
            } else {
                try {
                    process = Runtime.getRuntime().exec(params);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                node.setRunning(true);
                node.setStartCommand(params);
                return "Node started with start command " + params;
            }
        }
        if (mode.equals("hub")) {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://" + host + ":" + port + "/node/start");
            target
                    .queryParam("params", params)
                    .request()
                    .get();
            node.setRunning(true);
            node.setStartCommand(params);
            hub.addNode(node);
        }
        return "Unknown mode";
    }

    @GET
    @Timed
    @Path("/stop")
    public String stopNode() {
        if (process != null) {
            process.destroy();
            node.setRunning(false);
        }
        return "Node stopped";
    }

    @GET
    @Timed
    @Path("/restart")
    public String restartHub(@Nullable @QueryParam("params") String params) throws IOException {
        process.destroy();
        node.setRunning(false);
        hub.removeNode(node);
        process = Runtime.getRuntime().exec(params);
        node.setRunning(true);
        return "Hub restarted";
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
    public String statusNode() {
        return new Gson().toJson(node);
    }

    private void killBrowser(String browser) {
        browser = browser.toLowerCase();
        String os = configuration.getOs();
        if (os.equals("linux")) {
            try {
                Runtime.getRuntime().exec("pkill -f " + browser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (os.equals("windows")) {
            try {
                if (browser.contains("chrome")) {
                    Runtime.getRuntime().exec("TASKKILL /F /IM chrome.exe /T");
                    Runtime.getRuntime().exec("TASKKILL /F /IM chromeDriverServer.exe /T");
                }
                if (browser.contains("firefox")) {
                    Runtime.getRuntime().exec("TASKKILL /F /IM firefox.exe /t");
                }
                if (browser.contains("ie")) {
                    Runtime.getRuntime().exec("TASKKILL /F /IM iexplore.exe /t");
                    Runtime.getRuntime().exec("TASKKILL /F /IM IEDriverServer.exe /t");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @GET
    @Timed
    @Path("/browserList")
    public String getBrowsersList() throws IOException {
        String os = configuration.getOs();
        if (os.equals("linux")) {
            Process process = Runtime.getRuntime().exec("ps -e");
            return getBrowserFilteredList(process);
        }
        if (os.equals("windows")) {
            Process process = Runtime.getRuntime().exec
                    (System.getenv("windir") + "\\system32\\" + "tasklist.exe");
            return getBrowserFilteredList(process);
        }
        return "Unable to define open browsers";
    }

    private String getBrowserFilteredList(Process process) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(process.getInputStream()));

        String s;
        List<String> processes = new ArrayList<>();
        while ((s = stdInput.readLine()) != null) {
            processes.add(s);
        }
        stdInput.close();
        List<String> openBrowsersProcesses = new ArrayList<>();
        processes
                .stream()
                .filter(p -> p.contains("chrome") || p.contains("firefox") || p.contains("iexplore"))
                .forEach(openBrowsersProcesses::add);
        openBrowsersProcesses.forEach(System.out::println);
        String result = "";
        for (String str : openBrowsersProcesses) {
            result += str + " \r\n";
        }
        return result;
    }
}
