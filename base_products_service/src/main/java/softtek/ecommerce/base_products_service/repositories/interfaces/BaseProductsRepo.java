package softtek.ecommerce.base_products_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.base_products_service.entities.BaseProduct;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource( path = "base_products" )
public interface BaseProductsRepo extends JpaRepository<BaseProduct, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(BaseProduct entity);

    BaseProduct findByNameAndActive( String name, Boolean active );

    List<BaseProduct> findByActive( Boolean active );

    @Query(value = "select 'true' from base_products_types_has_areas b where b.types_has_areas_id_customization_type = :idType  and b.types_has_areas_id_customization_area = :idArea limit 1", nativeQuery = true)
    Boolean findByTypeHasArea( @Param("idType") String idType, @Param("idArea") String idArea );
}
