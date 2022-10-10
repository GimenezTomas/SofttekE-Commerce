package softtek.ecommerce.userservice.controllers;

import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.userservice.entities.Role;

@RestController
@RequestMapping( value = "/role" )
public class RoleController {
    @GetMapping
    public Role role( @RequestParam( value = "name" ) String name ){
        return new Role(name);
    }

    /*@PostMapping() //@PostMapping(value = "/users")
    public void createRole( @RequestBody()){

    }*/
}