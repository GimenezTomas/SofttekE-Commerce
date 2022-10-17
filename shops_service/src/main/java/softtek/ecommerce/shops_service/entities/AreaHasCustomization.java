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
    @Id
    @Column( name = "id_customization_area" )
    private String idCustomizationArea;

    @ManyToOne
    @MapsId
    @JoinColumn( name = "id_customization" )
    private Customization customization;

    AreaHasCustomization(){}

    AreaHasCustomization(Customization customization, String idCustomizationArea  ){
        this.idCustomizationArea = idCustomizationArea;
        this.customization = customization;
    }
}
