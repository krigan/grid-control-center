package lv.ctco.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HubModel {

    private Object host;
    private Integer port;
    private Integer newSessionWaitTimeout;
    private List<Object> servlets = new ArrayList<Object>();
    private Object prioritizer;
    private String capabilityMatcher;
    private Boolean throwOnCapabilityNotPresent;
    private Integer nodePolling;
    private Integer cleanUpCycle;
    private Integer timeout;
    private Integer browserTimeout;
    private Integer maxSession;
    private Integer jettyMaxThreads;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Object getHost() {
        return host;
    }

    public void setHost(Object host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getNewSessionWaitTimeout() {
        return newSessionWaitTimeout;
    }

    public void setNewSessionWaitTimeout(Integer newSessionWaitTimeout) {
        this.newSessionWaitTimeout = newSessionWaitTimeout;
    }

    public List<Object> getServlets() {
        return servlets;
    }

    public void setServlets(List<Object> servlets) {
        this.servlets = servlets;
    }

    public Object getPrioritizer() {
        return prioritizer;
    }

    public void setPrioritizer(Object prioritizer) {
        this.prioritizer = prioritizer;
    }

    public String getCapabilityMatcher() {
        return capabilityMatcher;
    }

    public void setCapabilityMatcher(String capabilityMatcher) {
        this.capabilityMatcher = capabilityMatcher;
    }

    public Boolean getThrowOnCapabilityNotPresent() {
        return throwOnCapabilityNotPresent;
    }

    public void setThrowOnCapabilityNotPresent(Boolean throwOnCapabilityNotPresent) {
        this.throwOnCapabilityNotPresent = throwOnCapabilityNotPresent;
    }

    public Integer getNodePolling() {
        return nodePolling;
    }

    public void setNodePolling(Integer nodePolling) {
        this.nodePolling = nodePolling;
    }

    public Integer getCleanUpCycle() {
        return cleanUpCycle;
    }

    public void setCleanUpCycle(Integer cleanUpCycle) {
        this.cleanUpCycle = cleanUpCycle;
    }

   public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getBrowserTimeout() {
        return browserTimeout;
    }

    public void setBrowserTimeout(Integer browserTimeout) {
        this.browserTimeout = browserTimeout;
    }

    public Integer getMaxSession() {
        return maxSession;
    }

    public void setMaxSession(Integer maxSession) {
        this.maxSession = maxSession;
    }

    public Integer getJettyMaxThreads() {
        return jettyMaxThreads;
    }

    public void setJettyMaxThreads(Integer jettyMaxThreads) {
        this.jettyMaxThreads = jettyMaxThreads;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
