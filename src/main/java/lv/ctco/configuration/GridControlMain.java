package lv.ctco.configuration;

import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import lv.ctco.adapters.SqliteAdapter;
import lv.ctco.beans.Hub;
import lv.ctco.helpers.HostHelper;
import lv.ctco.resources.GridControlResource;
import lv.ctco.resources.GridHubResource;
import lv.ctco.resources.GridLogResource;
import lv.ctco.resources.GridNodeResource;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.HashMap;
import java.util.Map;

public class GridControlMain extends Application<GridControlConfiguration> {

    public static Hub hub;

    public static void main(String[] args) throws Exception {
        new GridControlMain().run(args);
    }

    @Override
    public String getName() {
        return "at-grid-control-center";
    }

    @Override
    public void initialize(Bootstrap<GridControlConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        Map<String, String> map = new HashMap<>();
        map.put("/assets", "/assets");
        bootstrap.addBundle(new ConfiguredAssetsBundle(map));
    }

    @Override
    public void run(GridControlConfiguration configuration, Environment environment) {
        initGrid(configuration);
        environment.healthChecks().register("HealthCheck", new GridControlHealthCheck());
        environment.jersey().register(new GridControlResource(configuration));
        environment.jersey().register(new GridHubResource(configuration));
        environment.jersey().register(new GridNodeResource(configuration));
        environment.jersey().register(new GridLogResource(configuration));
    }

    private void initGrid(GridControlConfiguration configuration) {
        if (configuration.getMode().equals("node")) {
            String controlCenterUrl = configuration.getGridControlCenterUrl();
            if (controlCenterUrl == null || controlCenterUrl.isEmpty()) {
                throw new IllegalStateException("gridControlCenterUrl param need to be defined in configuration");
            }
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(controlCenterUrl + "/register");
            target
                    .queryParam("host", HostHelper.getHostName())
                    .queryParam("port", HostHelper.getPort(configuration))
                    .request()
                    .get();
        } else if (configuration.getMode().equals("hub")) {
            hub = new SqliteAdapter().getHubInfo();
            if (hub.isRunning()) {
                new GridHubResource(configuration).startHub(hub.getStartCommand());
            }
        }
    }

}
