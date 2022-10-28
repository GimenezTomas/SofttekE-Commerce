package softtek.ecommerce.shops_service.controllers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.PaymentMethod;
import softtek.ecommerce.shops_service.entities.Shop;
import softtek.ecommerce.shops_service.repositories.interfaces.PaymentMethodsRepo;
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

    /*@GetMapping("/{name}")
    Shop shop( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }*/

    @GetMapping("/owner")
    Shop shop( @RequestParam( value = "idUser" ) String idUser ){
        Optional<Shop> shop = repo.findByIdUser( idUser );
        return shop.isPresent() ? shop.get() : null;
    }

    @PostMapping("")//Lo pongo al idcurrentuser como request param xq cuando tenga spring sec esa info la voy a sacar de un token en el header
    @ResponseBody ResponseEntity<Object> createShop(@RequestBody @Valid Shop shop, @RequestParam String idCurrentUser ) throws Exception {
        final String permission = "CREAR_TIENDA";
        if ( !permissionValidationService.validation( idCurrentUser,permission) )
            return new ResponseEntity<Object>("The role must have the permission "+permission, HttpStatus.CONFLICT);

        String userString = restService.getUsersServiceObjectPlainJSON("users/"+shop.getIdUser());

        if ( userString.length() != 0 && !userString.equals("null") ){
            JsonObject user = new JsonParser().parse( userString ).getAsJsonObject();

            if ( !user.get("active").getAsBoolean() )
                return new ResponseEntity<Object>("The user " + user.get("email").getAsString() + " is not active any more", HttpStatus.CONFLICT);
        }
        else{
            return new ResponseEntity<Object>("The user does not exists", HttpStatus.NOT_FOUND);
        }

        if ( this.repo.findByIdUser( shop.getIdUser() ).isPresent() )
            return new ResponseEntity< Object>("The user already has a store", HttpStatus.CONFLICT);

        this.repo.save(shop);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @DeleteMapping("/{idPost}")
    @ResponseBody ResponseEntity<Object> deleteShop(@PathVariable( value = "idPost" ) String idPost ) throws Exception {
        /*
        roleValidationService.validation( dtoPermission.getIdCurrentUser(), "BORRAR_USUARIO");

        Optional<User> user = repo.findById( idUser );

        if ( !user.isPresent() )
            return new ResponseEntity<Object>("The user " + idUser + " does not exists ", HttpStatus.NOT_FOUND);

        String shopString = restService.getShopPlainJSON("owner?idUser="+idUser );

        if ( shopString.length() != 0 ){
            JsonObject shop = new JsonParser().parse( shopString ).getAsJsonObject();

            if ( shop.get("active").getAsBoolean() )
                return new ResponseEntity<Object>("The user " + user.get().getEmail() + " has an active shop", HttpStatus.CONFLICT);
        }

        user.get().setActive(false);
        repo.save(user.get());
*/  //TODO
        //hacer validaciones para que no elimine si, hay posteos, medios de pago o productos customizados
        return ResponseEntity.ok().build();
    }
}