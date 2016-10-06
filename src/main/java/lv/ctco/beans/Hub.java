package lv.ctco.beans;

import java.util.Set;

public class Hub {

    private Set<Node> nodeList;
    private String startCommand;
    private boolean isRunning;
    private String hostName;
    private int port;

    public Hub(Set<Node> nodeList) {
        this.nodeList = nodeList;
        isRunning = false;
    }

    public String getHost() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Set<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(Set<Node> nodeList) {
        this.nodeList = nodeList;
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

    public String getStartCommand() {
        return startCommand;
    }

    public void setStartCommand(String startCommand) {
        this.startCommand = startCommand;
    }

    public void removeNode(Node node) {
        nodeList.remove(node);
    }
}
