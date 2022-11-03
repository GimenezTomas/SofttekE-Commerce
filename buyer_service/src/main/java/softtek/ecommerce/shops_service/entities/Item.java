package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table( name = "items" )
public class Item {
    @Id
    @Column( name = "id_item" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idItem;

    @Column( name = "id_post" ) @NotBlank
    private String idPost;

    @Column( name = "quantity" ) @NotNull @Min(1)
    private Integer quantity;

    @Column( name = "active" )
    private Boolean active;

    @ManyToOne
    @JsonIgnore
    @JoinColumn( name = "id_shopping_cart" )
    private ShoppingCart shoppingCart;

    public Item() {
        this.active = true;
    }

    public Item(String idPost, Integer quantity) {
        this();
        this.idPost = idPost;
        this.quantity = quantity;
    }
}
