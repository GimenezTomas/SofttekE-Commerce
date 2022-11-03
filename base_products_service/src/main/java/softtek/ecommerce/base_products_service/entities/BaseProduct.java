package softtek.ecommerce.base_products_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import softtek.ecommerce.base_products_service.exceptions.TypeHasAreaException;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "base_products" )
public class BaseProduct {
    @Id
    @Column( name = "id_base_product" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idBaseProduct;

    @Column( name = "name" ) @NotEmpty( message = "The above field must not be blank" )
    private String name;

    @Column( name = "description" ) @NotEmpty( message = "The above field must not be blank" )
    private String description;

    @Column( name = "price" ) @NotNull
    private float price;

    @Column( name = "days_fabrication_time" ) @NotNull
    private int daysFabricationTime;

    @Column( name = "active" )
    private boolean active;

    @Column( name = "created_at", columnDefinition = "DATETIME" )
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATETIME" )
    private LocalDate updatedAt;

    @ManyToMany
    private Set<TypeHasArea> typesHasAreas;

    public Set<TypeHasArea> getTypesHasAreas() {
        return new HashSet<>(typesHasAreas);
    }

    public void addTypeHasArea( TypeHasArea typeHasArea ) throws TypeHasAreaException {
        if( this.typesHasAreas.stream().noneMatch( typeHasArea1 -> typeHasArea1.getIdTypeHasArea().getIdCustomizationType().equals(typeHasArea.getIdTypeHasArea().getIdCustomizationType()) && typeHasArea1.getIdTypeHasArea().getIdCustomizationArea().equals(typeHasArea.getIdTypeHasArea().getIdCustomizationArea()) ))
            this.typesHasAreas.add(typeHasArea);
        else
            throw new TypeHasAreaException(" dulicated", "TYPE HAS AREA KEY");
    }

    public void removeTypeHasArea( TypeHasArea typeHasArea ){
        this.typesHasAreas.removeIf( typeHasArea1 -> typeHasArea1.getIdTypeHasArea().getIdCustomizationType().equals(typeHasArea.getIdTypeHasArea().getIdCustomizationType()) && typeHasArea1.getIdTypeHasArea().getIdCustomizationArea().equals(typeHasArea.getIdTypeHasArea().getIdCustomizationArea()));
    }

    public BaseProduct(){
        this.active = true;
        this.createdAt = LocalDate.now();
        this.typesHasAreas = new HashSet<>();
    }

    public BaseProduct(String name, String description, float price, int days_fabrication_time) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.daysFabricationTime = days_fabrication_time;
    }
}
