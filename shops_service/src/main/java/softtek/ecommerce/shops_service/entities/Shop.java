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
@Table( name = "shops" )
public class Shop {
    @Id
    @Column( name = "id_shop" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idShop;

    @NotBlank @NotNull
    @Column( name = "name" )
    private String name;

    @NotBlank @NotNull
    @Column( name = "description" )
    private String description;

    @NotBlank @NotNull
    @Column( name = "id_user" )
    private String idUser;

    @Column( name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

    @JsonIgnore
    @OneToMany( mappedBy = "shop", fetch = FetchType.LAZY)
    private Set<PaymentMethod> paymentMethods;

    @JsonIgnore
    @OneToMany( mappedBy = "shop", fetch = FetchType.LAZY)
    private Set<CustomizatedProduct> customizatedProducts;

    @JsonIgnore
    @OneToMany( mappedBy = "shop", fetch = FetchType.LAZY)
    private Set<Post> posts;

    @JsonIgnore
    @OneToMany( mappedBy = "shop", fetch = FetchType.LAZY )
    private Set<Customization> customization;

    Shop(){
        this.active = true;
        this.createdAt = LocalDate.now();
        this.posts = new HashSet<>();
        this.paymentMethods = new HashSet<>();
        this.customizatedProducts = new HashSet<>();
        this.customization = new HashSet<>();
    }

    Shop( String name, String description, String id_user ){
        this();
        this.name = name;
        this.description = description;
        this.idUser = id_user;
    }
}
