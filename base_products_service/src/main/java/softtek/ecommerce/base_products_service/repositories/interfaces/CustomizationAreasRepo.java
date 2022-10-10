package softtek.ecommerce.base_products_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.base_products_service.entities.CustomizationArea;

@RepositoryRestResource( path = "customization_areas" )
public interface CustomizationAreasRepo extends JpaRepository<CustomizationArea, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(CustomizationArea entity);

    CustomizationArea findByNameAndActive( String name, Boolean active );
}
