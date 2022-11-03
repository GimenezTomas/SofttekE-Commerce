package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Item;
import softtek.ecommerce.shops_service.entities.Order;
import softtek.ecommerce.shops_service.entities.ShoppingCart;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource( path = "orders" )
public interface OrdersRepo extends JpaRepository<Order, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Order entity);

    List<Order> findByActive(Boolean active );

    @Query(value = "select * from orders where id_buyer = :idBuyer and active = :active limit 1", nativeQuery = true)
    Optional<Order> findByActiveAndIdBuyer(@Param("active") Boolean active, @Param( "idBuyer" ) String idBuyer );

    @Query(value = "select * from orders where id_bill = :idBill and active = :active limit 1", nativeQuery = true)
    Optional<Order> findByActiveAndIdBill(@Param("active") Boolean active, @Param( "idBill" ) String idBill );

    @Query(value = "select * from orders where id_payment_method = :idPaymentMethod and active = :active limit 1", nativeQuery = true)
    Optional<Order> findOneByidPaymentMethodAndActive( @Param( "idPaymentMethod" ) String idPaymentMethod, @Param( "active" ) Boolean active);
}

