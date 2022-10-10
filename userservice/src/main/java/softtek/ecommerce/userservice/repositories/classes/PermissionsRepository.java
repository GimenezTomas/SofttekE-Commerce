package softtek.ecommerce.userservice.repositories.classes;

import org.springframework.stereotype.Repository;
import softtek.ecommerce.userservice.entities.Permission;
import softtek.ecommerce.userservice.entities.Role;

import java.util.ArrayList;

@Repository
public class PermissionsRepository {
    private ArrayList<Permission> permissions;

    public PermissionsRepository() {
        super();

        this.permissions = new ArrayList<>();
    }

    public void savePermission( Permission permission ){
        this.permissions.add( permission );
    }
}
