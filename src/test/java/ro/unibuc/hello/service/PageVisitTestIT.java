package ro.unibuc.hello.service;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.PageVisit;
import ro.unibuc.hello.dto.Website;

@SpringBootTest
public class PageVisitTestIT {
    @Autowired
    PageVisitService pageVisitService;
    @Autowired
    CustomerService customerService;

    @AfterEach
    void afterEach() {
        customerService.CleanUpDb();
        pageVisitService.CleanUpDb();
    }
    
    @Test
    void test_pageVisit_appearsInReport() {
        // Arrange
        var customerToRegister = new Customer("foo");
        var websiteToRegister = new Website("https://example.com", "Test website");
        var pageVisit = new PageVisit("https://example.com", "/Home/Index");

        // Act
        var customerId = customerService.registerCustomer(customerToRegister).customerId;
        customerService.registerWebsite(customerId, websiteToRegister);
        pageVisitService.pageVisitWebhook(pageVisit);

        var report = customerService.defaultReport(customerId);

        // Assert
        Assertions.assertTrue(report.stream()
                .anyMatch(wvr -> wvr.pageVisits.contains(pageVisit)));
    }
}
