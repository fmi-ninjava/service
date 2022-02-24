package ro.unibuc.hello.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.dto.Customer;

import java.util.List;
import java.util.UUID;

/**
 * No need to implement this interface.
 * Spring Data MongoDB automatically creates a class it implementing the interface when you run the application.
 */
@Repository
public interface CustomerRepository extends MongoRepository<Customer, UUID> {

//    public InformationEntity findByTitle(String title);
//    public List<InformationEntity> findByDescription(String description);

}