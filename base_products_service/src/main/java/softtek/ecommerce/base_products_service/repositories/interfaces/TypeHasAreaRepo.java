package softtek.ecommerce.base_products_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;

@RepositoryRestResource( path = "type_has_area" )
public interface TypeHasAreaRepo extends JpaRepository<TypeHasArea, String> {
    @Override @RestResource( exported = false )
    void delete(TypeHasArea entity);

}
