package de.htwg.smartplant.caching;

import java.util.Map;

public class CachedRequest {

    private String url = "";
    private Map<String,String> requestParams = null;
    private Map<String,String> headers = null;

    public String getUrl() {
        return url;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public CachedRequest(Map<String, String> requestParam, String url, Map<String,String> headers){
        this.url = url;
        this.headers = headers;
        this.requestParams = requestParam;
    }
}
