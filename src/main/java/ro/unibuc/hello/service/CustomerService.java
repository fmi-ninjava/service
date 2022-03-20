package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;
import ro.unibuc.hello.exception.DuplicateEntryException;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.PropertyRequiredException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PageVisitRepository pageVisitRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PageVisitRepository pageVisitRepository) {
        this.customerRepository = customerRepository;
        this.pageVisitRepository = pageVisitRepository;
    }

    void CleanUpDb() {
        customerRepository.deleteAll();
    }

    public Customer registerCustomer(Customer customer) {
        if(customer.name == null || customer.name.isEmpty()) {
            throw new PropertyRequiredException("name", customer);
        }
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomer(String customerId) {
        return customerRepository.findById(customerId);
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
        var allCustomerWebsites = customerRepository.findAll()
                .stream().map(customer -> customer.websites)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        if(allCustomerWebsites.contains(website)) {
            throw new DuplicateEntryException(website);
        }

        var customerOpt = customerRepository.findById(customerId);
        customerOpt.ifPresent(customer -> {
            customer.websites.add(website);
            customerRepository.save(customer);
        });
    }

    public List<WebsiteVisitReport> defaultReport(String customerId) {
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
