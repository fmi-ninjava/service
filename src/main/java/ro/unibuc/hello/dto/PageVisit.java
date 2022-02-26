package ro.unibuc.hello.dto;

import java.time.OffsetDateTime;
import java.util.Date;

public class PageVisit {
    public String baseUrl;
    public String urlPath;
    public String originatingIp;
    public Date timestamp;

    public PageVisit(String baseUrl, String urlPath) {
        this.baseUrl = baseUrl;
        this.urlPath = urlPath;
        this.timestamp = Date.from(OffsetDateTime.now().toInstant());
    }
}
