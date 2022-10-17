package softtek.ecommerce.users_service.entities;

import com.fasterxml.jackson.annotation.*;
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

    @JsonIgnore
    @OneToMany( mappedBy = "role", fetch = FetchType.LAZY)
    private Set<User> users;

    @ManyToMany
    private Set<Permission> permissions;

    public Role() {
        this.users = new HashSet<>();
        this.permissions = new HashSet<>();
    }

    public Role( String name ){
        this();
        this.name = name;
    }

    public void addPermission( Permission permission ){
        this.permissions.add(permission);
    }

    public void removePermission( Permission permissionForRemove ){
        this.permissions.stream().filter( permission -> permission.getId_permission().equals(permissionForRemove.getId_permission()) );
    }

}
