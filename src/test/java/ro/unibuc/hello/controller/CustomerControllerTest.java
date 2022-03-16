package ro.unibuc.hello.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.Website;
import ro.unibuc.hello.dto.WebsiteVisitReport;
import ro.unibuc.hello.service.CustomerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController CustomerController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(CustomerController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_registerCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer("Bob");

        when(customerService.registerCustomer(any())).thenReturn(customer);

        // Act
        MvcResult result = mockMvc.perform(post("/customers")
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(customer));
    }

    @Test
    void test_listCustomer() throws Exception {
        // Arrange
        var customerList = new ArrayList<Customer>();

        customerList.add(new Customer("Alice"));
        customerList.add(new Customer("Bob"));

        when(customerService.listCustomers()).thenReturn(customerList);

        // Act
        MvcResult result = mockMvc.perform(get("/customers")
                        .content(objectMapper.writeValueAsString(customerList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(customerList));
    }

    @Test
    void test_deleteCustomer() throws Exception {
        // Arrange
        Customer customer = new Customer("Bob");

        when(customerService.deleteCustomer(any())).thenReturn(customer);

        // Act
        MvcResult result = mockMvc.perform(delete("/customers/1")
                        .content(objectMapper.writeValueAsString(customer))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(customer));
    }

    @Test
    void test_registerWebsite() throws Exception {
        // Arrange
        Customer customer = new Customer("Bob");
        Website website = new Website("example.com", "example");

        doNothing().when(customerService).registerWebsite("1", website);

        // Act
        MvcResult result = mockMvc.perform(post("/customers/1/websites")
                        .content(objectMapper.writeValueAsString(website))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        //Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(customer));
    }

    @Test
    void test_visitReport() throws Exception {
        // Arrange
        List<WebsiteVisitReport> websiteList = new ArrayList<>();

        when(customerService.test(any())).thenReturn(websiteList);

        // Act
        MvcResult result = mockMvc.perform(get("/customers/page-visit/1"))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(websiteList));
    }
}
