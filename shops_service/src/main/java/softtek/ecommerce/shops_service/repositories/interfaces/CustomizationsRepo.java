package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.Customization;

import java.util.Optional;

@RepositoryRestResource( path = "customizations" )
public interface CustomizationsRepo extends JpaRepository<Customization, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Customization entity);

    Customization findByName( String name );

    Optional<Customization> findByIdCustomizationTypeAndActive(String idCustomizationType, Boolean active );
}
