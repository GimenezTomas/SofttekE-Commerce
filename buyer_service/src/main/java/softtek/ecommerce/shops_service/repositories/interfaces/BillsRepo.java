package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Bill;
import softtek.ecommerce.shops_service.entities.Item;
import softtek.ecommerce.shops_service.entities.Order;

import java.util.List;

@RepositoryRestResource( path = "bills" )
public interface BillsRepo extends JpaRepository<Bill, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Bill entity);

    List<Bill> findByActive(Boolean active );
}
