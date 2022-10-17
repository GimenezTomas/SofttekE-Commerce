package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Permission;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    RolesRepo repo;

    @GetMapping("")
    Page<Role> roles(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    Role role( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createRole(@RequestBody @Valid Role role ){
        //VALIDATIONS
        //TODO
        this.repo.save(role);

        return "ok";
    }

    @Transactional
    @PutMapping("/{id_role}/add_permission")
    String addPermission(@RequestBody @Valid Permission permission, @PathVariable( value = "id_role" ) String id_role ){
        AtomicReference<String> message = new AtomicReference<>("Not founded");
        Optional<Role> optionalRole = repo.findById(id_role);
        optionalRole.ifPresent( role -> {
            role.addPermission(permission);
            repo.save(role);
            message.set( "the permission " + permission.getName() + " was added successfully " );
        });

        return message.toString();
    }

    @Transactional
    @PutMapping("/{id_role}/remove_permission")
    String removePermission(@RequestBody @Valid Permission permission, @PathVariable( value = "id_role" ) String id_role ){
        AtomicReference<String> message = new AtomicReference<>("Not founded");
        Optional<Role> optionalRole = repo.findById(id_role);
        optionalRole.ifPresent( role -> {
            role.removePermission(permission);
            repo.save(role);
            message.set( "the permission " + permission.getName() + " was removed successfully " );
        });

        return message.toString();
    }
}
