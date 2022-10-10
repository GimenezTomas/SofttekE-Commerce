package softtek.ecommerce.base_products_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import softtek.ecommerce.base_products_service.entities.CustomizationType;
import softtek.ecommerce.base_products_service.entities.TypeHasArea;
import softtek.ecommerce.base_products_service.repositories.interfaces.TypeHasAreaRepo;

import javax.validation.Valid;

@RestController()
@RequestMapping("/type_has_area")
public class TypeHasAreaController {

    @Autowired
    TypeHasAreaRepo repo;

    @GetMapping("")
    Page<TypeHasArea> type_has_area(Pageable page ){
        return repo.findAll( page );
    }

    @PostMapping("")
    String createTypeHasArea( @RequestBody @Valid TypeHasArea typeHasArea ){

        this.repo.save(typeHasArea);

        return "ok";
    }
}
