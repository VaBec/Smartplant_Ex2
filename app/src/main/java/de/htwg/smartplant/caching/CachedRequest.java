package de.htwg.smartplant.caching;

import java.util.Map;

public class CachedRequest {

    private String url;
    private Map<String,String> requestParams;

    public String getUrl() {
        return url;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public CachedRequest(Map<String, String> requestParam, String url){
        this.url = url;
        this.requestParams = requestParam;
    }
}
