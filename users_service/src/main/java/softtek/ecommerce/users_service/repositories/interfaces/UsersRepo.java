package softtek.ecommerce.users_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.users_service.entities.User;

@RepositoryRestResource( path = "users" )
public interface UsersRepo extends JpaRepository<User, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(User entity);

    User findByEmail( String name );
}
