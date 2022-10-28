package softtek.ecommerce.users_service.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.entities.User;
import softtek.ecommerce.users_service.entities.dtos.DTOPermission;
import softtek.ecommerce.users_service.entities.dtos.DTOUser;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;
import softtek.ecommerce.users_service.services.RestService;
import softtek.ecommerce.users_service.services.RoleValidationService;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersRepo repo;

    @Autowired
    RolesRepo rolesRepo;

    @Autowired
    RoleValidationService roleValidationService;

    @Autowired
    RestService restService;

    @GetMapping("")
    Page<User> users(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idUser}")
    Optional<User> user( @PathVariable( value = "idUser" ) String idUser ){
        return repo.findById( idUser );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createUser(@RequestBody DTOUser dtoUser ) throws Exception {
        roleValidationService.validation(dtoUser.getIdCurrentUser(), "AÑADIR_USUARIO");

        if ( this.repo.findByEmail(dtoUser.getEmail()) != null )
            return new ResponseEntity<Object>("User already exists", HttpStatus.CONFLICT);

        User user = new User( dtoUser.getEmail(), dtoUser.getPassword() );

        Optional<Role> roleUser = rolesRepo.findById(dtoUser.getIdRole());

        if ( !roleUser.isPresent() )
            return new ResponseEntity<Object>("El rol asignado al usuario no existe", HttpStatus.NOT_FOUND);

        user.setRole(roleUser.get());

        this.repo.save(user);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/{idUser}")
    @ResponseBody ResponseEntity<Object> deleteUser(@PathVariable( value = "idUser" ) String idUser, @RequestBody DTOPermission dtoPermission ) throws Exception {

        roleValidationService.validation( dtoPermission.getIdCurrentUser(), "BORRAR_USUARIO");

        Optional<User> user = repo.findById( idUser );

        if ( !user.isPresent() )
            return new ResponseEntity<Object>("The user " + idUser + " does not exists ", HttpStatus.NOT_FOUND);

        String shopString = restService.getShopPlainJSON("owner?idUser="+idUser );

        if ( shopString.length() != 0 ){
            JsonObject shop = new JsonParser().parse( shopString ).getAsJsonObject();

            if ( shop.get("active").getAsBoolean() )
                return new ResponseEntity<Object>("The user " + user.get().getEmail() + " has an active shop", HttpStatus.CONFLICT);
        }

        user.get().setActive(false);
        repo.save(user.get());

        return ResponseEntity.ok().build();
    }
}
