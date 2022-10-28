package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.entities.Shop;
import softtek.ecommerce.shops_service.entities.dtos.DTOCustomization;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizationsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.ShopsRepo;
import softtek.ecommerce.shops_service.services.PermissionValidationService;
import softtek.ecommerce.shops_service.services.RestService;

import java.util.Optional;

@RestController
@RequestMapping("/customizations")
public class CustomizationsController {
    @Autowired
    CustomizationsRepo repo;

    @Autowired
    ShopsRepo shopsRepo;

    @Autowired
    RestService restService;

    @Autowired
    PermissionValidationService permissionValidationService;

    @GetMapping("")
    Page<Customization> customizations(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idCustomization}")
    Optional<Customization> customization( @PathVariable( value = "idCustomization" ) String idCustomization ){
        return repo.findById( idCustomization );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createCustomization( @RequestBody DTOCustomization dtoCustomization, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_CUSTOMIZATION";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<Shop> shopOptional = shopsRepo.findById(dtoCustomization.getIdShop());
        if ( !shopOptional.isPresent() || !shopOptional.get().getActive() || !shopOptional.get().getIdUser().equals(idCurrentUser) ){
            return new ResponseEntity<Object>("The shop does not exists, was deleted or you are not the owner", HttpStatus.CONFLICT);
        }

        String customizationTypeString = restService.getBaseProductsServiceObjectPlainJSON("customization_types/"+dtoCustomization.getIdCustomizationType());

        if ( customizationTypeString.length() == 0 || customizationTypeString.equals("null") ){
            return new ResponseEntity<Object>("The customization type does not exists", HttpStatus.NOT_FOUND);
            //agregar checkeo de borrado logico, por ende el active alla TODO
        }

        Customization customization = new Customization(dtoCustomization.getName(), dtoCustomization.getContent(), dtoCustomization.getIdCustomizationType(), dtoCustomization.getPrice() );
        customization.setShop(shopOptional.get());

        this.repo.save( customization );

        return ResponseEntity.ok().build();
    }


    @DeleteMapping("")
    @ResponseBody ResponseEntity<Object> deleteCustomization( @RequestBody DTOCustomization dtoCustomization, @RequestParam String idCurrentUser ) throws Exception {
        //TODO
        return ResponseEntity.ok().build();
    }
}
