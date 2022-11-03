package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.CustomizatedProduct;
import softtek.ecommerce.shops_service.entities.Post;

import java.util.Optional;

@RepositoryRestResource( path = "customizated_products" )
public interface CustomizatedProductsRepo extends JpaRepository<CustomizatedProduct, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(CustomizatedProduct entity);

    CustomizatedProduct findByName( String name );

    @Query(value = "select * from customizated_products c where c.id_customization = :id and active = 1 limit 1", nativeQuery = true)
    Optional<CustomizatedProduct> findByIdCustomizationAndActive( @Param("id") String idCustomization, @Param("id") Boolean active );
}
