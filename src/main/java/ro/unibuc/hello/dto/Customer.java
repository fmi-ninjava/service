package ro.unibuc.hello.dto;

import org.springframework.data.annotation.Id;

import java.util.UUID;

public class Customer {
    @Id
    private final UUID customerId;
    private final String name;

    public Customer(String name) {
        this.customerId = UUID.randomUUID();
        this.name = name;
    }

    public UUID getCustomerId() { return this.customerId; }
    public String getName() { return this.name; }
}
