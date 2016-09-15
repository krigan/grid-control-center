package lv.ctco.configuration;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by Krigan on 26/07/16.
 */
public class GridControlHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }

}
