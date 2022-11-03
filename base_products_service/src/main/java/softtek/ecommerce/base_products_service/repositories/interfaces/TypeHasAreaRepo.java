package softtek.ecommerce.base_products_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;
import softtek.ecommerce.base_products_service.entities.TypeHasAreaKey;

import java.util.Optional;
import java.util.Set;

@RepositoryRestResource( path = "type_has_area" )
public interface TypeHasAreaRepo extends JpaRepository<TypeHasArea, String> {
    @Override @RestResource( exported = false )
    void delete(TypeHasArea entity);

    Optional<TypeHasArea> findByIdTypeHasArea( TypeHasAreaKey typeHasAreaKey );

    @Query(value = "select * from type_has_area tha where tha.id_customization_area = :idArea and active = 1", nativeQuery = true)
    Optional< Set<TypeHasArea> > findByIdAreaAndActive( @Param("idArea") String idCustomizatedProduct );

    @Query(value = "select * from type_has_area tha where tha.id_customization_type = :idType and active = 1", nativeQuery = true)
    Optional< Set<TypeHasArea> > findByIdTypeAndActive( @Param("idType") String idCustomizatedProduct );
}
