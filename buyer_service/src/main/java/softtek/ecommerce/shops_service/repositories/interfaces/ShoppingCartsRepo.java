package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Item;
import softtek.ecommerce.shops_service.entities.ShoppingCart;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource( path = "shopping_carts" )
public interface ShoppingCartsRepo extends JpaRepository<ShoppingCart, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(ShoppingCart entity);

    List<ShoppingCart> findByActive(Boolean active );

    @Query( value = "select s.* from shopping_carts s inner join orders o on o.id_shopping_cart = s.id_shopping_cart where s.id_shopping_cart = :id", nativeQuery = true )
    Optional<ShoppingCart> findByOrder(@Param("id") String id);
}