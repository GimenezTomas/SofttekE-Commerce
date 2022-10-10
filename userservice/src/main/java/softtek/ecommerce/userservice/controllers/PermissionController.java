package softtek.ecommerce.userservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softtek.ecommerce.userservice.entities.Permission;

@RestController
@RequestMapping( value = "/permission" )
public class PermissionController {
    @GetMapping
    public Permission permission(@RequestParam( value = "name" ) String name){
        return new Permission(name);
    }
}
