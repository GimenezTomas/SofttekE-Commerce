package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "shops" )
public class Shop {
    @Id
    @Column( name = "id_shop" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_shop;

    @Column( name = "name" ) @NotBlank
    private String name;

    @Column( name = "description" ) @NotBlank
    private String description;

    @Column( name = "id_user" ) @NotBlank
    private String id_user;

    @Column( name = "created_at", columnDefinition = "DATE") @NotBlank
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE") @NotBlank
    private LocalDate udatedAt;

    @OneToMany( mappedBy = "shop", fetch = FetchType.LAZY)
    private Set<PaymentMethod> paymentMethods;

    Shop(){}

    Shop( String name, String description, String id_user ){
        this.createdAt = LocalDate.now();
        this.name = name;
        this.description = description;
        this.id_user = id_user;
    }
}
