package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

public class Website {
    @Id
    public String baseUrl;
    public String name;

    public Website() {}

    public Website(String baseUrl, String name) {
        this.baseUrl = baseUrl;
        this.name = name;
    }
}
