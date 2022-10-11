package softtek.ecommerce.base_products_service.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class TypeHasAreaKey implements Serializable {
    @Column( name = "id_customization_type" )
    private String idCustomizationType;

    @Column( name = "id_customization_area" )
    private String idCustomizationArea;

    TypeHasAreaKey(){}

    TypeHasAreaKey( String idCustomizationArea, String idCustomizationType ){
        super();
        this.idCustomizationArea = idCustomizationArea;
        this.idCustomizationType = idCustomizationType;
    }
}
