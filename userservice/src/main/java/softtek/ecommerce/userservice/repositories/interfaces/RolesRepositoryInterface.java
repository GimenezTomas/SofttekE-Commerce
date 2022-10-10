package softtek.ecommerce.userservice.repositories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import softtek.ecommerce.userservice.entities.Permission;
import softtek.ecommerce.userservice.entities.Role;

import java.util.Set;

public interface RolesRepositoryInterface extends JpaRepository<Role, String> {
    void saveRole( Role role );

    Role findByName( String name );

    Set<Role> findBypermission( Permission permission);

    void deleteRole( Role role );

    @Override
    //@RestResource(extpo)
    void deleteById(String id);
}
