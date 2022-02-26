package ro.unibuc.hello.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.PageVisit;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;

import static com.mongodb.client.model.Aggregates.lookup;


@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PageVisitRepository pageVisitRepository;

    @PostMapping("/customers")
    @ResponseBody
    public Customer registerCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return customer;
    }

    @GetMapping("/customers")
    @ResponseBody
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @DeleteMapping("/customers/{customerId}")
    @ResponseBody
    public Optional<Customer> deleteCustomer(@PathVariable String customerId) {
        var customerOpt = customerRepository.findById(customerId);
        customerOpt.ifPresent(customer -> customerRepository.delete(customer));
        return customerOpt;
    }

    @PostMapping("/customers/{customerId}/websites")
    @ResponseBody
    public void registerWebsite(@PathVariable String customerId, @RequestBody Website website) {
        var customerOpt = customerRepository.findById(customerId);
        customerOpt.ifPresent(customer -> {
            customer.websites.add(website);
            customerRepository.save(customer);
        });
    }

    @GetMapping("/page-visit/{customerId}")
    @ResponseBody
    public List<WebsiteVisitReport> test(@PathVariable String customerId) {
        var res = new ArrayList<WebsiteVisitReport>();
        customerRepository
                .findById(customerId)
                .ifPresent(customer -> {
                    customer.websites.forEach(website -> {
                        var websiteVisitReport = new WebsiteVisitReport();
                        websiteVisitReport.baseUrl = website.baseUrl;
                        websiteVisitReport.pageVisits = pageVisitRepository.findByBaseUrl(website.baseUrl);
                        res.add(websiteVisitReport);
                    });
                });
        return res;
    }
}
