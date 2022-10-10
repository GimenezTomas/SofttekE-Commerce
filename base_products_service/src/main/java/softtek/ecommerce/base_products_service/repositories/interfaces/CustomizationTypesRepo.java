package softtek.ecommerce.base_products_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.base_products_service.entities.CustomizationArea;
import softtek.ecommerce.base_products_service.entities.CustomizationType;

@RepositoryRestResource( path = "customization_types" )
public interface CustomizationTypesRepo extends JpaRepository<CustomizationType, String> {

    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(CustomizationType entity);

    CustomizationType findByNameAndActive( String name, Boolean active );

    CustomizationType findByActive( Boolean active );
}
