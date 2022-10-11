package softtek.ecommerce.users_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table( name = "permissions" )
public class Permission {
    @Id
    @Column( name = "id_permission" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_permission;

    @Column( name = "name" ) @NotBlank
    private String name;

    @ManyToMany
    @JoinTable(
            name = "roles_has_permissions",
            joinColumns = @JoinColumn( name = "id_permission" ),
            inverseJoinColumns = @JoinColumn( name = "id_role" )
    )
    private Set<Role> roles;

    Permission() {
        this.roles = new HashSet<>();
    }

    Permission( String name ) {
        super();
        this.name = name;
        this.roles = new HashSet<>();
    }
}
