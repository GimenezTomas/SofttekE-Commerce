package softtek.ecommerce.users_service.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.HttpStatus;
import softtek.ecommerce.users_service.exceptions.PermissionInvalidException;
import softtek.ecommerce.users_service.exceptions.UserInvalidException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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

    public Set<User> getUsers() {
        return new HashSet<>( users );
    }

    public Set<Permission> getPermissions() {
        return new HashSet<>( permissions );
    }

    public void addPermission( Permission permission ) throws PermissionInvalidException {
        if ( this.permissions.stream().noneMatch( permission1 -> permission1.getId_permission().equals(permission.getId_permission()) ))
            this.permissions.add(permission);
        else
            throw new PermissionInvalidException( " is already inside the role", permission.getName() );
    }

    public void removePermission( Permission permissionForRemove ) throws PermissionInvalidException {
        this.permissions.removeIf( permission -> permission.getId_permission().equals(permissionForRemove.getId_permission()));
    }
}
