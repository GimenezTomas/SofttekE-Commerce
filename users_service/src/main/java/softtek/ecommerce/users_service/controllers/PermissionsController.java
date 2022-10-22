package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Permission;
import softtek.ecommerce.users_service.repositories.interfaces.PermissionsRepo;
import softtek.ecommerce.users_service.repositories.interfaces.RolesRepo;

import javax.validation.Valid;
import java.util.Optional;

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
    @ResponseBody ResponseEntity<Object> createPermission(@RequestBody @Valid Permission permission ) throws Exception{
        if ( this.repo.findByName(permission.getName()) != null )
            return new ResponseEntity<Object>("permission already exists", HttpStatus.CONFLICT);
        this.repo.save(permission);

        return ResponseEntity.ok().build();
    }
}
