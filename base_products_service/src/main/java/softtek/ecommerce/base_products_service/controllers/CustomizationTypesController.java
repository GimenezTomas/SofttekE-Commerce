package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.CustomizationArea;
import softtek.ecommerce.base_products_service.entities.CustomizationType;
import softtek.ecommerce.base_products_service.repositories.interfaces.CustomizationTypesRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.TypeHasAreaRepo;
import softtek.ecommerce.base_products_service.services.PermissionValidationService;
import softtek.ecommerce.base_products_service.services.RestService;

import javax.validation.Valid;
import java.util.Optional;

@RestController()
@RequestMapping("/customization_types")
public class CustomizationTypesController {

    @Autowired
    CustomizationTypesRepo repo;

    @Autowired
    PermissionValidationService permissionValidationService;

    @Autowired
    TypeHasAreaRepo typeHasAreaRepo;

    @Autowired
    RestService restService;

    @GetMapping("")
    Page<CustomizationType> customizationTyes(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idCustomizationType}")
    Optional<CustomizationType> customizationType( @PathVariable( value = "idCustomizationType" ) String idCustomizationType ){
        return repo.findById( idCustomizationType );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createCustomizationType(@RequestBody @Valid CustomizationType customizationType, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_CUSTOMIZATIONTYPE";

        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        if ( repo.findByNameAndActive( customizationType.getName(), true ) != null  ){
            return new ResponseEntity<Object>("The name is already in use, "+customizationType.getName(), HttpStatus.CONFLICT);
        }

        this.repo.save(customizationType);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idType}")
    @ResponseBody
    ResponseEntity<Object> deleteCustomizationType(@PathVariable( value = "idType" ) String idType, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "BORRAR_CUSTOMIZATIONTYPE";

        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<CustomizationType> customizationType = repo.findById( idType );

        if ( !customizationType.isPresent() && !customizationType.get().getActive() )
            return new ResponseEntity<Object>("The area does not exists", HttpStatus.NOT_FOUND);

        if ( !typeHasAreaRepo.findByIdTypeAndActive( idType ).isPresent() )
            return new ResponseEntity<Object>("The type has type has area reference", HttpStatus.CONFLICT);

        String obj = restService.getShopsServiceObjectPlainJSON("customizations/byIdCustomizationType/"+idType);
        if ( obj.length() != 0 || !obj.equals("null") ){
            return new ResponseEntity<Object>("The TYPE has an active CUSTOMIZATION", HttpStatus.CONFLICT);
        }

        customizationType.get().setActive( false );
        this.repo.save(customizationType.get());

        return ResponseEntity.ok().build();
    }
}
