package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.BaseProduct;
import softtek.ecommerce.base_products_service.repositories.interfaces.BaseProductsRepo;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/base_products")
public class BaseProductsController {
    @Autowired
    BaseProductsRepo repo;

    @GetMapping("")
    Page<BaseProduct> baseProducts(
            @RequestParam( value = "active", required = false ) Boolean active,
            Pageable page
    ){
        if ( active != null ){
            return new PageImpl<>(repo.findByActive(active));
        }

        return repo.findAll( page );
    }

    @GetMapping("/{idBaseProduct}")
    Optional<BaseProduct> baseProduct( @PathVariable( value = "idBaseProduct" ) String idBaseProduct ){
        return repo.findById( idBaseProduct );
    }

    @PostMapping("")
    String createBaseProduct(@RequestBody @Valid BaseProduct baseProduct ){
        this.repo.save(baseProduct);

        return "ok";
    }

/*    @PutMapping("")
    String editCustomizationArea( @RequestBody @Valid BaseProduct customizationArea ){
        BaseProduct customizationAreaBDD = repo.findByName( customizationArea.getName() );
        if ( customizationAreaBDD.getId_customization_area() == customizationArea.getId_customization_area() && customizationAreaBDD.getActive() ){
            return "El nombre ya està en uso";
        }

        this.repo.save(customizationArea);
        return "ok";
    }*/
}
