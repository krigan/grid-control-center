package lv.ctco.views;

import io.dropwizard.views.View;

import java.nio.charset.Charset;

public class GridImageView extends View {

    private String path;

    public GridImageView(String path) {
        super("/image.ftl", Charset.forName("UTF8"));
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
