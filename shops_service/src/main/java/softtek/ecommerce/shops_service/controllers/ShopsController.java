package softtek.ecommerce.shops_service.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Shop;
import softtek.ecommerce.shops_service.repositories.interfaces.ShopsRepo;
import softtek.ecommerce.shops_service.services.PermissionValidationService;
import softtek.ecommerce.shops_service.services.RestService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/shops")
public class ShopsController {
    @Autowired
    ShopsRepo repo;

    @Autowired
    RestService restService;

    @Autowired
    PermissionValidationService permissionValidationService;

    @GetMapping("")
    Page<Shop> shops(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{idShop}")
    Optional<Shop> shopById( @PathVariable( value = "idShop" ) String idShop ){
        return repo.findById( idShop );
    }

    @GetMapping("/byIdPost/{idPost}")
    String idShopByidPost(@PathVariable( value = "idPost" ) String idPost ){
        return repo.findByIdPost( idPost );
    }

    @GetMapping("/byIdPaymentMethod/{idPaymentMethod}")
    String idShopByIdPaymentMethod(@PathVariable( value = "idPaymentMethod" ) String idPaymentMethod ){
        return repo.findByIdPaymentMethod( idPaymentMethod );
    }

    @GetMapping("/owner")
    Shop shop( @RequestParam( value = "idUser" ) String idUser ){
        Optional<Shop> shop = repo.findByIdUser( idUser );
        return shop.isPresent() ? shop.get() : null;
    }

    @PostMapping("")//Lo pongo al idcurrentuser como request param xq cuando tenga spring sec esa info la voy a sacar de un token en el header
    @ResponseBody ResponseEntity<Object> createShop(@RequestBody @Valid Shop shop, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_TIENDA";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        String userString = restService.getUsersServiceObjectPlainJSON("users/"+shop.getIdUser());

        if ( userString.length() != 0 && !userString.equals("null") ){
            JsonObject user = new JsonParser().parse( userString ).getAsJsonObject();

            if ( !user.get("active").getAsBoolean() )
                return new ResponseEntity<>("The user " + user.get("email").getAsString() + " is not active any more", HttpStatus.CONFLICT);
        }
        else{
            return new ResponseEntity<>("The user does not exists", HttpStatus.NOT_FOUND);
        }

        if ( this.repo.findByIdUser( shop.getIdUser() ).isPresent() )
            return new ResponseEntity<>("The user already has a store", HttpStatus.CONFLICT);

        this.repo.save(shop);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/{idPost}")
    @ResponseBody ResponseEntity<Object> deleteShop(@PathVariable( value = "idPost" ) String idShop, @RequestParam String idCurrentUser ) throws Exception {
        permissionValidationService.validation( idCurrentUser, "BORRAR_USUARIO");

        Optional<Shop> shopOptional = repo.findById( idShop );

        if ( !shopOptional.isPresent() || !shopOptional.get().getActive() || !shopOptional.get().getIdUser().equals( idCurrentUser ) )
            return new ResponseEntity<>("The user is not the owner", HttpStatus.CONFLICT);

        if ( shopOptional.get().getCustomizatedProducts().stream().anyMatch( customizatedProduct -> customizatedProduct.getActive()) )
            return new ResponseEntity<>("The shop has an active customizatedProduct ", HttpStatus.CONFLICT);

        if ( shopOptional.get().getCustomization().stream().anyMatch( customization -> customization.getActive()) )
            return new ResponseEntity<>("The shop has an active customization ", HttpStatus.CONFLICT);

        if ( shopOptional.get().getPaymentMethods().stream().anyMatch( paymentMethod -> paymentMethod.getActive()) )
            return new ResponseEntity<>("The shop has an active paymentmethod ", HttpStatus.CONFLICT);

        if ( shopOptional.get().getPosts().stream().anyMatch( post -> post.getActive()) )
            return new ResponseEntity<>("The shop has an active post", HttpStatus.CONFLICT);

        shopOptional.get().setActive(false);
        repo.save( shopOptional.get() );
        return ResponseEntity.ok().build();
    }
}