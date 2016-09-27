package lv.ctco.helpers;

import io.dropwizard.jetty.ConnectorFactory;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.server.DefaultServerFactory;
import lv.ctco.configuration.GridControlConfiguration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by S89NV3 on 27.09.2016.
 */
public class HostHelper {

    public static String getHostName() {
        String hostname;
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            return hostname;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPort(GridControlConfiguration configuration) {
        DefaultServerFactory serverFactory = (DefaultServerFactory) configuration.getServerFactory();
        for (ConnectorFactory connector : serverFactory.getApplicationConnectors()) {
            if (connector.getClass().isAssignableFrom(HttpConnectorFactory.class)) {
                return ((HttpConnectorFactory) connector).getPort();
            }
        }
        return -1;
    }
}
