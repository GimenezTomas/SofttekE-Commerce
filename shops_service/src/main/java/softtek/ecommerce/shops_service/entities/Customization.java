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

    @Column( name = "content" ) @NotBlank
    private String content;

    @Column( name = "id_customization_type" ) @NotBlank
    private String id_customization_type;

    @Column( name = "created_at", columnDefinition = "DATE")
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
        this.active = true;
        this.createdAt = LocalDate.now();
        this.customizatedProducts = new HashSet<>();
    }

    Customization( String name, String content, String id_customization_type, float price ){
        this();
        this.name = name;
        this.content = content;
        this.id_customization_type = id_customization_type;
        this.price = price;
    }
}
