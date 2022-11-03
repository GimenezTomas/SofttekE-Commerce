package softtek.ecommerce.shops_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table( name = "orders" )
public class Order {
    @Id
    @Column( name = "id_order" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idOrder;

    @Column( name = "id_payment_method" ) @NotBlank
    private String idPaymentMethod;

    @Column( name = "active" )
    private Boolean active;

    @Column( name = "created_at", columnDefinition = "DATE")
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    private State state;

    @ManyToOne
    @JoinColumn( name = "id_buyer" )
    private Buyer buyer;

    @OneToOne
    @JoinColumn( name = "id_shopping_cart" )
    private ShoppingCart shoppingCart;

    @OneToOne
    @JoinColumn( name = "id_bill" )
    private Bill bill;

    public Order(){
        this.active = true;
        this.createdAt = LocalDate.now();
        this.state = State.GENERATED;
    }

    public Order(String idPaymentMethod) {
        this();
        this.idPaymentMethod = idPaymentMethod;
    }

    public void billRecived( boolean approved){
        if ( approved )
            this.state = State.APPROVED;
        else
            this.state = State.REJECTED;

        this.updatedAt = LocalDate.now();
    }
}
