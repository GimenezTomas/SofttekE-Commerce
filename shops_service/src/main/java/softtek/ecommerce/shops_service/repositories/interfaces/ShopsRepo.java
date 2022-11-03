package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "select s.id_shop from shops s inner join posts p on s.id_shop = p.id_shop where p.id_post = :id and s.active = 1 limit 1", nativeQuery = true)
    String findByIdPost( @Param("id") String idPost );

    @Query(value = "select s.id_shop from shops s inner join payment_methods p on s.id_shop = p.id_shop where p.id_payment_method = :id and s.active = 1 limit 1", nativeQuery = true)
    String findByIdPaymentMethod( @Param("id") String idPaymentMethod );
}
