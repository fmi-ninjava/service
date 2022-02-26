package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.PageVisit;

import java.util.List;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface PageVisitRepository extends MongoRepository<PageVisit, String> {
    public List<PageVisit> findByBaseUrl(String baseUrl);
//    public List<InformationEntity> findByDescription(String description);
}