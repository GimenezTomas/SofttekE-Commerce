package softtek.ecommerce.base_products_service.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table( name = "type_has_area" )
public class TypeHasArea {
    @EmbeddedId
    private TypeHasAreaKey idTypeHasArea;

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
        this.active = true;
    }

    public TypeHasArea( TypeHasAreaKey typeHasAreaKey, CustomizationType customizationType, CustomizationArea customizationArea ){
        this();
        this.idTypeHasArea = typeHasAreaKey;
        this.customizationType = customizationType;
        this.customizationArea = customizationArea;
    }
}
