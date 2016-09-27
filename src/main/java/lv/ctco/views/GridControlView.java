package lv.ctco.views;

import io.dropwizard.views.View;
import lv.ctco.configuration.GridControlConfiguration;

import java.nio.charset.Charset;

public class GridControlView extends View {

    private GridControlConfiguration configuration;

    public GridControlView(GridControlConfiguration configuration) {
        super("/main.ftl", Charset.forName("UTF8"));
        this.configuration = configuration;
    }
}