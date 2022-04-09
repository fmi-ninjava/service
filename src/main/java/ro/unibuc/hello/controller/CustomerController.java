package ro.unibuc.hello.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;
import ro.unibuc.hello.service.CustomerService;

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
        AtomicReference<List<Customer>> res = new AtomicReference<>();
        Metrics.timer("customer.list.time", "endpoint", "customer").record(() -> {
           res.set(customerService.listCustomers());
        });
        return res.get();
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
        AtomicReference<List<WebsiteVisitReport>> res = new AtomicReference<>();
        Metrics.counter("pageVisit.report.count", "endpoint", "pageVisit-report").increment();
        Metrics.timer("pageVisit.report.time", "endpoint", "pageVisit-report").record(() -> {
            res.set(customerService.defaultReport(customerId));
        });
        return res.get();
    }
}
