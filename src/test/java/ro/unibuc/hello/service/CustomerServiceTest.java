package ro.unibuc.hello.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomerServiceTest {
    @Mock
    CustomerRepository mockCustomerRepository;
    @Mock
    PageVisitRepository mockPageVisitRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void test_registerCustomer(){
        // Arrange
        String name = "Bob";
        Customer customer = new Customer(name);
        Customer returnCustomer = new Customer(name);
        returnCustomer.customerId = "1";

        when(mockCustomerRepository.save(customer)).thenReturn(returnCustomer);
        // Act
        Customer returnedCustomer = customerService.registerCustomer(customer);

        // Assert
        Assertions.assertEquals(returnCustomer.customerId, returnedCustomer.customerId);
        Assertions.assertEquals(returnCustomer.name, returnedCustomer.name);
    }

    @Test
    void test_registerWebsite(){
        // Arrange
        String id = "1";

        Customer customer = new Customer("Bob");
        Website website = new Website("example.com", "example");
        customer.customerId = id;
        customer.websites.add(website);

        when(mockCustomerRepository.findById("1")).thenReturn(Optional.of(customer));
        when(mockCustomerRepository.save(any())).thenReturn(customer);
        // Act
        customerService.registerWebsite(id, website);

        // Assert
        // ?
    }

    @Test
    void test_visitReport(){
        // Arrange
        String id = "1";

        Customer customer = new Customer("Bob");
        Website website = new Website("example.com", "example");
        List<WebsiteVisitReport> webSiteList = new ArrayList<>();
        customer.websites.add(website);

        when(mockCustomerRepository.findById("1")).thenReturn(Optional.of(customer));

        // Act
        List<WebsiteVisitReport> returnReport = customerService.test(id);

        // Assert
        Assertions.assertEquals(returnReport.get(0).baseUrl, "example.com");
    }
}
