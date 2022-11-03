package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import softtek.ecommerce.shops_service.exceptions.ItemException;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "shopping_carts" )
public class ShoppingCart {
    @Id
    @Column( name = "id_shopping_cart" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idShoppingCart;

    @Column( name = "active" )
    private Boolean active;

    @OneToMany( mappedBy = "shoppingCart" )
    private Set<Item> items;

    @JsonIgnore
    @OneToOne( mappedBy = "shoppingCart" )
    private Order order;

    public ShoppingCart() {
        this.active = true;
    }

    public boolean addItem( Item item ) {
        for ( Item item1 : items ) {
            if ( item1.getIdPost().equals(item.getIdPost()) ) {
                item1.setQuantity(item1.getQuantity() + item.getQuantity());
                return true;
            }
        }
        return false;
    }
}
