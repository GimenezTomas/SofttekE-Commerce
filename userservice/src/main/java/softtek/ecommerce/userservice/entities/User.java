package softtek.ecommerce.userservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table( name = "users" )
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id_user;
    @Column( name = "email" )
    private String email;
    @Column( name = "password" )
    private String password;
    @Column( name = "status" )
    private boolean status;
    @Column( name = "created_at", columnDefinition = "DATE" )
    private LocalDate created_at;
    @Column( name = "updated_at", columnDefinition = "DATE" )
    private LocalDate updated_at;
    @ManyToOne
    @JoinColumn( name = "id_role" )
    private Role role;

    public User(String email, String password, Role role) {
        super();

        this.email = email;
        this.password = password;
        this.role = role;
        this.status = true;
    }
}
