package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Permission;
import softtek.ecommerce.users_service.repositories.interfaces.PermissionsRepo;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/permissions")
public class PermissionsController {
    @Autowired
    PermissionsRepo repo;

    @GetMapping("")
    Page<Permission> permissions(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    Permission permission( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createPermission(@RequestBody @Valid Permission permission ){
        //VALIDATIONS
        //TODO
        this.repo.save(permission);

        return "ok";
    }
}
