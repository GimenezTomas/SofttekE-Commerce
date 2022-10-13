package softtek.ecommerce.shops_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.shops_service.entities.CustomizatedProduct;
import softtek.ecommerce.shops_service.repositories.interfaces.CustomizatedProductsRepo;

import javax.validation.Valid;

@RestController
@RequestMapping("/customizated_products")
public class CustomizatedProductsController {
    @Autowired
    CustomizatedProductsRepo repo;

    @GetMapping("")
    Page<CustomizatedProduct> customizatedProducts(Pageable page ){
        return repo.findAll( page );
    }

    @GetMapping("/{name}")
    CustomizatedProduct customizatedProduct( @PathVariable( value = "name" ) String name ){
        return repo.findByName( name );
    }

    @PostMapping("")
    String createCustomizatedProduct(@RequestBody @Valid CustomizatedProduct customizatedProduct ){
        //VALIDATIONS
        //TODO
        this.repo.save( customizatedProduct );

        return "ok";
    }
}