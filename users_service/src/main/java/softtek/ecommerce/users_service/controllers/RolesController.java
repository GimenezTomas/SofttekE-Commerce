package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;

import javax.validation.Valid;

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
}
