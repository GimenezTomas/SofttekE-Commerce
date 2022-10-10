package softtek.ecommerce.userservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softtek.ecommerce.userservice.entities.Role;
import softtek.ecommerce.userservice.entities.User;

import java.util.UUID;

@RestController
@RequestMapping( value = "/user")
public class UserController {
    @GetMapping
    /*public User user(@RequestParam( value = "email" ) String email, @RequestParam( value = "id_role" ) UUID id_role, @RequestParam( value = "password" ) String password ){
        return new User( email, password, id_role );
    }*/
    public void user(@RequestParam( value = "email" ) String email, @RequestParam( value = "id_role" ) UUID id_role, @RequestParam( value = "password" ) String password ){
        //return new User( email, password, id_role );
    }
}
