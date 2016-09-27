package lv.ctco.configuration;

import io.dropwizard.Application;
import io.dropwizard.bundles.assets.ConfiguredAssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import lv.ctco.beans.Hub;
import lv.ctco.resources.GridControlResource;
import lv.ctco.resources.GridHubResource;
import lv.ctco.resources.GridNodeResource;

import java.util.HashMap;
import java.util.HashSet;
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
        initGrid();
        environment.healthChecks().register("HealthCheck", new GridControlHealthCheck());
        environment.jersey().register(new GridControlResource(configuration));
        environment.jersey().register(new GridHubResource(configuration));
        environment.jersey().register(new GridNodeResource(configuration));
    }

    private void initGrid() {
        hub = new Hub(new HashSet<>());
    }

}
