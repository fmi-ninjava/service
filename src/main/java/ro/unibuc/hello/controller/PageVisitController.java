package ro.unibuc.hello.controller;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.PageVisit;
import ro.unibuc.hello.service.HelperService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.mongodb.client.model.Aggregates.lookup;

@Controller
public class PageVisitController {

    private final PageVisitRepository pageVisitRepository;

    public PageVisitController(PageVisitRepository pageVisitRepository) {
        this.pageVisitRepository = pageVisitRepository;
    }

    @PostMapping("/page-visit")
    @ResponseBody
    public PageVisit pageVisitWebhook(@RequestBody PageVisit pageVisit, HttpServletRequest request) {
        // var originatingIp = request.getRemoteAddr();
        pageVisit.originatingIp = HelperService.generateRandomIp();
        pageVisitRepository.insert(pageVisit);
        return pageVisit;
    }
}
