package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Buyer;
import softtek.ecommerce.shops_service.entities.Order;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource( path = "buyers" )
public interface BuyersRepo extends JpaRepository<Buyer, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Buyer entity);

    List<Buyer> findByActive(Boolean active );

    Optional<Buyer> findByActiveAndIdentificationNumber(boolean active, String identifiactionNumber );

    Optional<Buyer> findByIdBuyerAndActive( String id, boolean active );
}
