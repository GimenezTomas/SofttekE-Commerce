package softtek.ecommerce.users_service.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import softtek.ecommerce.users_service.entities.Permission;

@RepositoryRestResource( path = "permissions" )
public interface PermissionsRepo extends JpaRepository<Permission, String> {
    @Override @RestResource( exported = false )
    void deleteById(String id);

    @Override @RestResource( exported = false )
    void delete(Permission entity);

    Permission findByName( String name );
}
