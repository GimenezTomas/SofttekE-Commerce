package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.PaymentMethod;
import softtek.ecommerce.shops_service.entities.Shop;

import java.util.Optional;

@RepositoryRestResource( path = "ShopsRepo" )
public interface ShopsRepo extends JpaRepository<Shop, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Shop entity);

    Shop findByName( String name );

    Optional<Shop> findByIdUser(String idUser );
}
