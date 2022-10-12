package softtek.ecommerce.users_service.entities;

import com.fasterxml.jackson.annotation.*;
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

    Permission() {}

    Permission( String name ) {
        super();
        this.name = name;
    }
}
