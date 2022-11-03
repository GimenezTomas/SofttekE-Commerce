package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table( name = "customizated_products" )
public class CustomizatedProduct {
    @Id
    @Column( name = "id_customizated_product" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idCustomizatedProduct;

    @NotBlank @NotNull
    @Column( name = "name" )
    private String name;

    @NotBlank @NotNull
    @Column( name = "id_base_product" )
    private String idBaseProduct;

    @Column( name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

/*    @ManyToOne
    @JoinColumn( name = "id_customization", nullable = false )
    private Customization customization;
*/
    @ManyToMany
    private Set<AreaHasCustomization> areaHasCustomizations;


    @ManyToOne
    @JoinColumn( name = "id_shop", nullable = false )
    private Shop shop;

    @JsonIgnore
    @OneToMany( mappedBy = "customizatedProduct", fetch = FetchType.LAZY )
    private Set<Post> posts;

    CustomizatedProduct(){
        this.active = true;
        this.createdAt = LocalDate.now();
        this.posts = new HashSet<>();
        this.areaHasCustomizations = new HashSet<>();
    }

    public CustomizatedProduct( String name, String id_base_product ){
        this();
        this.name = name;
        this.idBaseProduct = id_base_product;
    }
}
