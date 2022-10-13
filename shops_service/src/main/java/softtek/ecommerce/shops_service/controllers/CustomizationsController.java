package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizationsRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/customizations")
public class CustomizationsController {
    @Autowired
    CustomizationsRepo repo;

    @GetMapping("")
    Page<Customization> customizations(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    Customization customization( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createCustomization(@RequestBody @Valid Customization customization ){
        //VALIDATIONS
        //TODO
        this.repo.save( customization );

        return "ok";
    }
}
