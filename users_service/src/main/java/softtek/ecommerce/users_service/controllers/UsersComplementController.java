package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.entities.User;
import softtek.ecommerce.users_service.repositories.interfaces.PermissionsRepo;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;

import javax.validation.Valid;
import java.util.Optional;

@RepositoryRestController
public class UsersComplementController {
    @Autowired
    RolesRepo rolesRepo;

    @Autowired
    UsersRepo usersRepo;

    /*@PostMapping("users")
    @ResponseBody ResponseEntity<Object> createUser(@RequestBody @Valid User user ) throws Exception{
        if ( this.usersRepo.findByEmail(user.getEmail()) != null )
            return new ResponseEntity<Object>("User already exists", HttpStatus.CONFLICT);

        Optional<Role> role = rolesRepo.findById( user.getRole().getId_role() );
        if ( role.isPresent() )
            if ( role.get().getPermissions().stream().noneMatch( permission -> permission.getName() == "AÑADIR_USUARIO" ) )
                return new ResponseEntity<Object>("Role has not permission of AÑADIR_USUARIO", HttpStatus.CONFLICT);

        this.usersRepo.save(user);

        return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }*/

       /*@PostMapping("")
    @ResponseBody ResponseEntity<Object> createUser(@RequestBody DTOUser dtoUser ) throws Exception{
        if ( this.repo.findByEmail(dtoUser.getEmail()) != null )
            return new ResponseEntity<Object>("User already exists", HttpStatus.CONFLICT);

        User user = new User( dtoUser.getEmail(), dtoUser.getPassword() );
        Optional<User> currentUserOptional = repo.findById(dtoUser.getIdCurrentUser());

        if ( currentUserOptional.isPresent() ) {
            Optional<Role> role = rolesRepo.findById(currentUserOptional.get().getRole().getId_role());

            if (role.isPresent())
                if (role.get().getPermissions().stream().noneMatch(permission -> permission.getName().equals("AÑADIR_USUARIO")) )
                    return new ResponseEntity<Object>("Role has not permission of AÑADIR_USUARIO", HttpStatus.CONFLICT);

            Optional<Role> roleUser = rolesRepo.findById(dtoUser.getIdRole());

            if ( !roleUser.isPresent() )
                return new ResponseEntity<Object>("El rol asignado al usuario no existe", HttpStatus.NOT_FOUND);

            user.setRole(roleUser.get());

            this.repo.save(user);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Object>("Current user does not exists", HttpStatus.NOT_FOUND);
    }*/
}
