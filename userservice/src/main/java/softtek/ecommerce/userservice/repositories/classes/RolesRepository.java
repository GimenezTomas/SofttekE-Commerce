package softtek.ecommerce.userservice.repositories.classes;

import org.springframework.stereotype.Repository;
import softtek.ecommerce.userservice.entities.Permission;
import softtek.ecommerce.userservice.entities.Role;
import softtek.ecommerce.userservice.repositories.interfaces.RolesRepositoryInterface;

import java.util.ArrayList;
import java.util.Set;

@Repository // no se si tiene q ser abstract
public abstract class RolesRepository implements RolesRepositoryInterface {

    private ArrayList<Role> roles;

    public RolesRepository() {
        super();
        this.roles = new ArrayList<>();
    }

    public void saveRole( Role role){
        this.roles.add( role );
    }

    public Role findByName( String name ){
        return new Role(name);
    }

    @Override
    public Set<Role> findBypermission(Permission permission) {
        //TODO
        return null;
    }

    @Override
    public void deleteRole(Role role) {
        //TODO
    }
}
