package lv.ctco.beans;

import java.util.List;

public class Hub {

    private List<Node> nodeList;
    private String startParams;
    private boolean isRunning;

    public Hub(List<Node> nodeList) {
        this.nodeList = nodeList;
        isRunning = false;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
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
}
