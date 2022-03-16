package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;
import ro.unibuc.hello.service.CustomerService;

import static com.mongodb.client.model.Aggregates.lookup;

@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping()
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @DeleteMapping("{customerId}")
    public Customer deleteCustomer(@PathVariable String customerId) {
        return customerService.deleteCustomer(customerId);
    }

    @PostMapping("{customerId}/websites")
    public void registerWebsite(@PathVariable String customerId, @RequestBody Website website) {
        customerService.registerWebsite(customerId, website);
    }

    @GetMapping("/page-visit/{customerId}")
    public List<WebsiteVisitReport> test(@PathVariable String customerId) {
        return customerService.test(customerId);
    }
}
