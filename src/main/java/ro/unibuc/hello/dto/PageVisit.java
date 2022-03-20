package ro.unibuc.hello.dto;

import org.springframework.data.domain.Page;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Objects;

public class PageVisit {
    public String baseUrl;
    public String urlPath;
    public String originatingIp;
    public Date timestamp = Date.from(OffsetDateTime.now().toInstant());

    public PageVisit() {}

    public PageVisit(String baseUrl, String urlPath) {
        this.baseUrl = baseUrl;
        this.urlPath = urlPath;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseUrl, urlPath, timestamp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PageVisit)) {
            return false;
        }

        PageVisit pageVisit = (PageVisit) o;
        return Objects.equals(baseUrl, pageVisit.baseUrl) &&
                Objects.equals(urlPath, pageVisit.urlPath) &&
                Objects.equals(timestamp, pageVisit.timestamp);
    }
}
