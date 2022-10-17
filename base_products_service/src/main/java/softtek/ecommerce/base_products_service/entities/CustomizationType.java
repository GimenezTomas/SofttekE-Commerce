package softtek.ecommerce.base_products_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table( name = "customization_types" )
@Getter
@Setter
public class CustomizationType {
    @Id @Column( name = "id_customization_type" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_customization_type;

    @Column( name = "name" ) @NotEmpty( message = "The above field must not be blank" )
    private String name;

    @Column( name = "active" )
    private Boolean active;

    @OneToMany( mappedBy = "customizationArea" )
    Set<TypeHasArea> typesHasAreas;

    public CustomizationType( String name ){
        this();
        this.name = name;
    }

    public CustomizationType(){
        this.active = true;
    }
}
