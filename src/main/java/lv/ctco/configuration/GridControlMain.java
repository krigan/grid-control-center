package lv.ctco.configuration;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import lv.ctco.beans.Hub;
import lv.ctco.beans.Node;
import lv.ctco.resources.GridControlResource;
import lv.ctco.resources.GridHubResource;
import lv.ctco.resources.GridNodeResource;

import java.util.ArrayList;
import java.util.List;

public class GridControlMain extends Application<GridControlConfiguration> {

    public static Hub hub;
    public static List<Node> nodes;

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
        nodes = new ArrayList<>();
        hub = new Hub(nodes);
    }

}
