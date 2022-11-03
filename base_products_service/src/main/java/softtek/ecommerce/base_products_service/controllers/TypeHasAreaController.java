package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.*;
import softtek.ecommerce.base_products_service.repositories.interfaces.BaseProductsRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.CustomizationAreasRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.CustomizationTypesRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.TypeHasAreaRepo;
import softtek.ecommerce.base_products_service.services.PermissionValidationService;
import softtek.ecommerce.base_products_service.services.RestService;

import javax.validation.Valid;
import java.util.Optional;

@RestController()
@RequestMapping("/type_has_area")
public class TypeHasAreaController {

    @Autowired
    TypeHasAreaRepo repo;

    @Autowired
    BaseProductsRepo baseProductsRepo;

    @Autowired
    CustomizationAreasRepo customizationAreaRepo;

    @Autowired
    CustomizationTypesRepo customizationTypeRepo;

    @Autowired
    PermissionValidationService permissionValidationService;

    @Autowired
    RestService restService;


    @GetMapping("")
    Page<TypeHasArea> type_has_area(Pageable page ){
        return repo.findAll( page );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createTypeHasArea(@RequestBody @Valid TypeHasAreaKey typeHasAreaKey, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_TYPEHASAREA";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<CustomizationArea> customizationArea = customizationAreaRepo.findById( typeHasAreaKey.getIdCustomizationArea() );

        if ( !customizationArea.isPresent() || !customizationArea.get().getActive() )
            return new ResponseEntity<Object>("The area does not exist or was deleted", HttpStatus.NOT_FOUND);

        Optional<CustomizationType> customizationType = customizationTypeRepo.findById( typeHasAreaKey.getIdCustomizationType() );

        if ( !customizationType.isPresent() || !customizationType.get().getActive() )
            return new ResponseEntity<Object>("The type does not exist or was deleted", HttpStatus.NOT_FOUND);

        Optional<TypeHasArea> typeHasAreaOptional = repo.findByIdTypeHasArea( typeHasAreaKey );

        if ( typeHasAreaOptional.isPresent() && typeHasAreaOptional.get().isActive() )
            return new ResponseEntity<Object>("The type has area exists", HttpStatus.CONFLICT);

        TypeHasArea typeHasArea = new TypeHasArea( typeHasAreaKey, customizationType.get(), customizationArea.get() );
        this.repo.save( typeHasArea );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/")
    @ResponseBody ResponseEntity<Object> deleteTypeHasArea(@RequestBody TypeHasAreaKey typeHasAreaKey, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "BORRAR_TYPEHASAREA";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<TypeHasArea> typeHasAreaOptional = repo.findByIdTypeHasArea( typeHasAreaKey );

        if ( !typeHasAreaOptional.isPresent() || !typeHasAreaOptional.get().isActive() )
            return new ResponseEntity<Object>("The type has area does not exists or was deleted", HttpStatus.CONFLICT);

        if ( baseProductsRepo.findByTypeHasArea( typeHasAreaKey.getIdCustomizationType(), typeHasAreaKey.getIdCustomizationArea() ) != null )
            return new ResponseEntity<Object>("The type has area has base product", HttpStatus.CONFLICT);

        String obj = restService.getShopsServiceObjectPlainJSON("areas_has_customizations/byTHA?idArea"+typeHasAreaKey.getIdCustomizationArea()+"&idType="+typeHasAreaKey.getIdCustomizationType());
        if ( obj.length() != 0 || !obj.equals("null") ){
            return new ResponseEntity<Object>("The TYPEHASAREA has an avtive AREAHASCUSTOMIZATION", HttpStatus.NOT_FOUND);
        }

        typeHasAreaOptional.get().setActive( false );
        this.repo.save(typeHasAreaOptional.get());

        return ResponseEntity.ok().build();
    }
}
