package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.CustomizationType;
import softtek.ecommerce.base_products_service.repositories.interfaces.CustomizationTypesRepo;

import javax.validation.Valid;
import java.util.Optional;

@RestController()
@RequestMapping("/customization_types")
public class CustomizationTypesController {

    @Autowired
    CustomizationTypesRepo repo;

    @GetMapping("")
    Page<CustomizationType> customizationTyes(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idCustomizationType}")
    Optional<CustomizationType> customizationType( @PathVariable( value = "idCustomizationType" ) String idCustomizationType ){
        return repo.findById( idCustomizationType );
    }

    @PostMapping("")
    String createCustomizationType( @RequestBody @Valid CustomizationType customizationType ){
        if ( repo.findByNameAndActive( customizationType.getName(), true ) != null ){
            return "El nombre ya está en uso";
        }
        this.repo.save(customizationType);

        return "ok";
    }
}
