package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "customizations" )
public class Customization {
    @Id
    @Column( name = "id_customization" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_customization;

    @Column( name = "name" ) @NotBlank
    private String name;

    @Column( name = "price" ) @NotNull
    private float price;

    @Column( name = "description" ) @NotBlank
    private String description;

    @Column( name = "id_customization_type" ) @NotBlank
    private String id_customization_type;

    @Column( name = "created_at", columnDefinition = "DATE") @NotBlank
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

    @OneToMany( mappedBy = "customization", fetch = FetchType.LAZY)
    private Set<AreaHasCustomization> areas;

    @OneToMany( mappedBy = "customization", fetch = FetchType.LAZY)
    private Set<CustomizatedProduct> customizatedProducts;

    Customization(){
        super();
        this.active = true;
        this.createdAt = LocalDate.now();
        this.customizatedProducts = new HashSet<>();
    }

    Customization( String name, String description, String id_customization_type, float price ){
        super();
        this.customizatedProducts = new HashSet<>();
        this.active = true;
        this.createdAt = LocalDate.now();
        this.name = name;
        this.description = description;
        this.id_customization_type = id_customization_type;
        this.price = price;
    }
}
