package lv.ctco.beans;

import lv.ctco.enums.Browser;

import java.util.Set;

public class Node {

    private Set<Browser> browsers;
    private Hub hub;
    private boolean isRunning;

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public Set<Browser> getBrowsers() {
        return browsers;
    }

    public void setBrowsers(Set<Browser> browsers) {
        this.browsers = browsers;
    }
}
