package softtek.ecommerce.shops_service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.Item;
import softtek.ecommerce.shops_service.entities.ShoppingCart;
import softtek.ecommerce.shops_service.entities.dtos.DTOItem;
import softtek.ecommerce.shops_service.exceptions.ItemException;
import softtek.ecommerce.shops_service.repositories.interfaces.ItemsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.ShoppingCartsRepo;
import softtek.ecommerce.shops_service.services.RestService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    ItemsRepo repo;

    @Autowired
    ShoppingCartsRepo shoppingCartsRepo;

    @Autowired
    RestService restService;

    @GetMapping("")
    Page<Item> items(
            @RequestParam( value = "active", required = false ) Boolean active,
            Pageable page
    ){
        if ( active != null ){
            return new PageImpl<>(repo.findByActive(active));
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idItem}")
    Optional<Item> item (@PathVariable( value = "idItem" ) String idItem ){
        return repo.findById( idItem );
    }


    @GetMapping("/byPost/{idItem}")
    List<Item> items (@PathVariable( value = "idItem" ) String idPost ){
        return repo.findByIdPostAndActive( idPost, true );
    }

    @Transactional
    @PostMapping("")
    @ResponseBody ResponseEntity<Object> item( @RequestBody @Valid DTOItem dtoItem ) throws ItemException {
        String post = restService.getShopsServiceObjectPlainJSON("posts/" + dtoItem.getIdPost());

        if (post.length() == 0 || post.equals("null")) {
            return new ResponseEntity<Object>("The post does not exists", HttpStatus.NOT_FOUND);
        }

        String shopDto = restService.getShopsServiceObjectPlainJSON("shops/byIdPost/" + dtoItem.getIdPost());

        if (shopDto.length() == 0 || shopDto.equals("null")) {
            return new ResponseEntity<Object>("The shop does not exists", HttpStatus.NOT_FOUND);
        }

        Optional<ShoppingCart> shoppingCartOptional = shoppingCartsRepo.findById(dtoItem.getIdShoppingCart());

        if (!shoppingCartOptional.isPresent() || !shoppingCartOptional.get().getActive()) {
            return new ResponseEntity<Object>("The shopping cart does not exists or was deleted", HttpStatus.NOT_FOUND);
        }

        if (shoppingCartOptional.get().getItems().stream().findAny().isPresent()) {
            shoppingCartOptional.get().getItems().stream().findAny().get().getIdPost();

            String shop = restService.getShopsServiceObjectPlainJSON("shops/byIdPost/" + dtoItem.getIdPost());
            if (shop.length() != 0 && shop.equals("null")) {
                if ( !shopDto.equals( shop ) )
                    return new ResponseEntity<Object>("The post inside item is from another shop", HttpStatus.CONFLICT);
            }
        }

        Item item = new Item( dtoItem.getIdPost(), dtoItem.getQuantity());

        if ( shoppingCartOptional.get().addItem( item ) )
            shoppingCartsRepo.save( shoppingCartOptional.get() );
        else{
            item.setShoppingCart( shoppingCartOptional.get() );
            repo.save( item );
        }

        return  ResponseEntity.ok().build();
    }
}
