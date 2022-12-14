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
@Table( name = "customizations" )
public class Customization {
    @Id
    @Column( name = "id_customization" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idCustomization;

    @NotBlank @NotNull
    @Column( name = "name" )
    private String name;

    @NotNull
    @Column( name = "price" )
    private float price;

    @NotBlank @NotNull
    @Column( name = "content" )
    private String content;

    @NotBlank @NotNull
    @Column( name = "id_customization_type" )
    private String idCustomizationType;

    @Column( name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

    @JsonIgnore
    @OneToMany( mappedBy = "customization", fetch = FetchType.LAZY)
    private Set<AreaHasCustomization> areas;
/*
    @JsonIgnore
    @OneToMany( mappedBy = "customization", fetch = FetchType.LAZY)
    private Set<CustomizatedProduct> customizatedProducts;*/

    @ManyToOne
    @JoinColumn(name = "id_shop")
    private Shop shop;

    Customization(){
        this.active = true;
        this.createdAt = LocalDate.now();
        //this.customizatedProducts = new HashSet<>();
    }

    public Customization(String name, String content, String id_customization_type, float price){
        this();
        this.name = name;
        this.content = content;
        this.idCustomizationType = id_customization_type;
        this.price = price;
    }
}
