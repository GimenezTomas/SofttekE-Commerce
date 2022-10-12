package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table( name = "payment_methods" )
public class PaymentMethod {
    @Id
    @Column( name = "id_payment_method" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_payment_method;

    @Column( name = "name" ) @NotBlank
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn( name = "id_shop", nullable = false )
    private Shop shop;

    PaymentMethod(){}

    PaymentMethod( String name, Shop shop ){
        super();
        this.name = name;
        this.shop = shop;
    }
}

