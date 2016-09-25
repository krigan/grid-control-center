package lv.ctco.helpers;

import lv.ctco.beans.HubModel;

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
}
