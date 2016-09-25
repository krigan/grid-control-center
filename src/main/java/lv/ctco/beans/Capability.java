package lv.ctco.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Capability {

    private String browserName;
    private Integer maxInstances;
    private String version;
    private String platform;
    private String seleniumProtocol;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public Integer getMaxInstances() {
        return maxInstances;
    }

    public void setMaxInstances(Integer maxInstances) {
        this.maxInstances = maxInstances;
    }

    public String getVersion() {
        return version;
    }

   public void setVersion(String version) {
        this.version = version;
    }

   public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getSeleniumProtocol() {
        return seleniumProtocol;
    }

    public void setSeleniumProtocol(String seleniumProtocol) {
        this.seleniumProtocol = seleniumProtocol;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}