package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.AreaHasCustomization;
import softtek.ecommerce.shops_service.entities.AreaHasCustomizationId;
import softtek.ecommerce.shops_service.entities.PaymentMethod;

import java.io.Serializable;

@RepositoryRestResource( path = "areas_has_customizations" )
public interface AreasHasCustomizationsRepo extends JpaRepository<AreaHasCustomization, AreaHasCustomizationId> {

    @Override @RestResource( exported = false )
    void delete(AreaHasCustomization entity);
}
