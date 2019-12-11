package de.htwg.smartplant.caching;

import java.util.ArrayList;
import java.util.List;

public class RequestCacheQueue {

    private List<CachedRequest> requests;

    public RequestCacheQueue() {
        requests = new ArrayList<>();
    }
    public void addRequest(CachedRequest request) {
        requests.add(request);
    }

    public List<CachedRequest> getRequests(){
        return this.requests;
    }
}
