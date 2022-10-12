package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.PaymentMethod;
import softtek.ecommerce.shops_service.entities.Shop;
import softtek.ecommerce.shops_service.repositories.interfaces.PaymentMethodsRepo;
import softtek.ecommerce.shops_service.repositories.interfaces.ShopsRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/shops")
public class ShopsController {
    @Autowired
    ShopsRepo repo;

    @GetMapping("")
    Page<Shop> shops(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    Shop shop( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createShop(@RequestBody @Valid Shop shop ){
        //VALIDATIONS
        //TODO
        this.repo.save(shop);

        return "ok";
    }
}