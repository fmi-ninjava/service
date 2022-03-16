package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ro.unibuc.hello.data.PageVisitRepository;
import ro.unibuc.hello.dto.PageVisit;

import javax.servlet.http.HttpServletRequest;

@Service
public class PageVisitService {
    private final PageVisitRepository pageVisitRepository;

    @Autowired
    public PageVisitService(PageVisitRepository pageVisitRepository) {
        this.pageVisitRepository = pageVisitRepository;
    }

    public PageVisit pageVisitWebhook(PageVisit pageVisit) {
        // var originatingIp = request.getRemoteAddr();
        pageVisit.originatingIp = HelperService.generateRandomIp();
        pageVisitRepository.insert(pageVisit);
        return pageVisit;
    }
}
