package ro.unibuc.hello.controller;

import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.CustomerRepository;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.Customer;
import ro.unibuc.hello.dto.PageVisit;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.mongodb.client.model.Aggregates.lookup;

@Controller
public class PageVisitController {

    @Autowired
    private PageVisitRepository pageVisitRepository;

    private String generateRandomIp() {
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

    @PostMapping("/page-visit")
    @ResponseBody
    public PageVisit pageVisitWebhook(@RequestBody PageVisit pageVisit, HttpServletRequest request) {
        // var originatingIp = request.getRemoteAddr();
        pageVisit.originatingIp = generateRandomIp();
        pageVisitRepository.insert(pageVisit);
        return pageVisit;
    }
}
