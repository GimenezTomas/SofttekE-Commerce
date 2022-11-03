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
@Table( name = "posts" )
public class Post {
    @Id
    @Column( name = "id_post" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_post;

    private State state;

    @NotBlank @NotNull
    @Column( name = "description" )
    private String description;

    @Column( name = "created_at", columnDefinition = "DATE")
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
        this.state = State.ACTIVE;
    }

    public Post(String description){
        this();
        this.description = description;
    }


}
