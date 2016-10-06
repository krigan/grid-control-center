package lv.ctco.views;

import io.dropwizard.views.View;
import lv.ctco.beans.LogItem;
import lv.ctco.configuration.GridControlConfiguration;

import java.nio.charset.Charset;
import java.util.List;

public class GridLogsView extends View {


    private GridControlConfiguration configuration;
    private List<LogItem> logsList;

    public GridLogsView(GridControlConfiguration configuration, List<LogItem> logsList) {
        super("/logs.ftl", Charset.forName("UTF8"));
        this.configuration = configuration;
        this.logsList = logsList;
    }

    public List<LogItem> getLogsList() {
        return logsList;
    }


}
