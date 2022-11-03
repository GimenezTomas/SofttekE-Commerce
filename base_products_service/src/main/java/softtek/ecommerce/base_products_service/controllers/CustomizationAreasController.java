package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.CustomizationArea;
import softtek.ecommerce.base_products_service.repositories.interfaces.BaseProductsRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.CustomizationAreasRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.TypeHasAreaRepo;
import softtek.ecommerce.base_products_service.services.PermissionValidationService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/customization_areas")
public class CustomizationAreasController {

    @Autowired
    CustomizationAreasRepo repo;

    @Autowired
    PermissionValidationService permissionValidationService;

    @Autowired
    TypeHasAreaRepo typeHasAreaRepo;

    @GetMapping("")
    Page<CustomizationArea> customizationAreas( Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idArea}")
    Optional<CustomizationArea> customizationArea( @PathVariable( value = "idArea" ) String idArea ){
        return repo.findById(idArea);
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createCustomizationArea(@RequestBody @Valid CustomizationArea customizationArea, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_CUSTOMIZATIONAREA";

        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        if ( repo.findByNameAndActive( customizationArea.getName(), true ) != null  ){
            return new ResponseEntity<Object>("The name is already in use, "+customizationArea.getName(), HttpStatus.CONFLICT);
        }

        this.repo.save(customizationArea);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idArea}")
    @ResponseBody ResponseEntity<Object> deleteCustomizationArea( @PathVariable( value = "idArea" ) String idArea, @RequestParam String idCurrentUser ) throws Exception {
    final String permission = "BORRAR_CUSTOMIZATIONAREA";

        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<CustomizationArea> customizationAreaOptional = repo.findById( idArea );

        if ( !customizationAreaOptional.isPresent() && !customizationAreaOptional.get().getActive() )
            return new ResponseEntity<Object>("The area does not exists", HttpStatus.NOT_FOUND);

        if ( !typeHasAreaRepo.findByIdAreaAndActive( idArea ).isPresent() )
            return new ResponseEntity<Object>("The area has type has area reference", HttpStatus.CONFLICT);

        customizationAreaOptional.get().setActive( false );
        this.repo.save(customizationAreaOptional.get());

        return ResponseEntity.ok().build();
    }
}
