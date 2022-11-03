package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Buyer;
import softtek.ecommerce.shops_service.entities.Order;
import softtek.ecommerce.shops_service.entities.ShoppingCart;
import softtek.ecommerce.shops_service.entities.dtos.DTOOrder;
import softtek.ecommerce.shops_service.repositories.interfaces.BuyersRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.OrdersRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.ShoppingCartsRepo;
import softtek.ecommerce.shops_service.services.RestService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    OrdersRepo repo;

    @Autowired
    ShoppingCartsRepo shoppingCartsRepo;

    @Autowired
    BuyersRepo buyersRepo;

    @Autowired
    RestService restService;

    @GetMapping("")
    Page<Order> orders(@RequestParam( value = "active", required = false ) Boolean active, Pageable page ){
        if ( active != null ){
            return new PageImpl<>( repo.findByActive(active) );
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idOrder}")
    Optional<Order> order( @PathVariable( value = "idOrder") String idOrder ){
        return repo.findById( idOrder );
    }

    @GetMapping("/byPaymentMethod/{idPaymentMethod}")
    Optional<Order> orderByPaymentMethod( @PathVariable( value = "idPaymentMethod") String idPaymentMethod ){
        return repo.findOneByidPaymentMethodAndActive( idPaymentMethod, true );
    }


    @PostMapping("")
    @ResponseBody ResponseEntity<Object> createOrder( @RequestBody @Valid DTOOrder dtoOrder ){
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartsRepo.findById( dtoOrder.getIdShoppingCart() );

        if ( !shoppingCartOptional.isPresent() || !shoppingCartOptional.get().getActive() || shoppingCartOptional.get().getItems().size() == 0 ){
            return new ResponseEntity<Object>("The shopping cart does not exists or was deleted", HttpStatus.NOT_FOUND);
        }

        if ( shoppingCartsRepo.findByOrder( dtoOrder.getIdShoppingCart() ).isPresent() )
            return new ResponseEntity<Object>("The shopping cart was used", HttpStatus.NOT_FOUND);

        String paymentMethod = restService.getShopsServiceObjectPlainJSON("payment_methods/"+dtoOrder.getIdPaymentMethod());

        if ( paymentMethod.length() == 0 || paymentMethod.equals("null") ){
            return new ResponseEntity<Object>("The paymentMethod does not exists", HttpStatus.NOT_FOUND);
        }

        String idShopPaymentMethod = restService.getShopsServiceObjectPlainJSON("shops/byIdPaymentMethod/"+dtoOrder.getIdPaymentMethod());

        String idShopItem = restService.getShopsServiceObjectPlainJSON("shops/byIdPost/" + shoppingCartOptional.get().getItems().stream().findAny().get().getIdPost());
        if ( (idShopPaymentMethod.length() != 0 && !idShopPaymentMethod.equals("null") ) && (idShopItem.length() != 0 && !idShopItem.equals("null") )) {
            if ( !idShopPaymentMethod.equals( idShopItem ) )
                return new ResponseEntity<>("The payment method is invalid", HttpStatus.CONFLICT);

            Order order = new Order( dtoOrder.getIdPaymentMethod() );
            order.setShoppingCart( shoppingCartOptional.get() );

            Optional<Buyer> buyerOptional = buyersRepo.findById(dtoOrder.getIdBuyer());
            if ( !buyerOptional.isPresent() || !buyerOptional.get().getActive() )
                return new ResponseEntity<>("The buyer does not exist or was deleted", HttpStatus.NOT_FOUND);

            order.setBuyer( buyerOptional.get() );

            repo.save( order );
            return ResponseEntity.ok().build();
        }

        return new ResponseEntity<>("idShopItem: "+idShopItem+" Error idShopPaymentMethod: "+idShopPaymentMethod, HttpStatus.CONFLICT);
    }
}
