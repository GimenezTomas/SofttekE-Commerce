package softtek.ecommerce.base_products_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table( name = "base_products" )
public class BaseProduct {
    @Id
    @Column( name = "id_base_product" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_base_product;

    @Column( name = "name" ) @NotEmpty( message = "The above field must not be blank" )
    private String name;

    @Column( name = "description" ) @NotEmpty( message = "The above field must not be blank" )
    private String description;

    @Column( name = "price" ) @NotNull
    private float price;

    @Column( name = "days_fabrication_time" ) @NotNull
    private int days_fabrication_time;

    @Column( name = "active" )
    private boolean active;

    @Column( name = "created_at", columnDefinition = "DATETIME" )
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATETIME" )
    private LocalDate updatedAt;

    public BaseProduct(){
        super();
        this.active = true;
        this.createdAt = LocalDate.now();
    }
}
