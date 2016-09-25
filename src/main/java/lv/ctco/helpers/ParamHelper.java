package lv.ctco.helpers;

import lv.ctco.beans.HubModel;
import lv.ctco.beans.NodeModel;

/**
 * Created by Anastasia on 9/25/2016.
 */
public class ParamHelper {

    public static String getHubStartParameters(HubModel hubModel){
        StringBuilder finalParamString=new StringBuilder(" -role hub");
        if (hubModel.getPort()!=null) finalParamString.append(" -port=").append(hubModel.getPort());
        if (hubModel.getTimeout()!=null) finalParamString.append(" -timeout=").append(hubModel.getTimeout());
        return finalParamString.toString();
    }

    public static String getNodeStartParameters(NodeModel hubModel){
        StringBuilder finalParamString=new StringBuilder(" -role node");
        if (hubModel.getConfiguration().getPort()!=null) finalParamString.append(" -port=").append(hubModel.getConfiguration().getPort());
        return finalParamString.toString();
    }
}
