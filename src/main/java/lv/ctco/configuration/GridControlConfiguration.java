package lv.ctco.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Krigan on 26/07/16.
 */
public class GridControlConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String os;

    @NotEmpty
    @JsonProperty
    private String mode;

    @NotEmpty
    @JsonProperty
    private String seleniumJarFileName;

    @NotEmpty
    @JsonProperty
    private String javaPath;

    public String getOs() {
        return os;
    }

    public String getMode() {
        return mode;
    }

    public String getSeleniumJarFileName() {
        return seleniumJarFileName;
    }

    public String getJavaPath() {
        return javaPath;
    }
}
