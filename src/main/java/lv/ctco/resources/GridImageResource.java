package lv.ctco.resources;

import com.codahale.metrics.annotation.Timed;
import lv.ctco.views.GridImageView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/image")
public class GridImageResource {

    @GET
    @Timed
    public GridImageView showImageResource(@QueryParam("path") String path) {
        return new GridImageView(path);
    }
}
