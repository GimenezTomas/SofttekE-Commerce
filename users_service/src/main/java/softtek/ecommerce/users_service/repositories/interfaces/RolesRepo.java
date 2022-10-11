package softtek.ecommerce.users_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.users_service.entities.Role;

@RepositoryRestResource( path = "roles" )
public interface RolesRepo extends JpaRepository<Role, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Role entity);

    Role findByName( String name );
}
