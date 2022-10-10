package softtek.ecommerce.base_products_service.entities;

import javax.persistence.*;

@Entity
@Table( name = "type_has_area" )
public class TypeHasArea {
    @EmbeddedId
    private TypeHasAreaKey id;

    @ManyToOne
    @MapsId("idCustomizationType")
    @JoinColumn( name = "id_customization_type" )
    private CustomizationType customizationType;

    @ManyToOne
    @MapsId("idCustomizationArea")
    @JoinColumn( name = "id_customization_area" )
    private CustomizationArea customizationArea;

    @Column( name = "active" )
    private boolean active;

    TypeHasArea(){
        super();
        this.active = true;
    }
}
