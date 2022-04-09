package ro.unibuc.hello.controller;

import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicReference;

import ro.unibuc.hello.dto.PageVisit;
import ro.unibuc.hello.service.PageVisitService;

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
        AtomicReference<PageVisit> res = new AtomicReference<>();
        Metrics.counter("pageVisit.webhook.count", "endpoint", "pageVisit").increment();
        Metrics.timer("pageVisit.webhook.time", "endpoint", "pageVisit").record(() -> {
            res.set(pageVisitService.pageVisitWebhook(pageVisit));
        });
        return res.get();
    }
}
