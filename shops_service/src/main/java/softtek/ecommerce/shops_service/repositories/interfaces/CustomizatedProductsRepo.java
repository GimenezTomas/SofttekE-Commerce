package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.CustomizatedProduct;

@RepositoryRestResource( path = "customizated_products" )
public interface CustomizatedProductsRepo extends JpaRepository<CustomizatedProduct, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(CustomizatedProduct entity);

    CustomizatedProduct findByName( String name );
}
