package softtek.ecommerce.users_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.users_service.entities.Permission;
import softtek.ecommerce.users_service.entities.Role;
import softtek.ecommerce.users_service.exceptions.PermissionInvalidException;
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
    @ResponseBody ResponseEntity<Object> createRole(@RequestBody @Valid Role role ) throws Exception{
        if ( this.repo.findByName(role.getName()) != null )
            return new ResponseEntity<Object>("Role already exists", HttpStatus.CONFLICT);
        this.repo.save(role);

        return ResponseEntity.ok().build();
    }
}
