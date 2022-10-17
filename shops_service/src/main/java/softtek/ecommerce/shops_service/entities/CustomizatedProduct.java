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
@Table( name = "customizated_products" )
public class CustomizatedProduct {
    @Id
    @Column( name = "id_customizated_product" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_customizated_product;

    @Column( name = "name" ) @NotBlank
    private String name;

    @Column( name = "id_base_product" ) @NotBlank
    private String id_base_product;

    @Column( name = "created_at", columnDefinition = "DATE") @NotBlank
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

    @ManyToOne
    @JoinColumn( name = "id_customization", nullable = false )
    private Customization customization;

    @ManyToOne
    @JoinColumn( name = "id_shop", nullable = false )
    private Shop shop;

    @OneToMany( mappedBy = "customizatedProduct", fetch = FetchType.LAZY )
    private Set<Post> posts;

    CustomizatedProduct(){
        this.active = true;
        this.createdAt = LocalDate.now();
        this.posts = new HashSet<>();
    }

    CustomizatedProduct( String name, String description, String id_base_product ){
        this();
        this.name = name;
        this.id_base_product = id_base_product;
    }
}
