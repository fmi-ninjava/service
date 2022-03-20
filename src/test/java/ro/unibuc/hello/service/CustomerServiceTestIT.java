package ro.unibuc.hello.service;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.exception.DuplicateEntryException;
import ro.unibuc.hello.exception.PropertyRequiredException;

@SpringBootTest
public class CustomerServiceTestIT {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    @AfterEach
    void afterEach() {
        customerService.CleanUpDb();
    }

    @Test
    void test_registerCustomer_returnsValidCustomerIdAndName() {
        // Arrange
        Customer customerToRegister = new Customer("bob");

        // Act
        var res = customerService.registerCustomer(customerToRegister);

        // Assert
        Assertions.assertNotNull(res.customerId);
        Assertions.assertEquals(res.name, customerToRegister.name);
    }

    @Test
    void test_registerCustomerWithNullName_throwsException() {
        // Arrange
        Customer customerToRegister = new Customer();

        // Act & Assert
        Assert.assertThrows(
                PropertyRequiredException.class,
                () -> customerService.registerCustomer(customerToRegister));
    }

    @Test
    void test_registerWebsite_addsWebsiteToCustomer() {
        // Arrange
        var customerId = customerService.registerCustomer(new Customer("foo")).customerId;
        var websiteToRegister = new Website("https://example.com", "Test website");

        // Act
        customerService.registerWebsite(customerId, websiteToRegister);

        // Assert
        var customerOpt = customerService.getCustomer(customerId);
        Assertions.assertTrue(customerOpt.isPresent());
        var customerWebsites = customerOpt.get().websites;
        Assertions.assertTrue(customerWebsites.contains(websiteToRegister));
    }

    @Test
    void test_registerSameWebsite_throwsException() {
        // Arrange
        var customerId1 = customerService.registerCustomer(new Customer("foo")).customerId;
        var customerId2 = customerService.registerCustomer(new Customer("bar")).customerId;
        var websiteToRegister = new Website("https://example.com", "Test website");

        // Act
        customerService.registerWebsite(customerId1, websiteToRegister);


        // Assert
        Assertions.assertThrows(
                DuplicateEntryException.class,
                () -> customerService.registerWebsite(customerId2, websiteToRegister));
    }
}
