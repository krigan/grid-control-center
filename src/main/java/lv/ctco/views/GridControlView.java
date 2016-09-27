package lv.ctco.views;

import io.dropwizard.views.View;
import lv.ctco.beans.Hub;
import lv.ctco.beans.Node;
import lv.ctco.configuration.GridControlConfiguration;

import java.nio.charset.Charset;
import java.util.Set;

import static lv.ctco.configuration.GridControlMain.hub;

public class GridControlView extends View {

    private GridControlConfiguration configuration;

    public GridControlView(GridControlConfiguration configuration) {
        super("/main.ftl", Charset.forName("UTF8"));
        this.configuration = configuration;
    }

    public Hub getHub() {
        return hub;
    }

    public Set<Node> getNodes() {
        return hub.getNodeList();
    }
}