package softtek.ecommerce.base_products_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.base_products_service.entities.BaseProduct;

import java.util.List;

@RepositoryRestResource( path = "base_products" )
public interface BaseProductsRepo extends JpaRepository<BaseProduct, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(BaseProduct entity);

    BaseProduct findByNameAndActive( String name, Boolean active );

    List<BaseProduct> findByActive( Boolean active );
}
