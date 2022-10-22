package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table( name = "areas_has_customizations" )
public class AreaHasCustomization {
    @EmbeddedId
    private AreaHasCustomizationId areaHasCustomizationId;

    @ManyToOne
    @JoinColumn( name = "id_customization" )
    @MapsId("idCustomization")
    private Customization customization;

    AreaHasCustomization(){}

    public AreaHasCustomization(Customization customization, AreaHasCustomizationId areaHasCustomizationId){
        this.areaHasCustomizationId = areaHasCustomizationId;
        this.customization = customization;
    }
}
