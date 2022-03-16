package ro.unibuc.hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ro.unibuc.hello.dto.PageVisit;
import ro.unibuc.hello.service.PageVisitService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class PageVisitControllerTest {
    @Mock
    private PageVisitService pageVisitService;

    @InjectMocks
    private PageVisitController PageVisitController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(PageVisitController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void test_pageVisitWebhook() throws Exception {
        // Arrange
        PageVisit pageVisit = new PageVisit("example.com", "Home/Index");
        when(pageVisitService.pageVisitWebhook(any())).thenReturn(pageVisit);

        // Act
        MvcResult result = mockMvc.perform(post("/page-visit")
                        .content(objectMapper.writeValueAsString(pageVisit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(pageVisit));
    }
}
