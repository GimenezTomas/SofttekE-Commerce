package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table( name = "areas_has_customizations" )
@IdClass(AreaHasCustomization.class)
public class AreaHasCustomization {
    @Id
    @Column( name = "id_customization_area" )
    private String id_customization_area;

    @ManyToOne
    @Id
    @JoinColumn( name = "id_customization" )
    private Customization customization;

    AreaHasCustomization(){}

    AreaHasCustomization(Customization customization, String id_customization_area ){
        this.id_customization_area = id_customization_area;
        this.customization = customization;
    }
}
