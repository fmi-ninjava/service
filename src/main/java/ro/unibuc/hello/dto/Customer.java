package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    @Id
    public String customerId;
    public String name;
    public List<Website> websites = new ArrayList<Website>();

    public Customer() {}

    public Customer(String name) {
        this.name = name;
    }
}
