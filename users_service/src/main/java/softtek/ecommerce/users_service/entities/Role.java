package softtek.ecommerce.users_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Table( name = "roles" )
@Entity
public class Role {
    @Id
    @Column( name = "id_role" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_role;

    @Column( name = "name" ) @NotBlank
    private String name;

    @OneToMany( mappedBy = "role" )
    private Set<User> users;

    @ManyToMany( mappedBy = "roles" )
    private Set<Permission> permissions;

    public Role() {
        this.users = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public Role( String name ){
        super();
        this.name = name;
        this.users = new HashSet<>();
    }
}
