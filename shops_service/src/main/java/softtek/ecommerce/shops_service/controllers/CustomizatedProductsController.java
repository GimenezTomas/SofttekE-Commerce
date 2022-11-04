package softtek.ecommerce.shops_service.controllers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import softtek.ecommerce.shops_service.entities.*;
import softtek.ecommerce.shops_service.entities.dtos.DTOCustomizatedProduct;
import softtek.ecommerce.shops_service.entities.dtos.DTOTHA;
import softtek.ecommerce.shops_service.exceptions.AreaHasCustomizationException;
import softtek.ecommerce.shops_service.repositories.interfaces.*;
import softtek.ecommerce.shops_service.services.AreasHasCustomizationsService;
import softtek.ecommerce.shops_service.services.PermissionValidationService;
import softtek.ecommerce.shops_service.services.RestService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/customizated_products")
public class CustomizatedProductsController {
    @Autowired
    CustomizatedProductsRepo repo;

    @Autowired
    ShopsRepo shopsRepo;

    @Autowired
    PostsRepo postsRepo;

    @Autowired
    AreasHasCustomizationsService areasHasCustomizationsService;

    @Autowired
    AreasHasCustomizationsRepo areasHasCustomizationsRepo;

    @Autowired
    RestService restService;

    @Autowired
    PermissionValidationService permissionValidationService;

    @GetMapping("")
    Page<CustomizatedProduct> customizatedProducts(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idCustomizatedProduct}")
    Optional<CustomizatedProduct> customizatedProduct( @PathVariable( value = "idCustomizatedProduct" ) String idCustomizatedProduct ){
        return repo.findById( idCustomizatedProduct );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createCustomizatedProduct(@RequestBody @Valid DTOCustomizatedProduct dtoCustomizatedProduct, @RequestParam String idCurrentUser ) throws Exception {
        //VERIFICO PERMISOS
        final String permission = "CREAR_CUSTOMIZATED_PRODUCT";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        try{
            Set< AreaHasCustomization > areaHasCustomizations = areasHasCustomizationsService.dataValidation( dtoCustomizatedProduct, idCurrentUser );

            CustomizatedProduct customizatedProduct = new CustomizatedProduct(dtoCustomizatedProduct.getName(), dtoCustomizatedProduct.getIdBaseProduct());
            customizatedProduct.setShop( shopsRepo.findById(dtoCustomizatedProduct.getIdShop()).get());
            customizatedProduct.setAreaHasCustomizations( areaHasCustomizations );

            this.repo.save( customizatedProduct );

            return ResponseEntity.ok().build();
        }catch ( Exception e){
            throw  e;
        }
    }



    @DeleteMapping("/{idCustomizatedProduct}")
    @ResponseBody ResponseEntity<Object> deleteCustomizatedProduct( @PathVariable String idCustomizatedProduct, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "BORRAR_CUSTOMIZATEDPRODUCT";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<CustomizatedProduct> customizatedProductOptional = repo.findById(idCustomizatedProduct);

        if ( !customizatedProductOptional.isPresent() || !customizatedProductOptional.get().getActive() || !customizatedProductOptional.get().getShop().getIdUser().equals(idCurrentUser))
            return new ResponseEntity<Object>("The customizatedProduct does not exists, was deleted or you are not the owner", HttpStatus.CONFLICT);

        Optional<Post> postOptional = postsRepo.findByCustomizatedProductById( idCustomizatedProduct );
        if ( customizatedProductOptional.isPresent() )
            return new ResponseEntity<Object>("The customizatedProduct has an active post", HttpStatus.CONFLICT);

        customizatedProductOptional.get().setActive( false );

        this.repo.save( customizatedProductOptional.get() );
        return ResponseEntity.ok().build();
    }
}