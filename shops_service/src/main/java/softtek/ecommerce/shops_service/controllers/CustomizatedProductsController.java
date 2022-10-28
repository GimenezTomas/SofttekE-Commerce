package softtek.ecommerce.shops_service.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.CustomizatedProduct;
import softtek.ecommerce.shops_service.entities.Customization;
import softtek.ecommerce.shops_service.entities.dtos.DTOCustomizatedProduct;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizatedProductsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizationsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.PostsRepo;
import softtek.ecommerce.shops_service.services.PermissionValidationService;
import softtek.ecommerce.shops_service.services.RestService;

import java.util.Optional;

@RestController
@RequestMapping("/customizated_products")
public class CustomizatedProductsController {
    @Autowired
    CustomizatedProductsRepo repo;

    /*@Autowired
    ShopsRepo shopsRepo;
*/
    @Autowired
    PostsRepo postsRepo;

    @Autowired
    CustomizationsRepo customizationsRepo;

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
    @ResponseBody ResponseEntity<Object> createCustomizatedProduct(@RequestBody DTOCustomizatedProduct dtoCustomizatedProduct, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_CUSTOMIZATED_PRODUCT";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<Customization> customizationOptional = customizationsRepo.findById(dtoCustomizatedProduct.getIdCustomization());
        if ( !customizationOptional.isPresent() || !customizationOptional.get().getActive() || !customizationOptional.get().getShop().getIdShop().equals(dtoCustomizatedProduct.getIdShop()) || !customizationOptional.get().getShop().getIdUser().equals(idCurrentUser) ){
            return new ResponseEntity<Object>("The shop or the customization does not exists, was deleted or you are not the owner", HttpStatus.CONFLICT);
        }

        String baseProductString = restService.getBaseProductsServiceObjectPlainJSON("base_products/"+dtoCustomizatedProduct.getIdBaseProduct());

        if ( baseProductString.length() == 0 || baseProductString.equals("null") ){
            return new ResponseEntity<Object>("The base product does not exists", HttpStatus.NOT_FOUND);
            //agregar checkeo de borrado logico, por ende el active alla TODO
        }

        JsonObject baseProduct = new JsonParser().parse( baseProductString ).getAsJsonObject();

        if ( !baseProduct.get("active").getAsBoolean() )
            return new ResponseEntity<Object>("The base product was deleted", HttpStatus.NOT_FOUND);


        CustomizatedProduct customizatedProduct = new CustomizatedProduct(dtoCustomizatedProduct.getName(), dtoCustomizatedProduct.getIdBaseProduct());
        customizatedProduct.setShop(customizationOptional.get().getShop());
        customizatedProduct.setCustomization(customizationOptional.get());

        this.repo.save( customizatedProduct );

        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{idCustomizatedProduct}")
    @ResponseBody ResponseEntity<Object> deleteCustomizatedProduct( @PathVariable String idCustomizatedProduct, @RequestParam String idCurrentUser ) throws Exception {
        /*final String permission = "BORRAR_CUSTOMIZATED_PRODUCT";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        Optional<Po> customizatedProductOptional = repo.findById(idCustomizatedProduct);

        if ( !customizatedProductOptional.isPresent() || !customizatedProductOptional.get().getActive() || !customizatedProductOptional.get().getShop().getIdUser().equals(idCurrentUser))
            return new ResponseEntity<Object>("The customizatedProduct does not exists, was deleted or you are not the owner", HttpStatus.CONFLICT);

        Optional<Post> postOptional = postsRepo.findById()

        String baseProductString = restService.getBaseProductsServiceObjectPlainJSON("base_products/"+dtoCustomizatedProduct.getIdBaseProduct());

        if ( baseProductString.length() == 0 || baseProductString.equals("null") ){
            return new ResponseEntity<Object>("The base product does not exists", HttpStatus.NOT_FOUND);
            //agregar checkeo de borrado logico, por ende el active alla TODO
        }

        JsonObject baseProduct = new JsonParser().parse( baseProductString ).getAsJsonObject();

        if ( !baseProduct.get("active").getAsBoolean() )
            return new ResponseEntity<Object>("The base product was deleted", HttpStatus.NOT_FOUND);


        CustomizatedProduct customizatedProduct = new CustomizatedProduct(dtoCustomizatedProduct.getName(), dtoCustomizatedProduct.getIdBaseProduct());
        customizatedProduct.setShop(customizationOptional.get().getShop());
        customizatedProduct.setCustomization(customizationOptional.get());

        this.repo.save( customizatedProduct );
*///TODO
        return ResponseEntity.ok().build();
    }
}