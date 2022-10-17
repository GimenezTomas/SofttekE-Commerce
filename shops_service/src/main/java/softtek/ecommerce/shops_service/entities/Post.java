package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table( name = "posts" )
public class Post {
    @Id
    @Column( name = "id_post" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_post;

    @Column( name = "state" ) @NotBlank//enumerado?
    private String state;

    @Column( name = "description" ) @NotBlank
    private String description;

    @Column( name = "created_at", columnDefinition = "DATE") @NotBlank
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE")
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

    @ManyToOne
    @JoinColumn( name = "id_shop", nullable = false )
    private Shop shop;

    @ManyToOne
    @JoinColumn( name = "id_customizated_product", nullable = false )
    private CustomizatedProduct customizatedProduct;

    Post(){
        this.active = true;
        this.createdAt = LocalDate.now();
    }

    Post( String state, String description ){
        this();
        this.state = state;
        this.description = description;
    }


}
