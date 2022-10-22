package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.entities.User;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;
import softtek.ecommerce.users_service.repositories.interfaces.UsersRepo;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersRepo repo;

    @GetMapping("")
    Page<User> users(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{email}")
    User user( @PathVariable( value = "email" ) String email ){
        return repo.findByEmail( email );
    }

    @PostMapping("")
    @ResponseBody
    ResponseEntity<Object> createUser(@RequestBody @Valid User user ) throws Exception{
        if ( this.repo.findByEmail(user.getEmail()) != null )
            return new ResponseEntity<Object>("User already exists", HttpStatus.CONFLICT);
        this.repo.save(user);

        return ResponseEntity.ok().build();
    }
}
