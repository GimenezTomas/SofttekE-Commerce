package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Item;
import softtek.ecommerce.shops_service.entities.ShoppingCart;
import softtek.ecommerce.shops_service.repositories.interfaces.ShoppingCartsRepo;

import java.util.Optional;

@RestController
@RequestMapping("/shopping_carts")
public class ShoppingCartsController {
    @Autowired
    ShoppingCartsRepo repo;

    @GetMapping("")
    Page<ShoppingCart> shoppingCarts(@RequestParam( value = "active", required = false ) Boolean active, Pageable page ){
        if ( active != null ){
            return new PageImpl<>( repo.findByActive(active) );
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idShoppingCart}")
    Optional<ShoppingCart> shoppingCart (@PathVariable( value = "idShoppingCart" ) String idShoppingCart ){
        return repo.findById( idShoppingCart );
    }

    @PostMapping("")
    @ResponseBody ResponseEntity<Object> shoppingCart(){
        repo.save( new ShoppingCart() );
        return ResponseEntity.ok().build();
    }
}
