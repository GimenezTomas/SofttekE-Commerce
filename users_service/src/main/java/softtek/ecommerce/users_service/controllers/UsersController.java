package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    String createUser(@RequestBody @Valid User user ){
        //VALIDATIONS
        //TODO
        this.repo.save(user);

        return "ok";
    }
}
