package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.PaymentMethod;

@RepositoryRestResource( path = "payment_methods" )
public interface PaymentMethodsRepo extends JpaRepository<PaymentMethod, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(PaymentMethod entity);

    PaymentMethod findByName( String name );
}
