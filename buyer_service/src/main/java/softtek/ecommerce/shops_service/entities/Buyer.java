package softtek.ecommerce.shops_service.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "buyers" )
public class Buyer {
    @Id
    @Column( name = "id_buyer" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String idBuyer;

    @Column( name = "name" ) @NotBlank
    private String name;

    @Column( name = "lastname" ) @NotBlank
    private String lastname;

    @Column( name = "email" ) @NotBlank
    private String email;

    @Column( name = "identification_number" ) @NotBlank
    private String identificationNumber;

    @Column( name = "active" )
    private Boolean active;

    @JsonIgnore
    @OneToMany( mappedBy = "buyer" )
    private Set<Order> orders;

    public Buyer(){
        this.active = true;
    }

    public Buyer(String name, String lastname, String email, String identificationNumber) {
        this();
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.identificationNumber = identificationNumber;
    }
}
