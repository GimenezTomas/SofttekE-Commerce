package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table( name = "payment_methods" )
public class PaymentMethod {
    @Id
    @Column( name = "id_payment_method" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idPaymentMethod;

    @NotBlank @NotNull
    @Column( name = "name" )
    private String name;

    @Column( name = "active" )
    private Boolean active;

    @ManyToOne
    @JoinColumn( name = "id_shop", nullable = false )
    private Shop shop;

    PaymentMethod(){
        this.active = true;
    }

    public PaymentMethod( String name ){
        this();
        this.name = name;
    }
}

