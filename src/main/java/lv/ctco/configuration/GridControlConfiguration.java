package lv.ctco.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.bundles.assets.AssetsBundleConfiguration;
import io.dropwizard.bundles.assets.AssetsConfiguration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Krigan on 26/07/16.
 */
public class GridControlConfiguration extends Configuration implements AssetsBundleConfiguration {

    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();
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

    @Override
    public AssetsConfiguration getAssetsConfiguration() {
        return assets;
    }

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
