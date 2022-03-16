package ro.unibuc.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.PageVisit;
import ro.unibuc.hello.service.HelperService;
import ro.unibuc.hello.service.PageVisitService;

import javax.servlet.http.HttpServletRequest;

import static com.mongodb.client.model.Aggregates.lookup;

@RestController
@RequestMapping("page-visit")
public class PageVisitController {

    private final PageVisitService pageVisitService;

    @Autowired
    public PageVisitController(PageVisitService pageVisitService) {
        this.pageVisitService = pageVisitService;
    }

    @PostMapping()
    public PageVisit pageVisitWebhook(@RequestBody PageVisit pageVisit) {
        return pageVisitService.pageVisitWebhook(pageVisit);
    }
}
