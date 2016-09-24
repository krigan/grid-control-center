package lv.ctco.beans;

import java.net.URL;
import java.util.Set;

public class Hub {

    private Set<Node> nodeList;
    private String startParams;
    private boolean isRunning;
    private URL url;

    public Hub(Set<Node> nodeList) {
        this.nodeList = nodeList;
        isRunning = false;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Set<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(Set<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public String getStartParams() {
        return startParams;
    }

    public void setStartParams(String startParams) {
        this.startParams = startParams;
    }

    public void setStartCommand(String startParams) {
        this.startParams = startParams;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public void removeNode(Node node) {
        nodeList.remove(node);
    }
}
