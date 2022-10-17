package softtek.ecommerce.users_service.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table( name = "users" )
public class User {
    @Id
    @Column( name = "id_user" ) @GeneratedValue(generator = "UUID")
    @GenericGenerator( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator" )
    private String id_user;

    @Column( name = "email" )
    private String email;

    @Column( name = "password" )
    private String password;

    @Column( name = "created_at", columnDefinition = "DATE" )
    private LocalDate createdAt;

    @Column( name = "updated_at", columnDefinition = "DATE" )
    private LocalDate updatedAt;

    @Column( name = "active" )
    private Boolean active;

    @ManyToOne
    @JoinColumn( name = "id_role", nullable = false )
    private Role role;

    public User(){
        this.createdAt = LocalDate.now();
        this.active = true;
    }

    public User( String email, String password ){
        this();
        this.email = email;
        this.password = password;
    }
}
