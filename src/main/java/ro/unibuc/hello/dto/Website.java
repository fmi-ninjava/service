package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Website {
    @Id
    public String baseUrl;
    public String name;

    public Website() {}

    public Website(String baseUrl, String name) {
        this.baseUrl = baseUrl;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseUrl);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Website)) {
            return false;
        }

        Website website = (Website) o;
        return Objects.equals(baseUrl, website.baseUrl);
    }
}
