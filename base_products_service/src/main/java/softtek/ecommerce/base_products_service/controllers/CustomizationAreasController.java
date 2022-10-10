package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.CustomizationArea;
import softtek.ecommerce.base_products_service.repositories.interfaces.CustomizationAreasRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/customization_areas")
public class CustomizationAreasController {

    @Autowired
    CustomizationAreasRepo repo;

    @GetMapping("")
    Page<CustomizationArea> customizationAreas( Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    CustomizationArea customizationArea( @PathVariable( value = "name" ) String name ){
        return repo.findByNameAndActive( name, true );
    }

    @PostMapping("")
    String createCustomizationArea(@RequestBody @Valid CustomizationArea customizationArea/*, BindingResult bindingResult*/ ){
        if ( repo.findByNameAndActive( customizationArea.getName(), true ) != null  ){
            return "El nombre ya está en uso";
        }

        this.repo.save(customizationArea);

        return "ok";
    }

    @PutMapping("")
    String editCustomizationArea( @RequestBody @Valid CustomizationArea customizationArea ){
        if ( repo.findByNameAndActive( customizationArea.getName(), true ) != null  ){
            return "El nombre ya está en uso";
        }

        this.repo.save(customizationArea);
        return "ok";
    }
}
