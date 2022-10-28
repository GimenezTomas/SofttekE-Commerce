package softtek.ecommerce.users_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.entities.User;
import softtek.ecommerce.users_service.entities.dtos.DTOUser;
import softtek.ecommerce.users_service.exceptions.PermissionInvalidException;
import softtek.ecommerce.users_service.exceptions.RoleInvalidException;
import softtek.ecommerce.users_service.exceptions.UserInvalidException;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;

import java.util.Optional;

@Service
public class RoleValidationService {
    @Autowired
    UsersRepo usersRepo;

    @Autowired
    RolesRepo rolesRepo;

    public Boolean validation( String idCurrentUser, String permissionName ) throws Exception{
        Optional<User> currentUserOptional = usersRepo.findById( idCurrentUser );

        if ( currentUserOptional.isPresent() ) {
            Optional<Role> role = rolesRepo.findById(currentUserOptional.get().getRole().getId_role());

            if (role.get().getPermissions().stream().anyMatch(permission -> permission.getName().equals(permissionName)) )
                    return true;

            throw new PermissionInvalidException("Role " + role.get().getName() + " has not permission", permissionName );
        }else{
            throw new UserInvalidException("User does not exists", idCurrentUser);
        }
    }
}
