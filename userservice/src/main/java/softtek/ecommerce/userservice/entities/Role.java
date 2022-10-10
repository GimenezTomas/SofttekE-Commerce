package softtek.ecommerce.userservice.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.security.Permissions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "roles" )
@Setter
@Getter
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id_role;
    @Column( name = "name" )
    private String name;
    @Column( name = "status" )
    private boolean status;
    @Column( name = "created_at", columnDefinition = "DATE" )
    private LocalDate created_at;
    @Column( name = "updated_at", columnDefinition = "DATE" )
    private LocalDate updated_at;
    @ManyToMany()
    @JoinTable(
            name = "roles_has_permissions",
            joinColumns = @JoinColumn( name = "id_role"),
            inverseJoinColumns = @JoinColumn( name = "id_permission" )
    )
    private Set<Permissions> permissions;

    public Role(String name) {
        this.name = name;
        this.permissions = new HashSet<>();
        this.status = true;
    }

    public Role(){
        this.status = true;
        this.permissions = new HashSet<>();
    }
}
