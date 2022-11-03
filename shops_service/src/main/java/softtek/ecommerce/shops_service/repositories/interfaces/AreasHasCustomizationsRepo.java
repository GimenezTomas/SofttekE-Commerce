package softtek.ecommerce.shops_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.shops_service.entities.AreaHasCustomization;
import softtek.ecommerce.shops_service.entities.AreaHasCustomizationId;
import softtek.ecommerce.shops_service.entities.PaymentMethod;

import java.io.Serializable;
import java.util.Optional;

@RepositoryRestResource( path = "areas_has_customizations" )
public interface AreasHasCustomizationsRepo extends JpaRepository<AreaHasCustomization, AreaHasCustomizationId> {

    @Override @RestResource( exported = false )
    void delete(AreaHasCustomization entity);

    Optional<AreaHasCustomization> findByAreaHasCustomizationId(AreaHasCustomizationId areaHasCustomizationId);

    @Query( value = "select a.* from areas_has_customizations a inner join customizations c on a.id_customization = c.id_customization where a.id_customization_area = :idArea and c.id_customization_type = :idType", nativeQuery = true )
    Optional<AreaHasCustomization> findByTHA(@Param("idArea") String idArea, @Param("idType") String idType);

    @Query( value = "select a.* from areas_has_customizations where a.id_customization = :idCustomization limit 1", nativeQuery = true )
    Optional<AreaHasCustomization> findByIdCustomization( @Param("idCustomization") String idCustomization);
}
