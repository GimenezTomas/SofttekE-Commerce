package softtek.ecommerce.base_products_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table( name = "customization_areas" )
@Getter
@Setter
public class CustomizationArea {
    @Id @Column( name = "id_customization_area" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_customization_area;

    @NotEmpty( message = "The above field must not be blank" ) @Column( name = "name" )
    private String name;

    @Column( name = "active" )
    private Boolean active;

    @OneToMany( mappedBy = "customizationType" )
    Set<TypeHasArea> typesHasAreas;

    public CustomizationArea(){
        this.active = true;
    }

    public CustomizationArea( String name ){
        this();
        this.name = name;
    }
}
