package softtek.ecommerce.userservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table( name = "permissions" )
public class Permission {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id_permission;

    @Column( name  = "name" )
    @NotBlank
    private String name;

    @NotBlank
    @Column( name = "status" )
    private boolean status;

    @NotBlank
    @Column( name = "created_at", columnDefinition = "DATE" )
    private LocalDate created_at;

    @NotBlank
    @Column( name = "updated_at", columnDefinition = "DATE" )
    private LocalDate updated_at;

    @ManyToMany( mappedBy = "permissions" )
    private Set<Role> roles;

    public Permission(String name) {
        super();

        this.status = true;
        this.name = name;
        this.roles = new HashSet<>();
    }

    public Permission(){
        super();

        this.status = true;
        this.roles = new HashSet<>();
    }
}
