package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.BaseProduct;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;
import softtek.ecommerce.base_products_service.entities.TypeHasAreaKey;
import softtek.ecommerce.base_products_service.entities.dtos.DTOBaseProduct;
import softtek.ecommerce.base_products_service.entities.dtos.DTOTHA;
import softtek.ecommerce.base_products_service.repositories.interfaces.BaseProductsRepo;
import softtek.ecommerce.base_products_service.repositories.interfaces.TypeHasAreaRepo;
import softtek.ecommerce.base_products_service.services.PermissionValidationService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/base_products")
public class BaseProductsController {
    @Autowired
    BaseProductsRepo repo;

    @Autowired
    TypeHasAreaRepo typeHasAreaRepo;

    @Autowired
    PermissionValidationService permissionValidationService;

    @GetMapping("")
    Page<BaseProduct> baseProducts(
            @RequestParam( value = "active", required = false ) Boolean active,
            Pageable page
    ){
        if ( active != null ){
            return new PageImpl<>(repo.findByActive(active));
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idBaseProduct}")
    Optional<BaseProduct> baseProduct( @PathVariable( value = "idBaseProduct" ) String idBaseProduct ){
        return repo.findById( idBaseProduct );
    }

    @PostMapping("/{idBaseProduct}/typeHasAreas")
    @ResponseBody ResponseEntity<Object> baseProductContainsTHA(@PathVariable( value = "idBaseProduct" ) String idBaseProduct, @RequestBody DTOTHA dtotha ){
        Optional<BaseProduct> baseProductOptional = repo.findById(idBaseProduct);

        if (!baseProductOptional.isPresent() || !baseProductOptional.get().isActive() )
            return new ResponseEntity<Object>("The base product does not exist or was deleted", HttpStatus.NOT_FOUND);

        for ( TypeHasAreaKey typeHasAreaKey: dtotha.getTypeHasAreaKeys() ) {
            Optional<TypeHasArea> typeHasAreaOptional = typeHasAreaRepo.findByIdTypeHasArea( typeHasAreaKey );

            if ( !typeHasAreaOptional.isPresent() || !typeHasAreaOptional.get().isActive() )
                return new ResponseEntity<Object>("this typehasarea does not exist or was deleted area:"+typeHasAreaKey.getIdCustomizationArea()+" type: "+typeHasAreaKey.getIdCustomizationType(), HttpStatus.NOT_FOUND);
            else if( !baseProductOptional.get().getTypesHasAreas().contains( typeHasAreaOptional.get() ) )
                return new ResponseEntity<Object>("The base product does not allow this typehasarea area:"+typeHasAreaKey.getIdCustomizationArea()+" type: "+typeHasAreaKey.getIdCustomizationType(), HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.accepted().build();
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createBaseProduct(@RequestBody @Valid DTOBaseProduct dtoBaseProduct, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_BASEPRODUCT";

        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Set<TypeHasArea> typeHasAreas = new HashSet<>();

        if ( dtoBaseProduct.getTypeHasAreaKeys() != null ) {
            for (TypeHasAreaKey typeHasAreaKey : dtoBaseProduct.getTypeHasAreaKeys()) {
                Optional<TypeHasArea> typeHasAreaOptional = typeHasAreaRepo.findByIdTypeHasArea(typeHasAreaKey);

                if (typeHasAreaOptional.isPresent() && typeHasAreaOptional.get().isActive()) {
                    typeHasAreas.add(typeHasAreaOptional.get());
                } else {
                    return new ResponseEntity<Object>("The type has area does not exist or was deleted", HttpStatus.NOT_FOUND);
                }
            }
        }

        BaseProduct baseProduct = new BaseProduct( dtoBaseProduct.getName(), dtoBaseProduct.getDescription(), dtoBaseProduct.getPrice(), dtoBaseProduct.getDays_fabrication_time() );
        baseProduct.setTypesHasAreas( typeHasAreas );
        this.repo.save(baseProduct);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/{idBaseProduct}")
    @ResponseBody ResponseEntity<Object> baseProduct(@PathVariable( value = "idBaseProduct" ) String idBaseProduct, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "BORRAR_BASEPRODUCT";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<BaseProduct> baseProductOptional = repo.findById(idBaseProduct);

        if ( !baseProductOptional.isPresent() || !baseProductOptional.get().isActive() )
            return new ResponseEntity<Object>("The baseproduct does not exists or was deleted", HttpStatus.NOT_FOUND);

        baseProductOptional.get().setActive(false);
        repo.save(baseProductOptional.get());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idBaseProduct}/addTypeHasArea")
    @ResponseBody ResponseEntity<Object> addTypeHasArea(@PathVariable( value = "idBaseProduct" ) String idBaseProduct, @RequestParam String idCurrentUser, @RequestBody TypeHasAreaKey typeHasAreaKey ) throws Exception {
        final String permission = "AÑADIR_TYPEHASAREA";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<BaseProduct> baseProductOptional = repo.findById(idBaseProduct);

        if ( !baseProductOptional.isPresent() && !baseProductOptional.get().isActive() )
            return new ResponseEntity<Object>("The base product does not exist or was deleted", HttpStatus.NOT_FOUND);

        Optional<TypeHasArea> typeHasAreaOptional = typeHasAreaRepo.findByIdTypeHasArea( typeHasAreaKey );
        if ( !typeHasAreaOptional.isPresent() && !typeHasAreaOptional.get().isActive() )
            return new ResponseEntity<Object>("The type has area does not exist or was deleted", HttpStatus.NOT_FOUND);

        baseProductOptional.get().addTypeHasArea( typeHasAreaOptional.get() );
        repo.save( baseProductOptional.get() );
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{idBaseProduct}/removeTypeHasArea")
    @ResponseBody ResponseEntity<Object> removeTypeHasArea(@PathVariable( value = "idBaseProduct" ) String idBaseProduct, @RequestParam String idCurrentUser, @RequestBody TypeHasAreaKey typeHasAreaKey ) throws Exception {
        final String permission = "REMOVER_TYPEHASAREA";
        if ( !permissionValidationService.validation( idCurrentUser, permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<BaseProduct> baseProductOptional = repo.findById(idBaseProduct);

        if ( !baseProductOptional.isPresent() && !baseProductOptional.get().isActive() )
            return new ResponseEntity<Object>("The base product does not exist or was deleted", HttpStatus.NOT_FOUND);

        Optional<TypeHasArea> typeHasAreaOptional = typeHasAreaRepo.findByIdTypeHasArea( typeHasAreaKey );
        if ( !typeHasAreaOptional.isPresent() && !typeHasAreaOptional.get().isActive() )
            return new ResponseEntity<Object>("The type has area does not exist or was deleted", HttpStatus.NOT_FOUND);

        baseProductOptional.get().removeTypeHasArea( typeHasAreaOptional.get() );
        repo.save( baseProductOptional.get() );
        return ResponseEntity.accepted().build();
    }
}
