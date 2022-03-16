package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PageVisitRepository pageVisitRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PageVisitRepository pageVisitRepository) {
        this.customerRepository = customerRepository;
        this.pageVisitRepository = pageVisitRepository;
    }

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    public Customer deleteCustomer(String customerId) {
        var customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new EntityNotFoundException(customerId);
        }

        var customer = customerOpt.get();
        customerRepository.delete(customer);
        return customer;
    }

    public void registerWebsite(String customerId, Website website) {
        var customerOpt = customerRepository.findById(customerId);
        customerOpt.ifPresent(customer -> {
            customer.websites.add(website);
            customerRepository.save(customer);
        });
    }

    public List<WebsiteVisitReport> test(String customerId) {
        var res = new ArrayList<WebsiteVisitReport>();
        customerRepository
                .findById(customerId)
                .ifPresent(customer -> customer.websites.forEach(website -> {
                    var websiteVisitReport = new WebsiteVisitReport();
                    websiteVisitReport.baseUrl = website.baseUrl;
                    websiteVisitReport.pageVisits = pageVisitRepository.findByBaseUrl(website.baseUrl);
                    res.add(websiteVisitReport);
                }));
        return res;
    }
}
